package com.portfolio.pact.postdispatch.async;

import com.portfolio.pact.postdispatch.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCreatedListener {

    private final PostService postService;

    @RabbitListener
    public void handlePostCreation(PostCreatedEvent event) {
        postService.createPost(event.id, event.text);
    }
}
