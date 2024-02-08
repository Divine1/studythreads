package com.basic2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class IntroAsync {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf1 =  CompletableFuture.runAsync(()->{
            System.out.println("Executing task1 on thread -> "+Thread.currentThread().getName());
        });

        Void unused = cf1.get();
        // by default completableFuture executes on ForkJoinPool thread by default
        // developer can update the commonPool thread capacity  to any number


        ExecutorService executorService = Executors.newFixedThreadPool(5);
        // we can also pass our own thread pool object to CompletableFuture
        // it is advised to use our own thread pool while using CompletableFuture

        //its important to have our own thread pool to debug performance issues in general.
        // different stages in the pipeline can run on different thread pools
        CompletableFuture<Void> cf2 =  CompletableFuture.runAsync(()->{
            System.out.println("Executing task2 on thread -> "+Thread.currentThread().getName());
            sleep(5000);
            System.out.println("task2 execution completed");
        },executorService);
        //cf2.get();

        CompletableFuture<Void> cf3  = cf2.thenRunAsync(()->{
            System.out.println("executing task3 on thread -> "+Thread.currentThread().getName());
            //return "task3";
            sleep(5000);
            System.out.println("task3 execution completed");

        },executorService);

        cf3.get();
        System.out.println("main ended");

    }
    static void sleep(long milliseconds){

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
