package com;

import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.queues.CallQueue;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CallcenterApplication {

    public static void main(String[] args) {
        CallQueue callQueue = new CallQueue(5);
        AtomicInteger ai = new AtomicInteger(1);

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                System.out.println("Agrego: " + ai.get() + " a " + LocalDateTime.now());
                callQueue.add(new Call(ai.getAndIncrement()));
            }
        };

        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println("Empiezo " + LocalDateTime.now());
                    System.out.println("Consumo: " + callQueue.attend().toString());
                } catch (InterruptedException ex) {
                    Logger.getLogger(CallcenterApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(consumer);
        executorService.execute(consumer);
        executorService.execute(consumer);
        executorService.execute(producer);
        executorService.execute(producer);
        executorService.execute(producer);
        executorService.execute(consumer);
        executorService.execute(producer);
        executorService.execute(producer);
        executorService.execute(consumer);
        executorService.execute(producer);
        executorService.execute(producer);
        executorService.execute(producer);
        executorService.execute(producer);
        executorService.execute(producer);
        
        executorService.shutdown();

    }
}
