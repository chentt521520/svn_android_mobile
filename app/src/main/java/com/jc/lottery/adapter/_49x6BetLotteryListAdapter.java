package com.jc.lottery.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.lottery._37x6BettingActivity;
import com.jc.lottery.base.BaseBetLotteryListAdapter;
import com.jc.lottery.base.ObservableAdapter;
import com.jc.lottery.bean.type.Group;
import com.jc.lottery.bean.type.Payment;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.FormatUtil;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.StringUtils;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.ClearDataDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class _49x6BetLotteryListAdapter extends BaseBetLotteryListAdapter implements ObservableAdapter {
    protected final SimpleDateFormat DATE_FORMAT_ISO8601 = new SimpleDateFormat(
            "yyyy-MM-dd");
    RemoteResourceManagerObserver mResourcesObserver;
    String time;
    private LayoutInflater mInflater;
    private Handler mHandler;

    private Context mContext;

    private TextView tv_money;
    private TextView tv_zhushu001;
    private TextView tv_beishu;
    /**
     * 计算价钱
     */

    private TextView tv_qishu;

    public _49x6BetLotteryListAdapter(Context context, TextView tv_money, TextView tv_zhushu001, TextView tv_beishu, TextView tv_qishu) {
        super(context);
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mHandler = new Handler();
        mResourcesObserver = new RemoteResourceManagerObserver();
        time = getNowAsIso8601();
        this.tv_money = tv_money;
        this.tv_zhushu001 = tv_zhushu001;
        this.tv_beishu = tv_beishu;
        this.tv_qishu = tv_qishu;
    }

    public void removeObserver() {
        //		mRrm.deleteObserver(mResourcesObserver);
    }

    @Override
    public void setGroup(Group<Payment> g) {
        super.setGroup(g);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //http://59.151.89.47:8000/lottery/lotteryterm.html
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.betlottery_item, null);
            holder = new ViewHolder();
            holder.tV_betlottery_mumber = (TextView) convertView.findViewById(R.id.tV_betlottery_mumber);
            holder.tV_betlottery_zhushu = (TextView) convertView.findViewById(R.id.tV_betlottery_zhushu);
            holder.bt_delete = (ImageView) convertView.findViewById(R.id.img_delete);
            holder.tV_betlottery_type = (TextView) convertView.findViewById(R.id.tV_betlottery_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Payment payment = (Payment) group.get(position);
        String price = "";
        if (TextUtils.equals(payment.getZuheType(), "90选5")) {
            holder.tV_betlottery_type.setText(R.string.s90x5name);
            price = FormatUtil.addComma(payment.getBetting() * Config.s90x5_R007_NoteMoney_min);
        } else if (TextUtils.equals(payment.getZuheType(), "37选6")) {
            holder.tV_betlottery_type.setText(R.string.s37x6name);
            price = FormatUtil.addComma(payment.getBetting() * Config.s37x6_R007_NoteMoney_min);
        } else {
            holder.tV_betlottery_type.setText(payment.getZuheType());
            price = FormatUtil.addComma(payment.getBetting() * Config.s49x6_R007_NoteMoney_min);
        }
//        String price = FormatUtil.addComma(payment.getBetting() * Config.OneBetPrice);

        holder.tV_betlottery_zhushu.setText(" · " + payment.getBetting() + mContext.getString(R.string.zhu) + " · " + MoneyUtil.getIns().GetMoney(price) + mContext.getString(R.string.price_unit));

        holder.bt_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (group == null || group.size() <= 1) {
                    ToastUtils.showShort(mContext.getString(R.string.minkeepOne));
                } else {
                    showClearDataDialog(mContext.getString(R.string.deleteNumber), position, group);
                }
            }
        });
        String code = payment.getMumber();
        String zuheType = payment.getZuheType();
        if (!StringUtils.isEmpty(code)) {
            String ball = null;
            String ball1 = null;
            if ("三连号通选".equals(zuheType)) {
                ball = "123 234 345 456";
            } else if ("三同号通选".equals(zuheType)) {
                ball = "111 222 333 444 555 666";
//			} else if("二不同号".equals(zuheType)) {
//				ball = code + "*";
            } else {
                String[] strBall = code.split("\\|");
                for (int i = 0; i < strBall.length; i++) {
                    if (i == 0) {
                        ball = strBall[0];
                        ball = ball.replace(",", " ");
                    } else if (i == 1) {
                        ball1 = strBall[1];
//                        ball1 = ball1.replace(",", " ");
                    }

                }
            }
            if (ball != null && ball1 != null) {
                holder.tV_betlottery_mumber.setText(Html.fromHtml("<font color='#00923d'>" + ball + "</font>" +
                        "<font color='#525252'>" + " |" + "</font>" + "<font color='#0d88ce'> " + ball1 + "</font>"));
            } else {
                holder.tV_betlottery_mumber.setText(Html.fromHtml("<font color='#00923d'>" + ball + "</font>"));
            }
        }


        return convertView;
    }

    /**
     * 清空数据弹窗
     */
    private void showClearDataDialog(String content, final int position, final Group<Payment> group) {
        final ClearDataDialog dialog = new ClearDataDialog(mContext, R.style.MyDialog2);

        Button clear_data_dialog_tv_yes = (Button) dialog.findViewById(R.id.clear_data_dialog_tv_yes);
        Button clear_data_dialog_tv_no = (Button) dialog.findViewById(R.id.clear_data_dialog_tv_no);
        TextView clear_data_dialog_tv_content = (TextView) dialog.findViewById(R.id.clear_data_dialog_tv_content);

        clear_data_dialog_tv_content.setText(content);

        Window dialogWindow = dialog.getWindow();
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);

        dialog.show();
        Payment payment = (Payment) group.get(position);
        int prices = 0;
        if (TextUtils.equals(payment.getZuheType(), "90选5")) {
            prices = Config.s90x5_R007_NoteMoney_min;
        } else if (TextUtils.equals(payment.getZuheType(), "37选6")) {
            prices = Config.s37x6_R007_NoteMoney_min;
        }else {
            prices = Config.s49x6_R007_NoteMoney_min;
        }
        final int finalPrices = prices;
        clear_data_dialog_tv_yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                group.remove(position);
                tv_zhushu001.setText(String.valueOf(getNum()));
                calcPrice(finalPrices / 100);
                _49x6BetLotteryListAdapter.this.notifyDataSetChanged();
                dialog.dismiss();
                try {
                    _37x6BettingActivity.instances.setAlpha(group);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        clear_data_dialog_tv_no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void calcPrice(int price) {
        int numZhuiHao = 1;
        int numBei = 1;
        try {
            numZhuiHao = Integer.parseInt(tv_qishu.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            numZhuiHao = 1;
        }
        try {
            numBei = Integer.parseInt(tv_beishu.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            numBei = 1;
        }
//        String addComma = FormatUtil.addComma(getNum() * Config.OneBetPrice * numBei * numZhuiHao);
        String addComma = FormatUtil.addComma(getNum() * price * numBei * numZhuiHao);
        tv_money.setText(addComma);
    }

    public int getNum() {
        int num = 0;
        for (int i = 0; i < group.size(); i++) {
            Payment p = (Payment) group.get(i);
            num += p.getBetting();
        }
        return num;
    }

    private String getNowAsIso8601() {
        return DATE_FORMAT_ISO8601.format(new Date(System.currentTimeMillis()));
    }

    private static class ViewHolder {
        TextView tV_betlottery_mumber;
        TextView tV_betlottery_zhushu;
        ImageView bt_delete;
        TextView tV_betlottery_type;
    }

    private class RemoteResourceManagerObserver implements Observer {
        @Override
        public void update(Observable observable, Object data) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

}