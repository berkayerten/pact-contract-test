package com.portfolio.pact.postdispatch.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostService {
    public void createPost(String id, String text) {
      log.info("Post created with id: {} and text: {}", id, text);
    }
}
