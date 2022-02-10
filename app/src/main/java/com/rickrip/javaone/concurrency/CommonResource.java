package com.rickrip.javaone.concurrency;

public class CommonResource {
    private int res = 0;

    public CommonResource(int res){
        this.res = res;
    }

    public void increaseValue(){
        res+=1;
    }

    public int getValue(){
        return res;
    }
}