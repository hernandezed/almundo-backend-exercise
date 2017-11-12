package com;

import com.callcenter.almundo.comparator.EmployeePriorityComparator;
import com.callcenter.almundo.domain.Employee;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CallcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallcenterApplication.class, args);
    }

    @Value("${com.callcenter.almundo.initial.quantity.employees}")
    private int initialQuantityEmployees;

    @Bean
    public Queue callQueue() {
        return new ActiveMQQueue("call.queue");
    }

    @Bean
    public AtomicInteger inProcessCalls() {
        return new AtomicInteger();
    }

    @Bean
    public BlockingQueue<Employee> employees() {
        return new PriorityBlockingQueue<>(initialQuantityEmployees, new EmployeePriorityComparator());
    }
    private ActiveMQConnectionFactory connectionFactory;
}
