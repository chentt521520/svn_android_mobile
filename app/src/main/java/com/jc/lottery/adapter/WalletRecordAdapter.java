package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.WalletListBean;
import com.jc.lottery.bean.resp.PaymentBean;
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

public class WalletRecordAdapter extends RecyclerView.Adapter<WalletRecordHolderView> {

    private List<WalletListBean> list;
    private LayoutInflater mInflater;
    private Context mContext = null;

    public WalletRecordAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<WalletListBean> list) {
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
    public WalletRecordHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.wallet_record_en_item, parent, false);
        WalletRecordHolderView holder = new WalletRecordHolderView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v, viewType);

                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final WalletRecordHolderView holder, final int position) {
        String bean = list.get(position).getWalleType();
        if (bean != null) {
//            holder.itemName.setText(list.get(position).getChannel());
//            if (list.get(position).getChannel().equals("0")) {
//                holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.terminal));
//            } else if (list.get(position).getChannel().equals("1")) {
//                holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.third_party_app));
//            } else if (list.get(position).getChannel().equals("2")) {
//                holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.block_chain));
//            } else {
//                holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.mobile_end));
//            }
//            holder.itemNum.setText(list.get(position).getIndex() +  ". ");
//            holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.mobile_end));
//            holder.itemTime.setText(mContext.getString(R.string.remarks) + ":" + list.get(position).getRemark());
            holder.itemTime.setText(list.get(position).getIndex() +  ". " + list.get(position).getRemark());
            if (list.get(position).getType().equals("00")) {
                holder.itemRMoney.setText(mContext.getString(R.string.income));
                holder.itemState.setText("+" + MoneyUtil.getIns().GetMoney(list.get(position).getMoney()) + mContext.getString(R.string.price_unit));
//                holder.itemState.setTextColor(Constant.success);
            }else {
                holder.itemRMoney.setText(mContext.getString(R.string.expenditure));
                holder.itemState.setText("-" + MoneyUtil.getIns().GetMoney(list.get(position).getMoney()) + mContext.getString(R.string.price_unit));
//                holder.itemState.setTextColor(Constant.dispatched);
            }
            holder.itemMoney.setText(timeStamp2Date(Long.parseLong(list.get(position).getCreateTime())));
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

class WalletRecordHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_payment_item_num)
    TextView itemNum;
    @BindView(R.id.tv_payment_item_order)
    TextView itemName;
    @BindView(R.id.tv_payment_item_time)
    TextView itemTime;
    @BindView(R.id.tv_wallet_record_money)
    TextView itemRMoney;
    @BindView(R.id.tv_payment_item_state)
    TextView itemState;
    @BindView(R.id.tv_payment_item_money)
    TextView itemMoney;

    public WalletRecordHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}