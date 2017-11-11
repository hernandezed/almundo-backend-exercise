package com.callcenter.almundo.queues;

import com.callcenter.almundo.domain.Call;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallQueue {

    private int maxCapacity;
    private BlockingQueue<Call> callQueue;
    private AtomicInteger callsInProgress;
    private static final Logger logger = LoggerFactory.getLogger(CallQueue.class);
    static ReentrantLock counterLock = new ReentrantLock(true);

    public CallQueue(int capacity) {
        maxCapacity = capacity;
        callQueue = new LinkedBlockingQueue();
        callsInProgress = new AtomicInteger(0);
    }

    public Call add(Call call) {
        synchronized (this) {
            if (callsInProgress.get() == maxCapacity) {
                logger.debug("Se agrega la llamada {} en la cola de espera", call.getId());
                call.setStandBy(true);
            } else {
                logger.debug("Se agrega la llamada {} en la cola principal", call.getId());
                callsInProgress = new AtomicInteger(callsInProgress.addAndGet(1));
            }
            callQueue.add(call);
            notifyAll();
            return call;
        }
    }

    public Call attend() throws InterruptedException {
        synchronized (this) {
            while (callQueue.isEmpty()) {
                wait();
            }
            return callQueue.poll();
        }
    }

    public int callSize() throws InterruptedException {
        synchronized (this) {
            return callQueue.size();
        }
    }

    public AtomicInteger getCallsInProgress() {
        return callsInProgress;
    }

}
