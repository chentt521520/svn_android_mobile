package com.jc.lottery.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.View;

import com.jc.lottery.R;
import com.jc.lottery.base.application.LotteryApplication;
import com.jc.lottery.inter.TitleListener;
import com.jc.lottery.util.AppLanguageUtils;
import com.jc.lottery.util.Density;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity  基类
 * 无特殊需求时，使用此BaseActivity
 */
public abstract class BaseActivity extends FragmentActivity {

    Unbinder mUnbinder;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppLanguageUtils.getAppLanguage(this);
        String language = SPUtils.look(this, SPkey.Language);
        if (TextUtils.equals(language, "Chinese")) {
            AppLanguageUtils.changeAppLanguage(this, Locale.CHINA);
        } else {
            AppLanguageUtils.changeAppLanguage(this, Locale.ENGLISH);
        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        ActivityCollector.addActivity(this);
//        LotteryApplication.getInstance().addActivity(this);
        LogUtils.e(getClass().getName() + "-----------onCreate");
//        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
//        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
//        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
//        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
//        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
//        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.pull_up_load_more);
//        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.release_load_now);
//        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.xlistview_header_hint_loading);
//        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.its_refreshing);
//        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.load_complete);
//        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.failed_to_load);
//        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = getString(R.string.no_more);
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        setOrientation();
        //得到布局文件
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        setStatusBar();
        getPreIntent();
//        initTitleBar();
        initView();
        initData();
        initListener();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.ani_top_get_into,R.anim.ani_bottom_sign_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_top_get_intos,R.anim.ani_bottom_sign_outs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        overridePendingTransition(R.anim.ani_bottom_sign_out,R.anim.ani_top_get_into);
        mUnbinder.unbind();
//        ActivityCollector.removeActivity(this);
    }

    /**
     * @return 布局文件id
     */
    public abstract int getLayoutId();

    /**
     * 获取上一个页面传递来的intent数据
     */
    public void getPreIntent() {
    }

    /**
     * 初始化View
     */
    public void initView() {
    }

    /**
     * 初始化界面数据
     */
    public void initData() {
    }

    /**
     * 绑定监听器与适配器
     */
    public void initListener() {
    }

//    public void initTitleBar(final TitleListener titleListener) {
//        try {
//            findViewById(R.id.header_type_one_right).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    titleListener.RightClick();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void initTitleBar() {
//        try {
//            findViewById(R.id.header_type_one_back).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(AppLanguageUtils.wrapContext(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    public void setOrientation(){
        Density.setDefault(this);
    }
    protected void setStatusBar() {
//        StatusBarUtil.setTranslucent(this,255);
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_color));
    }
}