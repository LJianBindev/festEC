package com.example.styh.festec;


import com.example.styh.latte_core.activitys.ProxyActivity;
import com.example.styh.latte_core.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
