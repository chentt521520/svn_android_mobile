package com.jc.lottery.activity.money;

import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值页面
 */
public class RechargeNewDetailActivity extends BaseActivity {

    @BindView(R.id.tv_recharge_detail_title)
    TextView tvRechargeDetailTitle;
    @BindView(R.id.wv_recharge_detail)
    WebView wvRechargeDetail;
    @BindView(R.id.lly_recharge_detail_back)
    LinearLayout llyRechargeDetailBack;
    private WebSettings webSettings;
    private String url = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_new_detail;
    }

    @Override
    public void initData() {
        url = getIntent().getStringExtra("url");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webSettings = wvRechargeDetail.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setAllowContentAccess(true);
                webSettings.setAppCacheEnabled(false);
                webSettings.setBuiltInZoomControls(false);
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                wvRechargeDetail.loadUrl(url);
                wvRechargeDetail.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view,
                                                            WebResourceRequest request) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            view.loadUrl(request.getUrl().toString());
                        } else {
                            view.loadUrl(request.toString());
                        }
                        return true;
                    }
                });
            }
        });

    }

    @OnClick(R.id.lly_recharge_detail_back)
    public void onViewClicked() {
        finish();
    }
}
