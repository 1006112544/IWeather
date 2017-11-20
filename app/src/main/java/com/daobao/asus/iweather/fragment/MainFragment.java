package com.daobao.asus.iweather.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daobao.asus.iweather.Enty.AirBean;
import com.daobao.asus.iweather.Enty.MultipleItem;
import com.daobao.asus.iweather.Enty.NewWeatherBean;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.adpter.MultipleItemQuickAdapter;
import com.daobao.asus.iweather.net.CallBack.IError;
import com.daobao.asus.iweather.net.CallBack.IFailure;
import com.daobao.asus.iweather.net.CallBack.ISuccess;
import com.daobao.asus.iweather.net.RestClient;
import com.daobao.asus.iweather.util.NetState;
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

    private AirBean mAirBean;                    //空气信息
    private NewWeatherBean mNewWeatherBean;      //天气信息
    private View view;
    protected boolean mIsCreateView = false;     //记录View是否已经被创建
    private boolean IsLoadInfoSuccess = false;   //记录数据是否加载成功
    private MultipleItemQuickAdapter mAdapter;
    private List<MultipleItem> data;
    private int UpdataTime = 0;                    //记录天气数据和空气数据是否都加载成功
    private static final int LOADINFO = 1;       //加载数据
    private static final int UPDATAINFO = 2;     //更新数据
    private static final int LOAD_FAIL = 3;      //加载数据失败
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOADINFO) //请求数据时调用
            {
                UpdataTime++;
                if (UpdataTime == 2) {
                    IsLoadInfoSuccess = true;
                    data = new ArrayList<>();
                    data.add(new MultipleItem(1, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(2, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(3, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(4, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    mAdapter = new MultipleItemQuickAdapter(data, getContext());
                    mAdapter.openLoadAnimation(0x00000001);
                    //不只执行一次动画
                    mAdapter.isFirstOnly(false);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    UpdataTime = 0;
                }
            }
            else if (msg.what == UPDATAINFO)//刷新时调用
            {
                UpdataTime++;
                if(UpdataTime==2) {
                    IsLoadInfoSuccess = true;
                    data.clear();
                    data.add(new MultipleItem(1, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(2, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(3, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(4, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                    mRefreshLayout.setRefreshing(false);
                    UpdataTime = 0;
                }
            }
            else if(msg.what == LOAD_FAIL)//没有网络或信息加载失败时
            {
                IsLoadInfoSuccess = false;
                data = new ArrayList<>();
                data.add(new MultipleItem(1, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                data.add(new MultipleItem(2, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                data.add(new MultipleItem(3, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                data.add(new MultipleItem(4, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                mAdapter = new MultipleItemQuickAdapter(data, getContext());
                mAdapter.openLoadAnimation(0x00000001);
                //不只执行一次动画
                mAdapter.isFirstOnly(false);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
            if(NetState.isNetworkAvailable(getContext()))
            {
                initWeatherInfo(LOADINFO);
            }
            else handler.sendEmptyMessage(LOAD_FAIL);
            //设置刷新progressbar颜色
            mRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.YELLOW,Color.RED);
            //设置刷新监听
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(NetState.isNetworkAvailable(getContext()))
                    {
                        initWeatherInfo(UPDATAINFO);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                        mRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }
        mIsCreateView = true;
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

    private void initWeatherInfo(final int msg)
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
                        handler.sendEmptyMessage(msg);
                        initAirInfo(msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        handler.sendEmptyMessage(LOAD_FAIL);
                        Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                        mRefreshLayout.setRefreshing(false);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        handler.sendEmptyMessage(LOAD_FAIL);
                        Toast.makeText(getContext(),"加载错误code"+code,Toast.LENGTH_SHORT).show();
                        mRefreshLayout.setRefreshing(false);

                    }
                })
                .build()
                .post();

    }
    private void initAirInfo(final int msg)
    {
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
                        handler.sendEmptyMessage(msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        handler.sendEmptyMessage(LOAD_FAIL);
                        Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                        mRefreshLayout.setRefreshing(false);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        handler.sendEmptyMessage(LOAD_FAIL);
                        Toast.makeText(getContext(),"加载错误code"+code,Toast.LENGTH_SHORT).show();
                        mRefreshLayout.setRefreshing(false);
                    }
                })
                .build()
                .post();
    }

}
