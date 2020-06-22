package com.jc.lottery.activity.lottery;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.MainFragmentTabActivity;
import com.jc.lottery.activity.scanner.BettingRecordActivity;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.resp.Resp_Order_Success;
import com.jc.lottery.bean.type.Betting;
import com.jc.lottery.content.Constant;
import com.jc.lottery.util.StringUtils;
import com.jc.lottery.util.TransfromUtils;

/**
 * 投注成功页面
 */
public class BettingSuccessActivity extends BaseActivity implements OnClickListener {
    private TextView tV_successqiu;
    private TextView tV_successqihao;
    private TextView tV_successtouzhu;
    private TextView tV_successmylottery;
    private TextView tV_successjixulottery;
    private LinearLayout ll_successfenxiang;
    private LinearLayout ll_successmylottery;
    private LinearLayout ll_successjixulottery;
    private String strType;
    private boolean yincang;
    private Betting betting;
    private Resp_Order_Success resp_order_success;
    private String gameAlias;

    @Override
    public int getLayoutId() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.bettingsuccess;
    }

    @Override
    public void getPreIntent() {
        Intent intent = getIntent();
        yincang = intent.getBooleanExtra("yincang", false);
        betting = (Betting) intent.getSerializableExtra("betting");
        try {
            resp_order_success = (Resp_Order_Success) intent.getSerializableExtra("info");
            gameAlias = resp_order_success.getData().getOrderInfo().getGameAlias();
        } catch (Exception e) {
            e.printStackTrace();
        }
        strType = intent.getStringExtra("strType");
    }

    @Override
    public void initView() {
        tV_successqiu = (TextView) findViewById(R.id.tV_successqiu);
        tV_successqihao = (TextView) findViewById(R.id.tV_successqihao);
        tV_successtouzhu = (TextView) findViewById(R.id.tV_successtouzhu);
        tV_successmylottery = (TextView) findViewById(R.id.tV_successmylottery);
        tV_successjixulottery = (TextView) findViewById(R.id.tV_successjixulottery);
        ll_successfenxiang = (LinearLayout) findViewById(R.id.ll_successfenxiang);
        ll_successmylottery = (LinearLayout) findViewById(R.id.ll_successmylottery);
        ll_successjixulottery = (LinearLayout) findViewById(R.id.ll_successjixulottery);

        LinearLayout ll_yincang = (LinearLayout) findViewById(R.id.ll_yincang);
        if (yincang) {
            ll_yincang.setVisibility(View.GONE);
        }
        ensureUi();
    }

    public boolean isExist(String username, String password) {
        return !StringUtils.isEmpty(username) && !StringUtils.isEmpty(password);
    }

    @Override
    public void initListener() {
        ll_successfenxiang.setOnClickListener(this);
        ll_successmylottery.setOnClickListener(this);
        ll_successjixulottery.setOnClickListener(this);
    }

    public void ensureUi() {
        String gamename = TransfromUtils.GameAlias2Name(BettingSuccessActivity.this, resp_order_success.getData().getOrderInfo().getGameAlias(), "彩票");
        tV_successqiu.setText(gamename);

        tV_successqihao.setText(String.format(getString(R.string.qici_no), resp_order_success.getData().getOrderInfo().getDrawNumber()));
        tV_successtouzhu.setText(R.string.print_scuess);
        tV_successmylottery.setText("投注总额：" + resp_order_success.getData().getOrderInfo().getTotalMoney() + getString(R.string.price_unit));
        tV_successjixulottery.setText("账户余额" + " XXX " + getString(R.string.price_unit));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_successfenxiang:
                String emailBody = "我刚用霁彩客户端买了彩票，你也来试试吧 \n"
                        + strType
                        + "："
                        + betting.getCodes().replace("fs-", "").replace("^", "   ")
                        .replace("zs_bh-", "").replace("zl_bh-", "")
                        + "\n 下载 http://www.yicp.com/app/android/CaiSoLottery.apk";

                // 系统邮件系统的动作为android.content.Intent.ACTION_SEND
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("text/plain");

			/*
             * //设置邮件默认地址 email.putExtra(android.content.Intent.EXTRA_EMAIL,
			 * emailReciver); //设置邮件默认标题
			 * email.putExtra(android.content.Intent.EXTRA_SUBJECT,
			 * emailSubject);
			 */
                // 设置要默认发送的内容
                email.putExtra(Intent.EXTRA_TEXT, emailBody);
                //email.putExtra(Intent.EXTRA_HTML_TEXT, "http://www.yicp.com/app/android/CaiSoLottery.apk");
                // 调用系统的邮件系统
                startActivity(Intent.createChooser(email, "分享到这里"));
                break;
            case R.id.ll_successmylottery:
                Intent intent1 = new Intent(BettingSuccessActivity.this, BettingRecordActivity.class);
                intent1.putExtra("game", strType);
                startActivity(intent1);
                finish();
                break;
            case R.id.ll_successjixulottery:
                if (TextUtils.equals(Constant.GAME_ALIAS_90X5, gameAlias)) {
                    Intent intent = new Intent(BettingSuccessActivity.this, s90x5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
                } else if (TextUtils.equals(Constant.GAME_ALIAS_37X6, gameAlias)) {
                    Intent intent = new Intent(BettingSuccessActivity.this, _37x6SelectNumActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    Intent intent = new Intent(BettingSuccessActivity.this, MainFragmentTabActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    BettingSuccessActivity.this.finish();
                }
//                else if (TextUtils.equals(Constant.GAME_ALIAS_K3, gameAlias)) {
//                    Intent intent = new Intent(BettingSuccessActivity.this, Kuai3Activity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
                break;
        }
    }

}
