package org.example.example_runable.example_callable;

import java.util.concurrent.Callable;

public class SumTask implements Callable<Long> {
    private int n;

    public SumTask(int n) {
        this.n = n;
    }

    @Override
    public Long call() throws Exception {
        long sum =0;
        for (int i =0 ; i<= n ;i++){
            sum +=i;
        }
        return sum;
    }
}
