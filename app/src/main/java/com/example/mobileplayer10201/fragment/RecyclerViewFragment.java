package com.example.mobileplayer10201.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mobileplayer10201.NetAudioBean;
import com.example.mobileplayer10201.R;
import com.example.mobileplayer10201.adapter.RecyclerViewAdpater;
import com.example.mobileplayer10201.base.BaseFragment;
import com.example.mobileplayer10201.utils.CacheUtils;
import com.example.mobileplayer10201.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 熊猛 on 2017/1/16.
 */

public class RecyclerViewFragment extends BaseFragment {
    private static final String TAG = RecyclerViewFragment.class.getSimpleName();
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.tv_nomedia)
    TextView tvNomedia;
    private RecyclerViewAdpater myAdapter;
    private List<NetAudioBean.ListBean> datas;
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_recyclerview, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String saveJson = CacheUtils.getString(mContext, Constants.NET_AUDIO_URL);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams reques = new RequestParams(Constants.NET_AUDIO_URL);
        x.http().get(reques, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                CacheUtils.putString(mContext,Constants.NET_AUDIO_URL,result);
                LogUtil.e("onSuccess==" + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onError==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished==");
            }
        });

    }

    private void processData(String json) {
        NetAudioBean netAudioBean = paraseJons(json);
//        LogUtil.e(netAudioBean.getList().get(0).getText()+"--------------");
        datas = netAudioBean.getList();
        if(datas != null && datas.size() >0){
            //有视频
            tvNomedia.setVisibility(View.GONE);
            //设置适配器
            myAdapter = new RecyclerViewAdpater(mContext,datas);
            recyclerview.setAdapter(myAdapter);

            //添加布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        }else{
            //没有视频
            tvNomedia.setVisibility(View.VISIBLE);
        }

        progressbar.setVisibility(View.GONE);



    }

    /**
     * json解析数据
     * @param json
     * @return
     */
    private NetAudioBean paraseJons(String json) {
        return new Gson().fromJson(json,NetAudioBean.class);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onRefreshData() {
        super.onRefreshData();
       // textView.setText("RecyclerView视频刷新");
    }
}
