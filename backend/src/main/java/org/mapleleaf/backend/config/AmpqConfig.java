package org.mapleleaf.backend.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmpqConfig {
    public static final String toRustQueueName = "to_rust";
    public static final String toSpringQueueName = "to_spring";

    @Bean
    public Queue toRustQueue() {
        return new Queue(toRustQueueName);
    }

    @Bean
    public Queue toSpringQueue() {
        return new Queue(toSpringQueueName);
    }
}
