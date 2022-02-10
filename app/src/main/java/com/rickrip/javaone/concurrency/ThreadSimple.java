package com.rickrip.javaone.concurrency;

import android.util.Log;

public class ThreadSimple extends Thread {

    public String strOut = "";
    private int intCounter = 0;

    @Override
    public void run() {

        strOut = this.getClass().getName()
                + " "
                + currentThread().getName();

        Log.d("DEF_TAG", strOut);

        while (true){
            currentThread();
            try {
                sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            increaseIntCounter();
        }

    }

    public String getIntCounter() {
        return String.valueOf(intCounter);
    }

    private void increaseIntCounter(){
        intCounter+=1;
    }

}
