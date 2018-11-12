package com.example.pact.messagepact.producer;

import com.example.pact.messagepact.DataMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@Slf4j
public class PactProducer {

   private final DataMessageStreamsProducer myMessageStreamOutput;

   @Autowired
   public PactProducer(DataMessageStreamsProducer myMessageStreamOutput) {
      this.myMessageStreamOutput = myMessageStreamOutput;
   }

   public void sendMessage(final DataMessage dataMessage) {
      MessageChannel messageChannel = myMessageStreamOutput.outboundChannel();
      boolean send = messageChannel.send(MessageBuilder
                                                 .withPayload(dataMessage)
                                                 .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                                                 .build());
      log.info("send {}", send);
   }
}
