package com.portfolio.pact.postdispatch.async;

import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "post-creation", providerType = ProviderType.ASYNCH)
class PostCreatedConsumerTest {

    @Autowired
    private ObjectMapper mapper;


    @Pact(consumer = "post-dispatch")
    V4Pact contractForPostCreatedEvent(PactBuilder pactBuilder) {
        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("id", "123123123");
        body.stringType("text", "test post text");
//        body.stringType("data", "{\"orderId\":\"1\"}"); //object example

        return pactBuilder
                .usingLegacyMessageDsl()
                .expectsToReceive("post created event")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "contractForPostCreatedEvent", providerType = ProviderType.ASYNCH)
    void shouldHandlePostCreatedMessage(V4Interaction.AsynchronousMessage message) throws JsonProcessingException {
        ObjectNode objectNode = mapper.readValue(
                new String(Objects.requireNonNull(message.getContents().component1().getValue()), UTF_8),
                ObjectNode.class
        );

        PostCreatedEvent event = PostCreatedEvent.builder()
                .id(objectNode.get("id").asText())
                .text(objectNode.get("text").asText())
                .build();

        assertThat(event.getId()).isEqualTo("123123123");
        assertThat(event.getText()).isEqualTo("test post text");
    }
}
