package com.jc.lottery.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.lottery.util.AppLanguageUtils;
import com.jc.lottery.util.Density;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ 创建时间: 2017/12/3 on 10:04.
 * @ 描述：BaseFragment 基类
 * @ 作者: vchao
 */

public abstract class BaseFragment extends Fragment {

    public Activity mActivity;
    public View mRootView;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getPreIntent();
        setOrientation();
        if (mRootView == null) {
            mRootView = View.inflate(mActivity, getLayoutId(), null);
            unbinder = ButterKnife.bind(this, mRootView);
            String language = SPUtils.look(mActivity, SPkey.Language);
            if (TextUtils.equals(language, "Chinese")) {
                AppLanguageUtils.changeAppLanguage(mActivity, Locale.CHINA);
            } else {
                AppLanguageUtils.changeAppLanguage(mActivity, Locale.ENGLISH);
            }
            AppLanguageUtils.getAppLanguage(getActivity());
            initView(mRootView);
        }

        return mRootView;
    }

    /**
     * @return 布局文件id
     */
    public abstract int getLayoutId();

    //view的初始化
    protected abstract void initView(View view);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppLanguageUtils.getAppLanguage(getActivity());
        initData();
        initListener();
    }

    /**
     * 初始化数据
     */
    public void initData() {
    }

    /**
     * 初始化监听器
     */
    public void initListener() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    /**
     * 延迟加载
     */
    protected void lazyLoad() {
    }

    /**
     * 获取 上一个页面 传的数据
     */
    public void getPreIntent() {
    }

    public void setOrientation(){
        Density.setDefault(getActivity());
    }
}