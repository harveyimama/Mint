package com.mint.harvey.code.challenge.CardVerifier.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mint.harvey.code.challenge.CardVerifier.data.Bank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APIReponse {
	private String scheme;
	private String type;
	private Bank bank;
	
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	

}
