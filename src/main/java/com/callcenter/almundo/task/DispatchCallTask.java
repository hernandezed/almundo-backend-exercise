package com.callcenter.almundo.task;

import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.domain.Employee;
import com.callcenter.almundo.queues.CallQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatchCallTask implements Runnable {

    private BlockingQueue<Employee> employees;
    private CallQueue callQueue;

    private static final Logger logger = LoggerFactory.getLogger(DispatchCallTask.class);

    public DispatchCallTask(BlockingQueue<Employee> employees, CallQueue callQueue) {
        this.employees = employees;
        this.callQueue = callQueue;
    }

    @Override
    public void run() {
        try {
            Employee employee = employees.take();
            Call call = callQueue.attend();
            logger.info("La llamada {} fue atendida por {}", call.getId(), employee.getName());
            call.setEmployee(employee);
            Thread.sleep(TimeUnit.SECONDS.toMillis(call.getDuration()));
            logger.info("La llamada {} atendida por {} duró {}s", call.getId(), employee.getName(), call.getDuration());
            callQueue.getCallsInProgress().decrementAndGet();
            employees.add(employee);
        } catch (InterruptedException ex) {
            logger.error("Hubo un error en la ejecución. Stacktrace", ex);
        }

    }

}
