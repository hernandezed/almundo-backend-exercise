package com.callcenter.almundo.config;

import com.callcenter.almundo.comparator.EmployeePriorityComparator;
import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.domain.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallcenterConfig {

    @Value("${com.callcenter.almundo.initial.quantity.employees}")
    private int initialQuantityEmployees;

    @Bean
    public AtomicInteger inProcessCalls() {
        return new AtomicInteger();
    }

    @Bean
    public BlockingQueue<Employee> employees() {
        return new PriorityBlockingQueue<>(initialQuantityEmployees, new EmployeePriorityComparator());
    }

    @Bean
    public List<Call> finalizeCalls() {
        return new ArrayList<>();
    }
}
