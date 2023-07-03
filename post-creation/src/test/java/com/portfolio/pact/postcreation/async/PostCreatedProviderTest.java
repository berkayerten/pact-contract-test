package com.portfolio.pact.postcreation.async;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
@Provider("PostProvider")
@PactFolder("../post-dispatch/target/pacts")
@Slf4j
class PostCreatedProviderTest {
    @Autowired
    private ObjectMapper mapper;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(Pact pact, Interaction interaction, PactVerificationContext pactVerificationContext) {
        log.info("testTemplate called: {}, {}", pact.getProvider().getName(), interaction);
        pactVerificationContext.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext pactVerificationContext) {
        // Set the Pact test target to "messaging". Otherwise, only request and response interactions would be supported:
        // "Only request/response interactions can be used with an HTTP test target"
        pactVerificationContext.setTarget(new MessageTestTarget());
    }

    @State("PostProvider")
    public void postProviderState() {
        log.info("PostProviderState callback");
    }

    @PactVerifyProvider("a post created event")
    String verifyMessage() throws JsonProcessingException {
        PostCreatedEvent eventMessage = PostCreatedEvent.builder()
                .id("123123123")
                .text("test post text")
//                .data(objectMapper.writeValueAsString(data)) //example for a nested object
                .build();

        return mapper.writeValueAsString(eventMessage);
    }
}
