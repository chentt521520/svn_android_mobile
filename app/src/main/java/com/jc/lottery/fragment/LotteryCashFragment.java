package com.jc.lottery.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jc.lottery.R;
import com.jc.lottery.activity.scanner.CashRecordActivity;
import com.jc.lottery.adapter.betting.LotteryRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.fragment.betting.G36x7BettingRecordFragment;
import com.jc.lottery.fragment.betting.G90x5BettingRecordFragment;
import com.jc.lottery.fragment.betting.K3BettingRecordFragment;
import com.jc.lottery.util.ImagePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class LotteryCashFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private String[] mTitle = {"快3","90选5","36选7"};
    // fragment的集合
    private ArrayList<Fragment> list;
    private MyCashFragmentViewPageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lottery_cash;
    }

    @Override
    protected void initView(View view) {
        FragmentManager fm = getChildFragmentManager();
        ImagePagerIndicator imagePagerIndicator = (ImagePagerIndicator) view.findViewById(R.id.image_indicator);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        // 将fragment放进集合，并初始化适配器
        list = new ArrayList<Fragment>();
        list.add(new K3BettingRecordFragment());
        list.add(new G90x5BettingRecordFragment());
        list.add(new G36x7BettingRecordFragment("37x6"));
        adapter = new MyCashFragmentViewPageAdapter(fm,
                list);
        viewPager.setAdapter(adapter);
        imagePagerIndicator.setTabTitles(mTitle,16);
        imagePagerIndicator.setViewPager(viewPager,0);
        imagePagerIndicator.setOnPageChangeListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Log.d("","viewpager onPageSelected"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyCashFragmentViewPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;
        public MyCashFragmentViewPageAdapter(FragmentManager fm,ArrayList<Fragment> list) {
            super(fm);
            this.list=list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
}
