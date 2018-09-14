package com.example.styh.latte_core.net.download;

import android.os.AsyncTask;

import com.example.styh.latte_core.net.RestCreator;
import com.example.styh.latte_core.net.callback.IError;
import com.example.styh.latte_core.net.callback.IFailure;
import com.example.styh.latte_core.net.callback.IRequest;
import com.example.styh.latte_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {

    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSTON;
    private final String NAME;

    public DownloadHandler(String url,
                           IRequest request,
                           String downloadDir,
                           String extenston,
                           String name,
                           ISuccess success,
                           IError error,
                           IFailure failure) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSTON = extenston;
        this.NAME = name;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILEURE = failure;
    }

    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILEURE;

    public final void handleDownload(){
        if (REQUEST != null){
            REQUEST.onRequestStart();//开始下载
        }

        RestCreator.getRestService().download(URL,PARAMS).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){//检查Call对象是否执行成功，
                    final ResponseBody responseBody = response.body();

                    final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSTON, responseBody, NAME);

                    //判断文件下载是否完
                    if (task.isCancelled()){
                        if (REQUEST !=null){
                            REQUEST.onRequestEnd();
                        }
                    }
                }else {
                    if (ERROR != null){
                        ERROR.onError(response.code(), response.message());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (FAILEURE != null){
                    FAILEURE.onFailure();
                }
            }
        });
    }
}
