package com.example.pact.messagepact.producer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DataMessageStreamsProducer {

   String OUTPUT = "greetings-out";


   @Output(OUTPUT)
   MessageChannel outboundChannel();

}
