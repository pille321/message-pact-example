package com.example.pact.messagepact;

import com.example.pact.messagepact.consumer.DataMessageStreamsConsumer;
import com.example.pact.messagepact.consumer.PactConsumer;
import com.example.pact.messagepact.producer.DataMessageStreamsProducer;
import com.example.pact.messagepact.producer.PactProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding({DataMessageStreamsProducer.class, DataMessageStreamsConsumer.class})
public class MessagePactApplication {

	@Autowired
	private PactProducer pactProducer;

	@Autowired
	private PactConsumer pactConsumer;

	public static void main(String[] args) {
		SpringApplication.run(MessagePactApplication.class, args);
	}

	@GetMapping("/send/{data}")
	public void send(@PathVariable("data") String data){
		pactProducer.sendMessage(new DataMessage(data));
	}

}
