package com.mint.harvey.code.challenge.CardVerifier.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mint.harvey.code.challenge.CardVerifier.data.BinEntity;
import com.mint.harvey.code.challenge.CardVerifier.data.HTTPParams;
import com.mint.harvey.code.challenge.CardVerifier.data.Payload;
import com.mint.harvey.code.challenge.CardVerifier.requests.DatabaseRequest;
import com.mint.harvey.code.challenge.CardVerifier.requests.HTTPRequest;
import com.mint.harvey.code.challenge.CardVerifier.requests.KafkaRequest;
import com.mint.harvey.code.challenge.CardVerifier.response.APIReponse;
import com.mint.harvey.code.challenge.CardVerifier.response.StatsResponse;
import com.mint.harvey.code.challenge.CardVerifier.response.VerificationResponse;

/**
 * @Authur Harvey Imama
 * Services Responsible for receiving verification requests, sending to respective request implementation
 * and interpreting the response
 */

@Component
public class VerificationService {
	@Autowired
	HTTPRequest hTTPRequest;
	@Autowired
	DatabaseRequest databaseRequest;
	@Autowired
	KafkaRequest kafkaRequest;

	private final static int ASYNC_NUMBER_OF_TRIES = 2;
	private final static int ASYNC_WAIT_TIME = 2;
	private final static int THREAD_LOCK_TIMEOUT = 8;
	private final ReentrantLock lock = new ReentrantLock();
	
	
	/**
	 * Receives HTTP requests and passes in thread to HHTPRequester 
	 * It calls the fetchEventualResult callback service to extract result of thread call
	 * @param bin
	 * @return VerificationResponse
	 */

	public VerificationResponse doHttpCall(String bin) {

		ExecutorService executor = Threader.startThreader();

		try {
			if (lock.tryLock(THREAD_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
				try {
					executor.execute(new HTTPRequester(bin));
				} finally {
					lock.unlock();
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return fetchEventualResult(bin);
	}
	
	/**
	 * Sends a message to the KafkaRequest Service for eventual broadcast
	 * @param VerificationResponse
	 * @return no return
	 */
	public void publish(final VerificationResponse resp) {
		kafkaRequest.sendRequest(resp.getPayload());

	}

	/**
	 * Sends a message to the KafkaRequest Service for eventual broadcast
	 * @param VerificationResponse
	 * @return no return
	 */
	public VerificationResponse doDataBaseCheck(final String bin) {

		VerificationResponse res = new VerificationResponse();

		BinEntity binE = (BinEntity) databaseRequest.sendRequest(bin);

		if (binE != null) {
			res.setSuccess(true);
			res.setPayload(new Payload(binE));
		}

		return res;
	}
	
	/**
	 * Extracts bin information from the database. It attempts this repeatedly based on the ASYNC_NUMBER_OF_TRIES
	 * settings and waits based on  ASYNC_WAIT_TIME before each call
	 * @param bin
	 * @return VerificationResponse
	 */
	private VerificationResponse fetchEventualResult(String bin) {
		VerificationResponse res = new VerificationResponse();
		for (int i = 0; i < ASYNC_NUMBER_OF_TRIES; i++) {

			res = this.doDataBaseCheck(bin);
			if (res != null && !res.isSuccess()) {
				try {
					TimeUnit.SECONDS.sleep(ASYNC_WAIT_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
					res = this.doDataBaseCheck(bin);
				}
			} else {
				if (res != null)
					break;
			}

		}

		return res;
	}

	/**
	 * @Authur Harvey Imama
	 * Sub class that implements Runnable used by the thread executor to make http calls to the HTTPRequest 
	 * service
	 */
	class HTTPRequester implements Runnable {

		private String bin;

		HTTPRequester(final String bin) {
			this.bin = bin;
		}

		@Override
		public void run() {

			HTTPParams params = new HTTPParams.Builder().url("https://lookup.binlist.net/".concat(this.bin))
					.connectionTimeOut(1221).readTimeOut(1221).mode("GET").build();

			String ret = hTTPRequest.sendRequest(params);

			if (ret != null) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					APIReponse bin = objectMapper.readValue(ret, APIReponse.class);
					BinEntity binE = new BinEntity();
					binE.setBank(bin.getBank().getName());
					binE.setScheme(bin.getScheme());
					binE.setType(bin.getType());
					binE.setBin(this.bin);
					databaseRequest.sendRequest(binE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Receives  database statistics requests from the controller sends to the database
	 * receives information and maps to a  StatsResponse before shipping response to contoller
	 * @param start limit
	 * @return StatsResponse
	 */
	public StatsResponse getDataBaseStats(int start, int limit) {
		
		StatsResponse statsResponse = new StatsResponse();
		
		List<BinEntity> entities =  databaseRequest.sendRequest(start,limit);
		if (entities.size() > 0 )
		{
			statsResponse.setSuccess(true);
			statsResponse.setLimit(limit);
			statsResponse.setStart(start);
			List<HashMap<String, Integer>> payload = new ArrayList<HashMap<String, Integer>> ();
			
			for(BinEntity entity  :  entities)
			{
				HashMap<String, Integer> h  = new HashMap<String, Integer>();
				h.put(entity.getBin(), entity.getNoOfSuccessfulCalls());
				payload.add(h );
				
			}
			statsResponse.setPayload(payload );
		}
		else
		{
			statsResponse.setSuccess(false);
			statsResponse.setLimit(limit);
			statsResponse.setStart(start);
			
		}
		return statsResponse;
	}

}
