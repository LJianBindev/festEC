package com.example.styh.latte_core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.styh.latte_core.app.Latte;

public class DimenUtil {

    public static int getScreenWidth(){//得到屏幕的宽
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){//得到屏幕的高
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
