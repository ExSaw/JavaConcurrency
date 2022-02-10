package com.rickrip.javaone.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class ThreadsWithCommonResource implements Runnable {

    private Handler handler;
    private TextView textView;
    private String prefix;
    CommonResource res;

    public ThreadsWithCommonResource(CommonResource res, Looper looper, TextView textView, String prefix) {
        handler = new Handler(looper);
        this.textView = textView;
        this.prefix = prefix;
        this.res = res;
    }

    @Override
    public void run() {

        for(int i=0;i<5;i++) {
            synchronized (res) {

                res.increaseValue();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(prefix+" CommonRes="+res.getValue());
                        System.out.println(" " + Thread.currentThread().getName() + " CommonRes=" + res.getValue());
                    }
                });

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
