package com.example.styh.festec;

import android.app.Application;

import com.example.styh.latte_core.app.Latte;

public class ExampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://127.0.0.1/") //本地电脑的地址
                .configure();
    }
}
