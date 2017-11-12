package com.callcenter.almundo.config;

import javax.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue callQueue() {
        return new ActiveMQQueue("call.queue");
    }
}
