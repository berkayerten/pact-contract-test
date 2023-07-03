package com.portfolio.pact.consumer.post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostService {
    public void createPost(String id, String text) {
      log.info("Post created with id: {} and text: {}", id, text);
    }
}
