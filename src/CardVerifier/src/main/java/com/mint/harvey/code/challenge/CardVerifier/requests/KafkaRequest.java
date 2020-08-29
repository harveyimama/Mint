package com.mint.harvey.code.challenge.CardVerifier.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mint.harvey.code.challenge.CardVerifier.data.Payload;


/**
 * @Authur Harvey Imama
 * Implements the sendRquest method of the inherited RequestType interface 
 * for Kafka calls. 
 */
@Service
public class KafkaRequest implements RequestType<Object,Payload>{
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String TOPIC = "com.ng.vela.even.card_verified";

	@Override
	public Object sendRequest(Payload request) {
		
		this.kafkaTemplate.send(TOPIC, request.toString());
		
		return null;
	}

}
