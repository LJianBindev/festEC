package com.example.styh.festec;

import android.app.Application;

import com.example.styh.latte_core.app.Latte;
import com.example.styh.latte_core.net.interceptors.DebugInterceptor;

public class ExampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
//                .withIcon(new FontAwesomeModule())
//                .withLoaderDeLayed(1000)
                .withApiHost("http://127.0.0.1/") //本地电脑的地址
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();
    }
}
