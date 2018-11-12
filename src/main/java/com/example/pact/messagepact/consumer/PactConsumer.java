package com.example.pact.messagepact.consumer;

import com.example.pact.messagepact.DataMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PactConsumer {

   @StreamListener(DataMessageStreamsConsumer.INPUT)
   public void handleGreetings(@Payload DataMessage dataMessage) {
      log.info("Received mymessage: {}", dataMessage);
   }
}
