package com.ms.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

	@KafkaListener(topics ="${spring.kafka.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
	public void listen(String message) {
		System.out.println("Received message: " + message);
	}

}