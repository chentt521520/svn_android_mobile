package com.jc.lottery.activity.lottery;

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
import com.jc.lottery.fragment.LotterySettlementRecordFragment;
import com.jc.lottery.fragment.SettlementRecordFragment;
import com.jc.lottery.util.ImagePagerIndicator;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 结算列表页
 */
public class SettlementListActivity extends BaseActivity implements ViewPager.OnPageChangeListener{


    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.header_type_one_title)
    TextView headerTypeOneTitle;
    private ViewPager viewPager;
    private String[] mTitle;
    // fragment的集合
    private ArrayList<LotterySettlementRecordFragment> list;
    private MySettlementViewPageAdapter adapter;

    FragmentManager fm = getSupportFragmentManager();

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement_viewpage;
    }

    @Override
    public void initView() {
        ImagePagerIndicator imagePagerIndicator = (ImagePagerIndicator) findViewById(R.id.image_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (SPUtils.look(this,SPkey.roleAlias).equals("fxs")){
            mTitle = new String[]{getString(R.string.settlement_records)};
            imagePagerIndicator.setVisibility(View.GONE);
        }else {
            mTitle = new String[]{getString(R.string.settlement_records), getString(R.string.audit_record)};
        }
        headerTypeOneTitle.setText(getString(R.string.settlement_records));
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        nameList = getIntent().getStringArrayListExtra("nameList");
        // 将fragment放进集合，并初始化适配器
        list = new ArrayList<LotterySettlementRecordFragment>();
        for (int i = 0; i < mTitle.length; i++) {
            if (i == 0) {
                list.add(new LotterySettlementRecordFragment("00"));
            }else {
                list.add(new LotterySettlementRecordFragment("01"));
            }
        }
        adapter = new MySettlementViewPageAdapter(fm,
                list);
        viewPager.setAdapter(adapter);
        imagePagerIndicator.setDEFAULT_TAB_VISABLE_COUNT(mTitle.length);
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
    }

    @Override
    public void initData() {

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }

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

    public class MySettlementViewPageAdapter extends FragmentPagerAdapter {

        private List<LotterySettlementRecordFragment> list;

        public MySettlementViewPageAdapter(FragmentManager fm, ArrayList<LotterySettlementRecordFragment> list) {
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
