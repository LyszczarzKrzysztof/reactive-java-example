package com.example.reactivejavaexample.observer;

import java.io.IOException;

public class Main implements MyObservable{

    public static void main(String[] args){
        Main main = new Main();
    }

    public Main() {
        Downloader downloader = new Downloader(this);
    }

    @Override
    public void onFinish() {
        System.out.println("Finish!");
    }
}
