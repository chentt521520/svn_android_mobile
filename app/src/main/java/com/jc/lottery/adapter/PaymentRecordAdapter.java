package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.resp.PaymentBean;
import com.jc.lottery.bean.resp.RewardInfoBean;
import com.jc.lottery.content.Constant;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class PaymentRecordAdapter extends RecyclerView.Adapter<PaymentRecordHolderView> {

    private List<PaymentBean.PayListBean> list;
    private LayoutInflater mInflater;
    private Context mContext = null;

    public PaymentRecordAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<PaymentBean.PayListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

    @Override
    public PaymentRecordHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.activation_payment_en_item, parent, false);
        PaymentRecordHolderView holder = new PaymentRecordHolderView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, viewType);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final PaymentRecordHolderView holder, final int position) {
        String bean = list.get(position).getOrderCode();
        if (bean != null) {
            holder.itemName.setText(list.get(position).getOrderCode());
            if (list.get(position).getPayTime() != null && !list.get(position).getPayTime().equals("")) {
                holder.itemTime.setText(mContext.getResources().getString(R.string.time) + timeStamp2Date(list.get(position).getPayTime()));
            } else {
                holder.itemTime.setText(mContext.getResources().getString(R.string.time) + "--");
            }
            if (list.get(position).getPayState().equals("01")) {
                holder.itemState.setText(mContext.getString(R.string.successful_payment));
                holder.itemState.setTextColor(Color.rgb(21, 119, 255));
            } else if (list.get(position).getPayState().equals("02")) {
                holder.itemState.setText(mContext.getString(R.string.failure_to_pay));
                holder.itemState.setTextColor(Color.rgb(255, 102, 0));
            } else if (list.get(position).getPayState().equals("03")) {
                holder.itemState.setText(mContext.getString(R.string.to_be_confirmed));
                holder.itemState.setTextColor(Color.rgb(255, 171, 0));
            } else if (list.get(position).getPayState().equals("00")) {
                holder.itemState.setText(mContext.getString(R.string.no_recharge));
                holder.itemState.setTextColor(Constant.dispatched);
            }
            holder.itemAmount.setText("" + MoneyUtil.getIns().GetMoney(list.get(position).getRechargeAmount()) + mContext.getString(R.string.price_unit));
            holder.itemMoney.setText(mContext.getString(R.string.recharge_moneys) + MoneyUtil.getIns().GetMoney(list.get(position).getPayMoney()) + mContext.getString(R.string.price_unit));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(mContext, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
            format = "dd-MM-yyyy HH:mm:ss";
        } else {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

}

class PaymentRecordHolderView extends RecyclerView.ViewHolder {

    //    @BindView(R.id.lly_payment_item)
//    LinearLayout itemLly;
    @BindView(R.id.tv_payment_item_order)
    TextView itemName;
    @BindView(R.id.tv_payment_item_time)
    TextView itemTime;
    @BindView(R.id.tv_payment_item_state)
    TextView itemState;
    @BindView(R.id.tv_payment_item_money)
    TextView itemMoney;
    @BindView(R.id.tv_payment_item_amount)
    TextView itemAmount;

    public PaymentRecordHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}

