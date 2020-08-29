package com.mint.harvey.code.challenge.CardVerifier.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class BinEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private  long id ;
	@Column(unique=true)
	private  String bin;
	private  String scheme;
	private  String type;
	private  String bank;
	private  int noOfSuccessfulCalls;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
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
	public int getNoOfSuccessfulCalls() {
		return noOfSuccessfulCalls;
	}
	public void setNoOfSuccessfulCalls(int noOfSuccessfulCalls) {
		this.noOfSuccessfulCalls = noOfSuccessfulCalls;
	}
	
	
	
}
