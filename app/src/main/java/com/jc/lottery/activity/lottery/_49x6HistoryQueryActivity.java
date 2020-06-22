package com.jc.lottery.activity.lottery;

import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter._36x7HistoryAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_Screen37x6History;
import com.jc.lottery.bean.resp.Resp_36x7_history;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 49 选 6 近期开奖查询
 */
public class _49x6HistoryQueryActivity extends BaseActivity {
    @BindView(R.id.lv_37x6_history)
    ListView lv37x6History;
    @BindView(R.id.header_type_one_back)
    LinearLayout headerTypeOneBack;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;

    private List<Resp_36x7_history.DrawListBean> datas;
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_37x6_history;
    }

    @Override
    public void initData() {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
        datas = new ArrayList<>();
        getHttp();
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
                getHttp();
                srlReceiving.resetNoMoreData();
            }
        });
    }

    private void getHttp(){
        String account_name = SPUtils.look(_49x6HistoryQueryActivity.this, SPkey.username);
        pos_Screen37x6History.ColorPeriodInfo colorPeriodInfo = new pos_Screen37x6History.ColorPeriodInfo("49x6");
        pos_Screen37x6History.DataBean dataBean = new pos_Screen37x6History.DataBean(colorPeriodInfo);
        pos_Screen37x6History screen_k3_history = new pos_Screen37x6History(account_name, dataBean);
        String s = new Gson().toJson(screen_k3_history);
        LogUtils.e("  请求参数  " + s);
        OkGo.<String>post(MyUrl.screen_49x6_History)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (ProgressUtil.getProgressDialog() == null) {
                            ProgressUtil.showProgressDialog(_49x6HistoryQueryActivity.this, getString(R.string.waitting));
                        }
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        srlReceiving.finishRefresh();
                        LogUtils.e(" 返回内容 " + response.body());
                        Resp_36x7_history resp_kuai3_history = new Gson().fromJson(response.body(), Resp_36x7_history.class);
                        datas.clear();
                        for (Resp_36x7_history.DrawListBean drawListBean : resp_kuai3_history.getDrawList()) {
                            datas.add(drawListBean);
                        }
                        _36x7HistoryAdapter adapter = new _36x7HistoryAdapter(_49x6HistoryQueryActivity.this, datas);
                        lv37x6History.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        srlReceiving.finishRefresh();
                        if (codeType){
                            finish();
                        }
                    }
                });
    }

    @OnClick(R.id.header_type_one_back)
    public void onViewClicked() {
        finish();
    }
}
