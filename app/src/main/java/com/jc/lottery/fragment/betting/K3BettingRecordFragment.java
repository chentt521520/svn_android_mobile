package com.jc.lottery.fragment.betting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.lottery.R;
import com.jc.lottery.adapter.betting.BettingRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.resp.BettingListInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class K3BettingRecordFragment extends BaseFragment {


    @BindView(R.id.rel_betting)
    RecyclerView relBetting;
    private List<BettingListInfo> list = new ArrayList<BettingListInfo>();
    private BettingRecordAdapter recordAdapter = null;

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
        showList();
        relBetting.setAdapter(recordAdapter);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }

    private void showList(){
//        for (int i = 0; i < 20; i++) {
//            list.add("No." + (45564121 + i));
//        }
//        recordAdapter = new BettingRecordAdapter(getActivity());
//        recordAdapter.setList(list);
    }

}
