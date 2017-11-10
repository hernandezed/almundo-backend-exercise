package com.callcenter.almundo.dispatcher;

import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.domain.Employee;
import com.callcenter.almundo.queues.CallQueue;
import com.callcenter.almundo.task.DispatchCallTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;

public class CallDispatcher {

    private ExecutorService executorService;
    private BlockingQueue<Employee> employees;
    private CallQueue callQueue;

    private static final int MIN_DURATION = 5;
    private static final int MAX_DURATION = 10;
    private static final int MAX_CALL_PERMITED = 10;

    public CallDispatcher(ExecutorService executorService, BlockingQueue<Employee> employees) {
        this.executorService = executorService;
        this.employees = employees;
        callQueue = new CallQueue(MAX_CALL_PERMITED);
    }

    public void dispatchCall(Call call) {
        call.setDuration(ThreadLocalRandom.current().nextInt(MIN_DURATION, MAX_DURATION + 1));
        callQueue.add(call);
        executorService.execute(new DispatchCallTask(employees, callQueue));
    }
}
