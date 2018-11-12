package com.example.pact.messagepact.producer;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.AmqpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import com.example.pact.messagepact.DataMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PactRunner.class)
@Provider("pactprovider")
@PactFolder("../message-pact/target/pacts")
public class PactProducerTest {

   @TestTarget
   public final Target target = new AmqpTarget(Collections.singletonList("com.example.pact.messagepact.producer.*"));
   private DataMessageStreamsProducer publisher = Mockito.mock(DataMessageStreamsProducer.class);
   private MessageChannel messageChannel = Mockito.mock(MessageChannel.class);
   private PactProducer messageProvider = new PactProducer(publisher);

   @PactVerifyProvider("a user created message")
   public String verifyUserCreatedMessage() throws JsonProcessingException {
      // given
      when(publisher.outboundChannel()).thenReturn(messageChannel);

      // when
      DataMessage message = new DataMessage("test");
      messageProvider.sendMessage(message);


      // then
      ArgumentCaptor<Message> messageCapture = ArgumentCaptor.forClass(Message.class);
      verify(messageChannel, times(1)).send(messageCapture.capture());

      return new ObjectMapper().writeValueAsString(messageCapture.getValue().getPayload());
   }
}