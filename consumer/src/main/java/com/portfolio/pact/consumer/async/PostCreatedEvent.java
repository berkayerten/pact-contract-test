package com.portfolio.pact.consumer.async;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreatedEvent {
    String id;
    String text;
}
