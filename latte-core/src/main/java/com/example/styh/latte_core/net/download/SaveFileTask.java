package com.example.styh.latte_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.styh.latte_core.app.Latte;
import com.example.styh.latte_core.net.callback.IRequest;
import com.example.styh.latte_core.net.callback.ISuccess;
import com.example.styh.latte_core.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object, Void, File>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... objects) {
        String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String name = (String) objects[4];
        final InputStream is =  body.byteStream();
        if (downloadDir == null || downloadDir.equals("")){
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")){
            extension = "";
        }
        if (name ==null){
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(),extension);//如果没name的文件则创建
        }else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {//执行完第一步后返回主线程的操作
        super.onPostExecute(file);
        if (SUCCESS != null){
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    //文件下载完成后默认直接安装
    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
