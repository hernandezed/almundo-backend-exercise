package com.callcenter.almundo.consumer;

import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.domain.Employee;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class DispatchToEmployeeConsumer {

    private BlockingQueue<Employee> employees;
    private AtomicInteger inProcessCalls;
    private List<Call> finalizeCalls;

    public DispatchToEmployeeConsumer(BlockingQueue<Employee> employees, AtomicInteger inProcessCalls, List<Call> finalizeCalls) {
        this.employees = employees;
        this.inProcessCalls = inProcessCalls;
        this.finalizeCalls = finalizeCalls;
    }

    @JmsListener(destination = "call.queue", concurrency = "${com.callcenter.almundo.max.concurrent.calls}")
    public void dispatchToEmployee(Call call) throws InterruptedException {
        Employee employee = employees.poll();
        inProcessCalls.getAndIncrement();
        call.setDuration(ThreadLocalRandom.current().nextInt(0, 11));
        call.setEmployee(employee);
        Thread.sleep(TimeUnit.SECONDS.toMillis(call.getDuration()));
        finalizeCalls.add(call);
        inProcessCalls.getAndDecrement();
    }

}
