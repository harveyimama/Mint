package com.mint.harvey.code.challenge.CardVerifier.response;

import org.springframework.stereotype.Service;

import com.mint.harvey.code.challenge.CardVerifier.data.Payload;

@Service
public class VerificationResponse {
	
	private boolean success ;
	private Payload payload;
	
	public VerificationResponse()
	{
		this.success = false;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	

}
