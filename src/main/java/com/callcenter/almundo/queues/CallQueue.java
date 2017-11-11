package com.callcenter.almundo.queues;

import com.callcenter.almundo.domain.Call;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallQueue {

    private int maxCapacity;
    private BlockingQueue<Call> callQueue;
    private BlockingQueue<Call> standByQueue;
    private AtomicInteger callsInProgress;
    private static final Logger logger = LoggerFactory.getLogger(CallQueue.class);

    public CallQueue(int capacity) {
        maxCapacity = capacity;
        callQueue = new LinkedBlockingQueue(maxCapacity);
        standByQueue = new LinkedBlockingQueue();
        callsInProgress = new AtomicInteger(0);
    }

    public Call add(Call call) {
        synchronized (this) {
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
    }

    public Call attend() throws InterruptedException {
        synchronized (this) {
            while (standByQueue.isEmpty() && callQueue.isEmpty()) {
                wait();
            }
            callsInProgress.addAndGet(1);
            if (standByQueue.isEmpty()) {
                return callQueue.poll();
            }
            return standByQueue.poll();
        }
    }

    public int callSize() throws InterruptedException {
        synchronized (this) {
            return callQueue.size();
        }
    }

    public int standBySize() throws InterruptedException {
        synchronized (this) {
            return standByQueue.size();
        }
    }

    public AtomicInteger getCallsInProgress() {
        return callsInProgress;
    }

}
