package com.example.mobileplayer10201.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.mobileplayer10201.base.BaseFragment;

/**
 * Created by 熊猛 on 2017/1/16.
 */

public class LocalAudioFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("本地音频");
    }
    @Override
    public void onRefreshData() {
        super.onRefreshData();
        textView.setText("本地音频刷新");
    }
}
