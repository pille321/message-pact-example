package com.example.pact.messagepact.consumer;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.MessagePactProviderRule;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.model.v3.messaging.MessagePact;
import com.example.pact.messagepact.DataMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PactConsumerTest {

   @Rule
   public MessagePactProviderRule mockProvider = new MessagePactProviderRule(this);
   private DataMessage currentMessage;

   @Autowired
   private PactConsumer messageConsumer;

   @Pact(provider = "pactprovider", consumer = "pactconsumer")
   public MessagePact userCreatedMessagePact(MessagePactBuilder builder) {
      PactDslJsonBody body = new PactDslJsonBody();
      body.stringValue("data", "test")
              .closeObject();

      return builder
              .expectsToReceive("a user created message")
              .withContent(body)
              .toPact();
   }

   @Test
   @PactVerification("userCreatedMessagePact")
   public void verifyCreatePersonPact() throws IOException {
      messageConsumer.handleGreetings(this.currentMessage);
   }

   /**
    * This method is called by the Pact framework.
    */
   public void setMessage(byte[] message) throws IOException {
      ObjectMapper objectMapper = new ObjectMapper();
      this.currentMessage =  objectMapper.readValue(message,DataMessage.class);
      ;
   }

}