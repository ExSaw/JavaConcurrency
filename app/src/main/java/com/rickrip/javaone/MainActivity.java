package com.rickrip.javaone;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rickrip.javaone.concurrency.CommonResource;
import com.rickrip.javaone.concurrency.ThreadsWithCommonResource;
import com.rickrip.javaone.concurrency.CounterRunnable;
import com.rickrip.javaone.concurrency.ThreadSimple;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ThreadSimple threadSimple = new ThreadSimple();
    TextView tvMainOutOne, tvMainOutTwo, tvMainOutThree, tvMainOutFour, tvMainOutFive;
    Button btnMainOK;
    CommonResource commonResource = new CommonResource(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tvMainOutOne = findViewById(R.id.tv_main_out_one);
        tvMainOutTwo = findViewById(R.id.tv_main_out_two);
        tvMainOutThree = findViewById(R.id.tv_main_out_three);
        tvMainOutFour = findViewById(R.id.tv_main_out_four);
        tvMainOutFive = findViewById(R.id.tv_main_out_five);
        btnMainOK = findViewById(R.id.btn_main_ok);

        initFunction();

        btnMainOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionsFunction();
            }
        });

    }

    //todo
    private synchronized void actionsFunction() {
        tvMainOutThree.setText(threadSimple.getIntCounter());
    }

    //todo
    private void initFunction() {

        threadSimple.setName("threadSimple");
        threadSimple.setDaemon(true);
        threadSimple.start(); // WRONG!!!

        CounterRunnable counterRunnable = new CounterRunnable(
                this.getMainLooper(),
                tvMainOutTwo,
                "CounterClass Thread"
        );
        Thread threadTwo = new Thread(counterRunnable);
        threadTwo.setName("threadTwo");
        threadTwo.start();

        Thread threadFour = new Thread(counterRunnable());
        threadFour.setName("threadFour");
        threadFour.start();

        startTimerThread();

        //common resource
        for(int i=0;i<5;i++){
            Thread t = new Thread(
                    new ThreadsWithCommonResource(
                            commonResource,
                            getMainLooper(),
                            tvMainOutFive,
                            "ThreadWithCommonRes№ "+i
                    )
            );
            t.setName("ThreadWithCommonRes№ "+i);
            t.start();
        }

    }

    // runnable way 3
    private Runnable counterRunnable() {
        Handler handler = new Handler(this.getMainLooper());
        Runnable runnable = new Runnable() {
            private int counter = 0;

            @Override
            public void run() {
                while (true) {

                    counter += 1;

                    try {
                        Thread.sleep(5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvMainOutFour.setText(String.valueOf(counter));
                        }
                    });

                }
            }
        };
        return runnable;
    }

    private void startTimerThread() {
        Handler handler = new Handler(this.getMainLooper());
        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            tvMainOutOne.setText(
                                    ((System.currentTimeMillis() - startTime) / 1000) + "s"
                            );
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }


}