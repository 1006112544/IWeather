package com.daobao.asus.iweather.net.DownLoad;

import android.content.Context;
import android.os.AsyncTask;

import com.daobao.asus.iweather.net.CallBack.IError;
import com.daobao.asus.iweather.net.CallBack.IFailure;
import com.daobao.asus.iweather.net.CallBack.IRequest;
import com.daobao.asus.iweather.net.CallBack.ISuccess;
import com.daobao.asus.iweather.net.RestCreator;
import com.daobao.asus.iweather.util.file.FileUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.WeakHashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by db on 2017/10/30.
 */

public class DownLoadHandler {

    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWANLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private Context context;

    public DownLoadHandler(String url,
                           IRequest request,
                           String dowanload_dir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error,
                           Context context) {
        URL = url;
        REQUEST = request;
        DOWANLOAD_DIR = dowanload_dir;
        EXTENSION = extension;
        NAME = name;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        this.context = context;
    }

    public final void handleDownload(){
        //开始下载了
        if(REQUEST!=null)
        {
            REQUEST.onRequsetStart();
        }
        RestCreator.getRestService().download(URL,PARAMS).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    final ResponseBody responseBody = response.body();
                    //文件总长度
                    long total =  responseBody.contentLength();
                    final SaveFileTask task = new SaveFileTask(REQUEST,SUCCESS,context);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWANLOAD_DIR,EXTENSION,responseBody,NAME,total);
                    //这里一定要注意判断，否则文件下载不安全
                    if(task.isCancelled())
                    {
                        if(REQUEST!=null)
                        {
                            REQUEST.onRequsetEnd();
                        }
                    }
                }
                else if (ERROR!=null)
                {
                    ERROR.onError(response.code(),response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(FAILURE!=null)
                {
                    FAILURE.onFailure();
                }
            }
        });
    }

}
