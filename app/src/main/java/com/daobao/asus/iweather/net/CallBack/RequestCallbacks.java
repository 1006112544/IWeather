package com.daobao.asus.iweather.net.CallBack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 2017/10/30.
 */

public class RequestCallbacks implements Callback<String>{
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error) {
        REQUEST = request;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful())
        {
            if(call.isExecuted())
            {
                if(SUCCESS!=null)
                {
                    SUCCESS.onSuccess(response.body());
                }
            }
        }
        else
        {
            if(ERROR!=null)
            {
                ERROR.onError(response.code(),response.message());
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(FAILURE!=null)
        {
            FAILURE.onFailure();
        }
        if(REQUEST!=null)
        {
            REQUEST.onRequsetEnd();
        }
    }
}
