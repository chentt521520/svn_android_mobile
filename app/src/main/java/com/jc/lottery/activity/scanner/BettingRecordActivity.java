package com.jc.lottery.activity.scanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.content.Constant;
import com.jc.lottery.fragment.betting.G36x7BettingRecordFragment;
import com.jc.lottery.fragment.betting.G90x5BettingRecordFragment;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.ImagePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 投注记录页面
 */
public class BettingRecordActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.header_type_one_title)
    TextView headerTypeOneTitle;
    private String[] mTitle = {"5/90", "6/37", "6/49"};
    // fragment的集合
    private ArrayList<Fragment> list;
    private MyFragmentViewPageAdapter adapter;

    FragmentManager fm = getSupportFragmentManager();
    private String game = "90选5";

    @Override
    public void getPreIntent() {
        if (getIntent().hasExtra("game")){
            game = getIntent().getStringExtra("game");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_betting_record;
    }

    @Override
    public void initView() {
        headerTypeOneTitle.setText(getString(R.string.bethistiory));
        ImagePagerIndicator imagePagerIndicator = (ImagePagerIndicator) findViewById(R.id.image_indicator);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        // 将fragment放进集合，并初始化适配器
        list = new ArrayList<Fragment>();
//        list.add(new K3BettingRecordFragment());
        list.add(new G90x5BettingRecordFragment());
        list.add(new G36x7BettingRecordFragment("37x6"));
        list.add(new G36x7BettingRecordFragment("49x6"));
        adapter = new MyFragmentViewPageAdapter(fm,
                list);
        viewPager.setAdapter(adapter);
        imagePagerIndicator.setTabTitles(mTitle, 16);
        imagePagerIndicator.setViewPager(viewPager, 0);
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
        if (!game.equals(Constant.Game_Name_90x5)){
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
        }
    }

    public class MyFragmentViewPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        public MyFragmentViewPageAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("", "容器 onPageSelected" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
