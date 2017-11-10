package com.callcenter.almundo.queues;

import com.callcenter.almundo.domain.Call;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CallQueue {

    int maxCapacity;
    private BlockingQueue<Call> callQueue;
    private BlockingQueue<Call> standByQueue;
    AtomicInteger callsInProgress;

    public CallQueue(int capacity) {
        maxCapacity = capacity;
        callQueue = new LinkedBlockingQueue(maxCapacity);
        standByQueue = new LinkedBlockingQueue();
        callsInProgress = new AtomicInteger(0);
    }

    public synchronized Call add(Call call) {
        if (callsInProgress.get() == maxCapacity) {
            callQueue.add(call);
        }
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
