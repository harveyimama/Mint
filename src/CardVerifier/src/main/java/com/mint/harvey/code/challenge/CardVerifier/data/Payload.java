package com.mint.harvey.code.challenge.CardVerifier.data;

public class Payload {
	
	private String scheme;
	private String type;
	private String bank;
	
	Payload()
	{
		
	}
	
	public Payload(BinEntity binEntity)
	{
		this.bank = binEntity.getBank();
		this.scheme = binEntity.getScheme();
		this.type = binEntity.getType();
	}
	
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
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "{\"class\":\"Payload\",\"scheme\":\"" + scheme + "\", \"type\":\"" + type + "\", \"bank\":\"" + bank
				+ "\"}";
	}
	
	
	

}
