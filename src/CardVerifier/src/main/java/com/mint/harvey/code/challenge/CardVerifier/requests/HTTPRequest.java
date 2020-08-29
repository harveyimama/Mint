package com.mint.harvey.code.challenge.CardVerifier.requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mint.harvey.code.challenge.CardVerifier.data.HTTPParams;

/**
 * @Authur Harvey Imama
 * Implements the sendRquest method of the inherited RequestType interface 
 * for HTTP calls. 
 */
@Service
public class HTTPRequest implements RequestType<String,HTTPParams>{


	@Override
	public String sendRequest(HTTPParams request) {
		String serviceOutPut ="";
		try {
		
			HttpURLConnection connection = HTTPConnection.getInstance(request.getUrl());
			connection.setDoOutput(true);
			connection.setRequestMethod(request.getMode());
			if(request.getConnectionTimeOut() > 0)
			connection.setConnectTimeout(request.getConnectionTimeOut());
			if(request.getReadTimeOut() > 0)
			connection.setReadTimeout(request.getReadTimeOut());
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(connection.getInputStream())));

			String thirdPartyOutput = "";

			while ((thirdPartyOutput = br.readLine()) != null) {

				serviceOutPut = thirdPartyOutput;
			}

			br.close();
			connection.disconnect();
			return serviceOutPut;
			
		} catch (Exception e) {
		
			return null;
		}
		
	}
	
	
	private final static class HTTPConnection {
		
		private static final Map<String,HttpURLConnection> connections = new HashMap<>();
		
		private HTTPConnection()
		{}
		
		public static HttpURLConnection getInstance(String postedurl)
		{
			HttpURLConnection conn = connections.get(postedurl) ;
			try {
				if(conn == null)
				{
					URL url = new URL(postedurl);
					conn = (HttpURLConnection) url.openConnection();
					connections.put(postedurl, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 	
			
			return conn;	
		}

	}

}
