package com.callcenter.almundo.task;

import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.domain.Employee;
import com.callcenter.almundo.queues.CallQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DispatchCallTask implements Runnable {

    private BlockingQueue<Employee> employees;
    private CallQueue callQueue;

    public DispatchCallTask(BlockingQueue<Employee> employees, CallQueue callQueue) {
        this.employees = employees;
        this.callQueue = callQueue;
    }

    @Override
    public void run() {
        try {
            Employee employee = employees.take();
            Call c = callQueue.attend();

            System.out.println("La llamada " + c.getId() + " fue atendida por " + employee.getName());

            c.setEmployee(employee);
            Thread.sleep(TimeUnit.SECONDS.toMillis(c.getDuration()));
            callQueue.getCallsInProgress().decrementAndGet();
            employees.add(employee);
        } catch (InterruptedException ex) {
            Logger.getLogger(DispatchCallTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
