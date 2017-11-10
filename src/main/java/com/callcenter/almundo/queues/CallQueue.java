package com.callcenter.almundo.queues;

import com.callcenter.almundo.domain.Call;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallQueue {

    int maxCapacity;
    private BlockingQueue<Call> callQueue;
    private BlockingQueue<Call> standByQueue;
    AtomicInteger callsInProgress;

    private final static Logger logger = LoggerFactory.getLogger(CallQueue.class);

    public CallQueue(int capacity) {
        maxCapacity = capacity;
        callQueue = new LinkedBlockingQueue(maxCapacity);
        standByQueue = new LinkedBlockingQueue();
        callsInProgress = new AtomicInteger(0);
    }

    public synchronized Call add(Call call) {
        if (callsInProgress.get() == maxCapacity) {
            logger.debug("Se agrega la llamada {} en la cola principal", call.getId());
            callQueue.add(call);
        }
        logger.debug("Se agrega la llamada {} en la cola de espera", call.getId());
        standByQueue.add(call);
        call.setStandBy(true);
        notifyAll();
        return call;
    }

    public synchronized Call attend() throws InterruptedException {
        while (standByQueue.isEmpty() && callQueue.isEmpty()) {
            wait();
        }
        callsInProgress.addAndGet(1);
        if (standByQueue.isEmpty()) {
            return callQueue.poll();
        }
        return standByQueue.poll();
    }

    public synchronized int callSize() throws InterruptedException {
        return callQueue.size();
    }

    public synchronized int standBySize() throws InterruptedException {
        return standByQueue.size();
    }

    public AtomicInteger getCallsInProgress() {
        return callsInProgress;
    }

}
