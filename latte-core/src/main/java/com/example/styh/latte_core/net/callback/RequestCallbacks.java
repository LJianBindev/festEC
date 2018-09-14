package com.example.styh.latte_core.net.callback;

import android.os.Handler;

import com.example.styh.latte_core.ui.LatteLoader;
import com.example.styh.latte_core.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallbacks implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILEURE;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IRequest request, ISuccess success, IError error, IFailure failure, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILEURE = failure;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){//请求成功时
            if (call.isExecuted()){//call已经执行时
                if (SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }else {
                if (ERROR != null){
                    ERROR.onError(response.code(), response.message());
                }
            }
            stopLoading();

        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILEURE != null){
            FAILEURE.onFailure();
        }
        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }
        stopLoading();
    }

    private void stopLoading(){
        if (LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            },1000);//1秒钟的延迟
        }
    }
}
