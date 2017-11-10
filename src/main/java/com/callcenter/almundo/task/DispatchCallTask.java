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
    private final static Logger logger = LoggerFactory.getLogger(CallQueue.class);

    public DispatchCallTask(BlockingQueue<Employee> employees, CallQueue callQueue) {
        this.employees = employees;
        this.callQueue = callQueue;
    }

    @Override
    public void run() {
        try {
            Employee employee = employees.take();
            Call c = callQueue.attend();
            logger.info("La llamada {} fue atendida por {}", c.getId(), employee.getName());
            c.setEmployee(employee);
            Thread.sleep(TimeUnit.SECONDS.toMillis(c.getDuration()));
            logger.info("La llamada {} atendida por {} duró {}", c.getId(), employee.getName(), c.getDuration());
            callQueue.getCallsInProgress().decrementAndGet();
            employees.add(employee);
        } catch (InterruptedException ex) {
            logger.error("Hubo un error en la ejecución. Stacktrace", ex);
        }

    }

}
