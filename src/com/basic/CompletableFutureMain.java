package com.basic;

import java.util.concurrent.*;

public class CompletableFutureMain {

    static ExecutorService threadpool = Executors.newFixedThreadPool(4);
    static int counter=0;
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //Future<String> future = executeAsync();
        Future<String> future = executeAsyncWithCompletable();
        System.out.println("Future block");
        System.out.println("future result "+future.get(20,TimeUnit.SECONDS));
        System.out.println("Main completed");
        threadpool.shutdown();
       // System.out.println("availableProcessors "+Runtime.getRuntime().availableProcessors());

    }

    public static Future<String> executeAsyncWithCompletable(){
        System.out.println("Processing completableFuture");
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        threadpool.submit(()->{
            String input = "workload "+(counter++);
             String output = executeWorkload(input);

             //propagate exceptions using completable futures
             completableFuture.completeExceptionally( new RuntimeException("exception testing"));
             //completableFuture can be completed only once
            completableFuture.complete(output);

        });
        return completableFuture;
    }

    public static Future<String> executeAsync(){
        System.out.println("processing normally");
        return threadpool.submit(()->{
           return executeWorkload("workload "+(counter++));

        });
    }

    private static String executeWorkload(String workload1) {
        System.out.println("executing "+workload1);
        try {
            Thread.sleep(3000);
            return "completed "+workload1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
