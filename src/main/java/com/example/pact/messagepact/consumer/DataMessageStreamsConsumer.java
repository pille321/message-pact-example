package com.example.pact.messagepact.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface DataMessageStreamsConsumer {

   String INPUT = "greetings-in";


   @Input(INPUT)
   SubscribableChannel subscribableChannel();
}
