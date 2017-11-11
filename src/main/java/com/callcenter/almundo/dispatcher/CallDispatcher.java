package com.callcenter.almundo.dispatcher;

import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.domain.Employee;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class CallDispatcher {

    private JmsMessagingTemplate jmsMessagingTemplate;
    private Queue callQueue;
    private AtomicInteger inProcessCalls;

    @Value("${com.callcenter.almundo.max.concurrent.calls}")
    private int maxConcurrentCalls;

    private static final Logger logger = LoggerFactory.getLogger(CallDispatcher.class);

    public CallDispatcher(JmsMessagingTemplate jmsMessagingTemplate, Queue callQueue, AtomicInteger inProcessCalls) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.callQueue = callQueue;
        this.inProcessCalls = inProcessCalls;
    }

    public void dispatchCall(Call call) {
        logger.info("Se recibio la llamada " + call.getId());
        if (inProcessCalls.get() == maxConcurrentCalls) {
            call.setStandBy(true);
        }
        jmsMessagingTemplate.convertAndSend(callQueue, call, new HashMap<>());

    }
}
