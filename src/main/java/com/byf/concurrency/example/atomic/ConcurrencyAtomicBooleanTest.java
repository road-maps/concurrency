package com.byf.concurrency.example.atomic;

import com.byf.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@ThreadSafe
public class ConcurrencyAtomicBooleanTest {
    private final static int clientTotal = 5000;
    private final static int threadTotal = 200;
    private static AtomicBoolean isHappend = new AtomicBoolean(false);

    private static void test(){
        if (isHappend.compareAndSet(false,true)){
            log.info("execute, isHaddped:{}", isHappend);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i=0; i<clientTotal;i++){
            exec.execute(()->{
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        exec.shutdown();
        log.info("isHapped:{}",isHappend.get());
    }
}