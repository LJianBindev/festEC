package com.example.styh.latte_core.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public abstract class BaseInterceptor implements Interceptor {

    protected LinkedHashMap<String, String> getUrlParameters(Chain chain){//LinkedHashMap有序的
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++){
            params.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return params;
    }

    protected String getUrlParameters(Chain chain, String key){//获取value值
        final HttpUrl url = chain.request().url();
        return url.queryParameter(key);
    }

    protected LinkedHashMap<String, String> getBodyParameters(Chain chain){//获取参数
        final FormBody formBody = (FormBody) chain.request().body();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        int size = formBody.size();
        for (int i = 0; i < size; i++){
            params.put(formBody.name(i),formBody.value(i));
        }
        return params;
    }

    protected String getBodyParameters(Chain chain, String key){
        return getBodyParameters(chain).get(key);
    }
}
