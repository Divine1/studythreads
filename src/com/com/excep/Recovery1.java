package com.com.excep;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Recovery1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            System.out.println("task1 executing");
            return "task1";
        }).thenApplyAsync((inp)->{
            System.out.println("task2 executing");
            return inp+"2";
        }).thenApplyAsync((inp)->{
            inp.charAt(300);

            return inp+"3";
        })
        .handleAsync((inp,ex )->{
            System.out.println("handleasync1 inp "+inp);
            System.out.println(ex);
            return inp+"h1";
        });
        /*
        .exceptionally((ex)->{
            System.out.println("exception");
            System.out.println(ex);
            return "caught exception";
        })*/
        CompletableFuture<String> cf2 = cf1.thenApplyAsync((inp)->{
            System.out.println("task4 executing");
            cf1.complete("interrupting -then complete");
            return inp+"4";
        }).whenCompleteAsync((res,ex)->{
            System.out.println("res "+res);
            System.out.println("ex "+ex);
        });

        String output1 = cf1.get();
        System.out.println("output1");
        System.out.println(output1);

        String output2 = cf2.get();
        System.out.println("output2");
        System.out.println(output2);


        CompletableFuture<Void> cf3 = CompletableFuture.runAsync(()->{
            System.out.println("cf3 task1");
        }).thenApplyAsync((inp)->{
            System.out.println("task2 inp "+inp);
            //cf3.complete("gello");
            return inp;
        });

        //CompletableFuture<Void> cf4 = cf3.

    }
}
