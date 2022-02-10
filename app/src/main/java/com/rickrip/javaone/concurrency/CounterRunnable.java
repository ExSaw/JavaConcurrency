package com.rickrip.javaone.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class CounterRunnable implements Runnable {

    private Handler handler;
    private TextView textView;
    private String prefix;
    private int count = 0;

    public CounterRunnable(Looper looper, TextView textView, String prefix){
        handler = new Handler(looper);
        this.textView = textView;
        this.prefix = prefix;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10L);
                count+=1;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
             handler.post(new Runnable() {
                 @Override
                 public void run() {
                     textView.setText(prefix+" "+count);
                 }
             });

        }
    }
}
