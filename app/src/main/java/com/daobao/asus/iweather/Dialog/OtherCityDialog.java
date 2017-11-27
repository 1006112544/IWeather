package com.daobao.asus.iweather.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.daobao.asus.iweather.R;

/**
 * Created by db on 2017/11/23.
 */

public class OtherCityDialog extends Dialog implements View.OnClickListener{
    public OtherCityDialog(@NonNull Context context,MyDialogListenner mListener) {
        super(context,R.style.MyDialog);
        this.mListener = mListener;
    }
    private Button mSureButton;
    private Button mCancleButton;
    private EditText mEditText;
    private View mView;
    private MyDialogListenner mListener;
    public interface MyDialogListenner
    {
        void ClickedSure(String cityName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_other_city);
        initView();
    }

    private void initView() {
        mSureButton = findViewById(R.id.dialog_sure);
        mCancleButton = findViewById(R.id.dialog_cancle);
        mEditText = findViewById(R.id.dialog_et_city);
        mView = findViewById(R.id.div_line_city);
        mSureButton.setOnClickListener(this);
        mCancleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.dialog_sure:
                mListener.ClickedSure(mEditText.getText().toString());
                this.cancel();
                break;
            case R.id.dialog_cancle:
                this.cancel();
                break;
        }
    }
}
