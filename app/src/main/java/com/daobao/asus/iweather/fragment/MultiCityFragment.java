package com.daobao.asus.iweather.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.daobao.asus.iweather.Bean.AirBean;
import com.daobao.asus.iweather.Bean.NewWeatherBean;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.adpter.MultiCityAdapter;
import com.daobao.asus.iweather.net.CallBack.IError;
import com.daobao.asus.iweather.net.CallBack.IFailure;
import com.daobao.asus.iweather.net.CallBack.ISuccess;
import com.daobao.asus.iweather.net.RestClient;
import com.daobao.asus.iweather.util.MySharedpreference;
import com.daobao.asus.iweather.util.NetState;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by db on 2017/11/17.
 */

public class MultiCityFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.empty)
    LinearLayout mLayout;
    @BindView(R.id.multi_text)
    TextView mMulti_text;
    SharedPreferences.Editor  editor;
    private View view;
    private NewWeatherBean mNewWeatherBean;
    private MultiCityAdapter adapter;
    protected boolean mIsCreateView = false;
    private ArrayList<NewWeatherBean> data;
    private int OldMultiCityNum;//记录更新UI前的城市个数
    private int MultiCityNum;
    private final int NO_MORE_CITIS=1;
    private final int HAVE_MORE_CITIS=2;
    private final int NO_INTERNET=3;
    private final int ADD_CITY=4;
    private final int UPDATA=5;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case NO_MORE_CITIS://没有多城市
                    mLayout.setVisibility(View.VISIBLE);
                    break;
                case HAVE_MORE_CITIS://有多城市
                    MultiCityNum--;
                    if(MultiCityNum==0)
                    {
                        adapter = new MultiCityAdapter(R.layout.item_multi_city,data,getContext());
                        mRecyclerView.setAdapter(adapter);
                        adapter.openLoadAnimation(SetAnim(MySharedpreference.preferences.getInt("Multi_anim",0)));
                        //不只执行一次动画
                        adapter.isFirstOnly(false);
                        AddSwipeListener();
                    }
                    break;
                case NO_INTERNET://没有网络
                    Toast.makeText(getContext(),"没有网络",Toast.LENGTH_SHORT).show();
                    break;
                case ADD_CITY://添加城市
                    if(OldMultiCityNum==0)
                    {
                        adapter = new MultiCityAdapter(R.layout.item_multi_city,data,getContext());
                        mRecyclerView.setAdapter(adapter);
                        adapter.openLoadAnimation(SetAnim(MySharedpreference.preferences.getInt("Multi_anim",0)));
                        //不只执行一次动画
                        adapter.isFirstOnly(false);
                        mLayout.setVisibility(View.GONE);
                        AddSwipeListener();
                    }
                    else adapter.notifyDataSetChanged();
                    break;
                case UPDATA://刷新数据
                    MultiCityNum--;
                    if(MultiCityNum==0)
                    {
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                    break;
            }
        }
    };

    private void AddSwipeListener() {
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }
            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                int CityNum  = MySharedpreference.preferences.getInt("MultiCityNum",0);
                CityNum--;
                editor.putInt("MultiCityNum",CityNum);
                editor.remove("MultiCityWeather"+MultiCityNum);
                editor.remove("MultiCity"+MultiCityNum);
                editor.commit();
                Log.d("cc", "clearView: ");
                if(CityNum==0) mLayout.setVisibility(View.VISIBLE);//显示没有城市
            }
            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_multicity, container, false);
            ButterKnife.bind(this, view);
            initView();
            data = new ArrayList();
            editor = MySharedpreference.getInstance(getContext());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            MultiCityNum = MySharedpreference.preferences.getInt("MultiCityNum",0);
            if(MultiCityNum==0)//没有更多城市
            {
                handler.sendEmptyMessage(NO_MORE_CITIS);
            }
            else if(JugeData())
            {
                for (int i=1;i<=MultiCityNum;i++)
                {
                    String weatherInfo = MySharedpreference.preferences.getString("MultiCityWeather"+i,null);
                    Gson g = new Gson();
                    data.add(g.fromJson(weatherInfo,NewWeatherBean.class));
                    handler.sendEmptyMessage(HAVE_MORE_CITIS);
                }
            }
            else
            {
                if(NetState.isNetworkAvailable(getContext()))
                {
                    for(int i=1;i<=MultiCityNum;i++)
                    {
                        String MultiCityName = MySharedpreference.preferences.getString("MultiCity"+i,null);
                        initWeatherInfo(MultiCityName,HAVE_MORE_CITIS);
                    }

                }
                else handler.sendEmptyMessage(NO_INTERNET);//没有网络
            }
        }
        return view;
    }

    private void initView() {
        //设置刷新progressbar颜色
        mRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.YELLOW,Color.RED);
        //设置刷新监听
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(NetState.isNetworkAvailable(getContext()))
                {
                    data.clear();//清空数据
                    MultiCityNum = MySharedpreference.preferences.getInt("MultiCityNum",0);
                    if(MultiCityNum==0)
                    {
                        handler.sendEmptyMessage(NO_MORE_CITIS);
                        mRefreshLayout.setRefreshing(false);
                    }
                    else
                    {
                        for(int i=1;i<=MultiCityNum;i++)
                        {
                            String MultiCityName = MySharedpreference.preferences.getString("MultiCity"+i,null);
                            initWeatherInfo(MultiCityName,UPDATA);
                        }
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
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

    private void initWeatherInfo(String city, final int code)
    {
       if(city!=null)
       {
           RestClient.builder()
                   .url("https://free-api.heweather.com/s6/weather?parameters")
                   .params("location",city)
                   .params("key","b844972b249244019f2afb19e1f3c889")
                   .context(getContext())
                   .success(new ISuccess() {
                       @Override
                       public void onSuccess(String response) {
                           Log.d("cc","MultiCityWeather"+response);
                           Gson g = new Gson();
                           mNewWeatherBean = g.fromJson(response,NewWeatherBean.class);
                           if(mNewWeatherBean.getHeWeather6().get(0).getStatus().equals("ok"))
                           {
                               data.add(mNewWeatherBean);
                               editor.putString("MultiCityWeather"+MultiCityNum,response);
                               editor.commit();
                               handler.sendEmptyMessage(code);
                           }
                           else
                           {
                               int CityNum  = MySharedpreference.preferences.getInt("MultiCityNum",0);
                               CityNum--;
                               editor.putInt("MultiCityNum",CityNum);
                               editor.remove("MultiCityWeather"+MultiCityNum);
                               editor.remove("MultiCity"+MultiCityNum);
                               editor.commit();
                               if(CityNum==0) mLayout.setVisibility(View.VISIBLE);//显示没有城市
                           }

                       }
                   })
                   .failure(new IFailure() {
                       @Override
                       public void onFailure() {
                           Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                           mRefreshLayout.setRefreshing(false);
                       }
                   })
                   .error(new IError() {
                       @Override
                       public void onError(int code, String msg) {
                           Toast.makeText(getContext(),"加载错误code"+code,Toast.LENGTH_SHORT).show();
                           mRefreshLayout.setRefreshing(false);
                       }
                   })
                   .build()
                   .post();
       }
    }
    //判断储存的数据是不是今天的
    public boolean JugeData()
    {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = mDateFormat .format(new Date());
        String SharedDate = MySharedpreference.preferences.getString("Date_Multi",null);
        if(SharedDate==null||!date.equals(SharedDate))
        {
            editor.putString("Date_Multi",date);
            editor.commit();
            return false;
        }
        else if(SharedDate.equals(SharedDate))
        {
            return true;
        }
        return false;
    }

    public void UpDataUi()
    {
        MultiCityNum = MySharedpreference.preferences.getInt("MultiCityNum",0);
        OldMultiCityNum = MultiCityNum - 1;
        if(NetState.isNetworkAvailable(getContext()))
        {
            if(MultiCityNum!=0)
            {
                String MultiCityName = MySharedpreference.preferences.getString("MultiCity"+MultiCityNum,null);
                initWeatherInfo(MultiCityName,ADD_CITY);
            }
            else handler.sendEmptyMessage(NO_MORE_CITIS);//没有更多城市
        }
        else handler.sendEmptyMessage(NO_INTERNET);//没有网络
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
