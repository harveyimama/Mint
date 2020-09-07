package com.mint.harvey.code.challenge.CardVerifier.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mint.harvey.code.challenge.CardVerifier.response.StatsResponse;
import com.mint.harvey.code.challenge.CardVerifier.response.VerificationResponse;

/**
 * @Authur Harvey Imama
 * Controller for routing web requests to the Service responsible for handling
 */
@RestController
public class Controller {
	

	@Autowired
	VerificationService verificationService;

	/**
	 * This method receives GET requests and does a db call to retrieve bin information
	 * am HTTP call is made if information  not in the database. It also initiates a call to the 
	 * publish service for broadcast  
	 * @param bin
	 * @return VerificationResponse
	 */
	@CrossOrigin
	@GetMapping(path = "/card-scheme/verify/{bin}", produces = MediaType.APPLICATION_JSON_VALUE)
	public VerificationResponse verifyBin(@PathVariable(value = "bin") final String bin) {

		VerificationResponse verifyResponse = verificationService.doDataBaseCheck(formatBin(bin));

		if (!verifyResponse.isSuccess())
		{
			verifyResponse = verificationService.doHttpCall(formatBin(bin));
			
		}
		
		if(verifyResponse.isSuccess())
		verificationService.publish(verifyResponse);

		return verifyResponse;

	}
	
	/**
	 * This Method extracts information on the bin calls made and count made on each bin. It connects 
	 * to the database to extract information 
	 * @param start limit
	 * @return StatsResponse
	 */
	@CrossOrigin
	@GetMapping(path = "/card-scheme/stats", produces = MediaType.APPLICATION_JSON_VALUE)
	public StatsResponse getCardSchemeStats(final int start,final int limit) {
		if (start > 0 && limit> 0)
		return verificationService.getDataBaseStats(start,limit);
		else
		{
			StatsResponse res = new StatsResponse();
			res.setSuccess(false);
			return res;
		}

	}
	
	
	/**
	 * This method returns formated 6 digit substring of the supplied bin
	 * @param bin
	 * @return String 
	 */
	private String formatBin(String bin)
	{
		return bin.substring(0,6);
	}

}
