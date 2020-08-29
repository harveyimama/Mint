package com.mint.harvey.code.challenge.subscriber.Subscriber.impl;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
public class Consumer {
	
	@KafkaListener(topics = "com.ng.vela.even.card_verified", groupId = "group_id")
    public void consume(String message) throws IOException {
		if(message != null)
        System.out.println("MINT Consummed message = ".concat(message));
    }

}
