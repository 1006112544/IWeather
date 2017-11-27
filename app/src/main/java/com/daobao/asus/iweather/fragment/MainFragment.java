package com.daobao.asus.iweather.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;

import com.daobao.asus.iweather.Bean.AirBean;
import com.daobao.asus.iweather.Bean.MultipleItem;
import com.daobao.asus.iweather.Bean.NewWeatherBean;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.activity.MainActivity;
import com.daobao.asus.iweather.adpter.MultipleItemQuickAdapter;
import com.daobao.asus.iweather.net.CallBack.IError;
import com.daobao.asus.iweather.net.CallBack.IFailure;
import com.daobao.asus.iweather.net.CallBack.ISuccess;
import com.daobao.asus.iweather.net.RestClient;
import com.daobao.asus.iweather.util.NetState;
import com.daobao.asus.iweather.util.MySharedpreference;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by db on 2017/11/17.
 */

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {
    @BindView(R.id.recyclerview)
    public RecyclerView mRecyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout mRefreshLayout;

    private AirBean mAirBean;                    //空气信息
    private NewWeatherBean mNewWeatherBean;      //天气信息
    private View view;
    protected boolean mIsCreateView = false;     //记录View是否已经被创建
    private boolean IsLoadInfoSuccess = false;   //记录数据是否加载成功
    private MultipleItemQuickAdapter mAdapter;
    private List<MultipleItem> data;
    private int UpdataTime = 0;                  //记录天气数据和空气数据是否都加载成功
    private static final int LOADINFO = 1;       //加载数据
    private static final int UPDATAINFO = 2;     //更新数据
    private static final int LOAD_FAIL = 3;      //加载数据失败
    private InitInfoListener mInitInfoListener;  //加载信息成功的监听
    private String OldCity;                      //记录选择城市前的城市名
    private String CurrentCityName;              //记录当前城市名
    SharedPreferences.Editor  editor;
    private Calendar calendar = Calendar.getInstance();
    public interface InitInfoListener
    {
        void InitSuccessed(String cityName);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //因为和风天气11点不提供每小时天气信息 所以我们要关闭其显示
            long time=System.currentTimeMillis();
            calendar.setTimeInMillis(time);
            int mHour = calendar.get(Calendar.HOUR);
            int amp = calendar.get(Calendar.AM_PM);
            if (msg.what == LOADINFO) //请求数据或者加载本地数据时调用
            {
                UpdataTime++;
                if (UpdataTime == 2) {
                    IsLoadInfoSuccess = true;
                    data = new ArrayList<>();
                    data.add(new MultipleItem(1, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    if(!(amp==1&&mHour==11))
                    {
                        data.add(new MultipleItem(2, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    }
                    data.add(new MultipleItem(3, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(4, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(5, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    mAdapter = new MultipleItemQuickAdapter(data, getContext());
                    mAdapter.openLoadAnimation(SetAnim(MySharedpreference.preferences.getInt("Main_anim",0)));
                    //不只执行一次动画
                    mAdapter.isFirstOnly(false);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mInitInfoListener.InitSuccessed(CurrentCityName);
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
                    if(!(amp==1&&mHour==11))
                    {
                        data.add(new MultipleItem(2, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    }
                    data.add(new MultipleItem(3, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(4, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(5, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                    mRefreshLayout.setRefreshing(false);
                    mInitInfoListener.InitSuccessed(CurrentCityName);
                    UpdataTime = 0;
                }
            }
            else if(msg.what == LOAD_FAIL)//没有网络或数据加载失败
            {
                IsLoadInfoSuccess = false;
                if(mAdapter == null)
                {
                    data = new ArrayList<>();
                    data.add(new MultipleItem(1, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    if(!(amp==1&&mHour==11))
                    {
                        data.add(new MultipleItem(2, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    }
                    data.add(new MultipleItem(3, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(4, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    data.add(new MultipleItem(5, mAirBean, mNewWeatherBean,IsLoadInfoSuccess));
                    mAdapter = new MultipleItemQuickAdapter(data, getContext());
                    mAdapter.openLoadAnimation(SetAnim(MySharedpreference.preferences.getInt("Main_anim",0)));
                    //不只执行一次动画
                    mAdapter.isFirstOnly(false);
                    if(mRefreshLayout.isRefreshing())
                    {
                        mRefreshLayout.setRefreshing(false);
                    }
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                else
                {
                    mRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(),"该地区不可加载",Toast.LENGTH_SHORT).show();
                    if(OldCity!=null)
                    {
                        CurrentCityName = OldCity;
                        editor.putString("City",CurrentCityName);
                        editor.commit();
                    }
                    else
                    {
                        CurrentCityName = "成都";
                        editor.putString("City",CurrentCityName);
                        editor.commit();
                    }
                }
                UpdataTime = 0;
            }
        }
    };

    @SuppressLint("ValidFragment")
    public MainFragment (InitInfoListener mInitInfoListener)
    {
        this.mInitInfoListener = mInitInfoListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
            initView();
            editor = MySharedpreference.getInstance(getContext());
            CurrentCityName = MySharedpreference.preferences.getString("City","成都");
            if(JugeData())
            {
                //保存数据是今天则需要取出保存的数据
                getDataFromLocal();
            }
            else
            {
                //保存数据不是今天则需要请求数据
                if(NetState.isNetworkAvailable(getContext()))
                {
                    initAirInfo(LOADINFO);
                }
                else handler.sendEmptyMessage(LOAD_FAIL);
            }

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
    public void initView()
    {
        //设置刷新progressbar颜色
        mRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.YELLOW,Color.RED);
        //设置刷新监听
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(NetState.isNetworkAvailable(getContext()))
                {
                    initAirInfo(UPDATAINFO);
                }
                else
                {
                    Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    protected void lazyLoad() {

    }

    private void initAirInfo(final int msg)
    {
        CurrentCityName = MySharedpreference.preferences.getString("City","成都");
        RestClient.builder()
                .url("https://free-api.heweather.com/s6/air/now?parameters")
                .params("location",CurrentCityName)
                .params("key","b844972b249244019f2afb19e1f3c889")
                .context(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("cc", "air: "+response);
                        Gson g = new Gson();
                        mAirBean = g.fromJson(response, AirBean.class);
                        try
                        {
                            if(mAirBean.getHeWeather6().get(0).getStatus().equals("ok"))
                            {
                                handler.sendEmptyMessage(msg);
                                //加载空气信息
                                initWeatherInfo(msg);
                                //保存天气信息
                                editor.putString("AirInfo",response);
                                editor.commit();
                            }
                            else handler.sendEmptyMessage(LOAD_FAIL);
                        }
                        catch (Exception e)
                        {
                            handler.sendEmptyMessage(msg);
                            Toast.makeText(getContext(),"该地区空气质量未知",Toast.LENGTH_SHORT).show();
                            //加载空气信息
                            initWeatherInfo(msg);
                            //保存天气信息
                            editor.putString("AirInfo",response);
                            editor.commit();
                        }

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
    private void initWeatherInfo(final int msg)
    {
        RestClient.builder()
                .url("https://free-api.heweather.com/s6/weather?parameters")
                .params("location",CurrentCityName)
                .params("key","b844972b249244019f2afb19e1f3c889")
                .context(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("cc", "sum: "+response);
                        Gson g = new Gson();
                        mNewWeatherBean= g.fromJson(response, NewWeatherBean.class);
                        if(mNewWeatherBean.getHeWeather6().get(0).getStatus().equals("ok"))
                        {
                            handler.sendEmptyMessage(msg);
                            //保存空气信息
                            editor.putString("WeatherInfo",response);
                            editor.commit();
                        }
                        else handler.sendEmptyMessage(LOAD_FAIL);
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

    public void getDataFromLocal()
    {
        String WeatherInfo = MySharedpreference.preferences.getString("WeatherInfo",null);
        String AirInfo = MySharedpreference.preferences.getString("AirInfo",null);
        Gson g = new Gson();
        Log.d("cc", "handleMessage: "+WeatherInfo);
        Log.d("cc", "handleMessage: "+AirInfo);
        mNewWeatherBean = g.fromJson(WeatherInfo,NewWeatherBean.class);
        mAirBean = g.fromJson(AirInfo, AirBean.class);
        //因为本地天气和空气信息是同时获取的所以先将获取次数加一
        UpdataTime++;
        handler.sendEmptyMessage(LOADINFO);
    }
    //判断储存的数据是不是今天的
    public boolean JugeData()
    {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = mDateFormat .format(new Date());
        String SharedDate = MySharedpreference.preferences.getString("Date",null);
        if(SharedDate==null||!date.equals(SharedDate))
        {
            editor.putString("Date",date);
            editor.commit();
            return false;
        }
        else if(SharedDate.equals(SharedDate))
        {
            return true;
        }
        return false;
    }

    public void UpDataUi(String OldCity)
    {
        this.OldCity = OldCity;
        if(NetState.isNetworkAvailable(getContext()))
        {
            initAirInfo(LOADINFO);
        }
        else handler.sendEmptyMessage(LOAD_FAIL);
    }
    private int SetAnim(int modle)
    {
        switch (modle)
        {
            case 0:
                return 0x00000001;
            case 1:
                return 0x00000002;
            case 2:
                return 0x00000003;
            case 3:
                return 0x00000004;
            case 4:
                return 0x00000005;
        }
        return 0x00000001;
    }
}
