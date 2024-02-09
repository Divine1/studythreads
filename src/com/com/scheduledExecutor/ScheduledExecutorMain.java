package com.com.scheduledExecutor;

import java.util.concurrent.*;

public class ScheduledExecutorMain {
    public static int counter=0;
    public static void main(String[] args) {


        Runnable task = ()->{

            System.out.println("start task "+counter);
            sleep(2000);
            System.out.println("end task "+(counter));
            counter++;
        };
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
       // ses.
        ses.scheduleAtFixedRate(task,2,5, TimeUnit.SECONDS);
        ses.scheduleAtFixedRate(task,2,5, TimeUnit.SECONDS);


    }

    static void sleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
