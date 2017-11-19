package com.daobao.asus.iweather.net;

import android.content.Context;

import com.daobao.asus.iweather.net.CallBack.IError;
import com.daobao.asus.iweather.net.CallBack.IFailure;
import com.daobao.asus.iweather.net.CallBack.IRequest;
import com.daobao.asus.iweather.net.CallBack.ISuccess;
import com.daobao.asus.iweather.net.CallBack.RequestCallbacks;
import com.daobao.asus.iweather.net.DownLoad.DownLoadHandler;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by db on 2017/10/29.
 */

public class RestClient {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IError ERROR;
    private final RequestBody REQUESTBODY;
    private final Context context;
    private final File FILE;

    RestClient(String url,
               Map<String, Object> params,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               File file,
               Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.REQUESTBODY = body;
        this.FILE = file;
        this.context = context;
    }

    public static RestClientBuilder builder()
    {
        return new RestClientBuilder();
    }

    private void  request(HttpMethod method)
    {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if(REQUEST!=null)
        {
            REQUEST.onRequsetStart();
        }
        switch (method)
        {
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,REQUESTBODY);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,REQUESTBODY);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = RestCreator.getRestService().upload(URL,body);
                break;
            default:
                break;
        }
        if(call!=null)
        {
            call.enqueue(getRequestCallback());
        }
    }

    /**
     * 获取RequestCallback
     * @return
     */
    private Callback<String> getRequestCallback()
    {
        return new RequestCallbacks(REQUEST,SUCCESS,FAILURE,ERROR);
    }

    public final void get()
    {
        request(HttpMethod.GET);
    }
    public final void post()
    {
        if(REQUESTBODY == null)
        {
            request(HttpMethod.POST);
        }
        else
        {
            if(!PARAMS.isEmpty())
            {
                throw new RuntimeException("params must be null！");
            }
            request(HttpMethod.POST_RAW);
        }
    }
    public final void put()
    {
        if(REQUESTBODY == null)
        {
            request(HttpMethod.PUT);
        }
        else
        {
            if(!PARAMS.isEmpty())
            {
                throw new RuntimeException("params must be null！");
            }
            request(HttpMethod.PUT_RAW);
        }
    }
    public final void delete()
    {
        request(HttpMethod.DELETE);
    }
    public final void upload()
    {
        request(HttpMethod.UPLOAD);
    }
    public final void download(){
        new DownLoadHandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR,context)
                .handleDownload();
    }
}
