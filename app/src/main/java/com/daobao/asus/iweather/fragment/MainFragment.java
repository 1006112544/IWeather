package com.daobao.asus.iweather.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.daobao.asus.iweather.Enty.AirBean;
import com.daobao.asus.iweather.Enty.MultipleItem;
import com.daobao.asus.iweather.Enty.NewWeatherBean;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.adpter.MultipleItemQuickAdapter;
import com.daobao.asus.iweather.net.CallBack.IError;
import com.daobao.asus.iweather.net.CallBack.IFailure;
import com.daobao.asus.iweather.net.CallBack.ISuccess;
import com.daobao.asus.iweather.net.RestClient;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by db on 2017/11/17.
 */

public class MainFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_erro)
    ImageView mIvError;

    private AirBean mAirBean;
    private NewWeatherBean mNewWeatherBean;
    private View view;
    protected boolean mIsCreateView = false;
    private List<MultipleItem> data;
    private int UpdaTime = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UpdaTime++;
            if(UpdaTime==2)
            {
                data = new ArrayList<>();
                data.add(new MultipleItem(1,mAirBean,mNewWeatherBean));
                data.add(new MultipleItem(2,mAirBean,mNewWeatherBean));
                data.add(new MultipleItem(3,mAirBean,mNewWeatherBean));
                data.add(new MultipleItem(4,mAirBean,mNewWeatherBean));
                mRecyclerView.setAdapter(new MultipleItemQuickAdapter(data,getContext()));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
            initHttp();
        }
        //mIsCreateView = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mIsCreateView) {
            lazyLoad();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            lazyLoad();
        }
    }

    protected void lazyLoad() {

    }

    private void initHttp()
    {
        RestClient.builder()
                .url("https://free-api.heweather.com/s6/air/now?parameters")
                .params("location","绵阳")
                .params("key","b844972b249244019f2afb19e1f3c889")
                .context(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("cc", "air: "+response);
                        Gson g = new Gson();
                        mAirBean = g.fromJson(response, AirBean.class);
                        handler.sendEmptyMessage(1);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .post();
        RestClient.builder()
                .url("https://free-api.heweather.com/s6/weather?parameters")
                .params("location","绵阳")
                .params("key","b844972b249244019f2afb19e1f3c889")
                .context(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("cc", "sum: "+response);
                        Gson g = new Gson();
                        mNewWeatherBean= g.fromJson(response, NewWeatherBean.class);
                        handler.sendEmptyMessage(1);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .post();
    }

}
