package com.jc.lottery.fragment.lottery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jc.lottery.R;
import com.jc.lottery.adapter.betting.BettingRecordAdapter;
import com.jc.lottery.adapter.betting.LotteryRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.resp.Resp_36x7_history;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class K3LotteryRecordFragment extends BaseFragment {


    @BindView(R.id.rel_betting)
    RecyclerView relBetting;
    private List<Resp_36x7_history.DrawListBean> list = new ArrayList<Resp_36x7_history.DrawListBean>();
    private LotteryRecordAdapter recordAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_betting_record;
    }

    @Override
    protected void initView(View view) {
        relBetting.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        relBetting.setAdapter(recordAdapter);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }


}
