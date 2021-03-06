package com.jc.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.ActivationListActivity;
import com.jc.lottery.activity.immediate.DeliveryRecordsActivity;
import com.jc.lottery.activity.immediate.ImmediatelActivationActivity;
import com.jc.lottery.activity.immediate.ImmediatelSettlementActivity;
import com.jc.lottery.activity.immediate.LotteryPurchaseActivity;
import com.jc.lottery.activity.immediate.OrderTrackActivity;
import com.jc.lottery.activity.immediate.PaymentRecordActivity;
import com.jc.lottery.activity.immediate.ReceivingRecordsActivity;
import com.jc.lottery.activity.immediate.StatisticsAmountActivity;
import com.jc.lottery.activity.immediate.StatisticsAmountsActivity;
import com.jc.lottery.activity.money.RechargeNewActivity;
import com.jc.lottery.activity.my.MyImmediateActivity;
import com.jc.lottery.activity.scanner.ManualScannerActivity;
import com.jc.lottery.bean.MyImmediateBean;
import com.jc.lottery.fragment.MyLotteryFragment;

import java.util.List;

/**
 * Created by Administrator on 2019/9/23.
 */

public class MyImmediatesAdapter extends RecyclerView.Adapter<MyImmediateHolderView> {

    private List<MyImmediateBean> list;
    private LayoutInflater mInflater;
    private MyLotteryFragment fragment = null;
    private Context mContext = null;
    private String type = "0";

    public MyImmediatesAdapter(MyLotteryFragment fragment, Context mContext, String type) {
        this.fragment = fragment;
        this.mContext = mContext;
        this.type = type;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MyImmediateBean> list) {
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
    public MyImmediateHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_immediates_item, parent, false);
        MyImmediateHolderView holder = new MyImmediateHolderView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyImmediateHolderView holder, final int position) {
        String bean = list.get(position).getName();
        if (bean != null) {
            holder.tvMyImmediateItemName.setText(bean);
        }
        if (list.get(position).isState()){
            holder.imgMyImmediateItemLock.setVisibility(View.VISIBLE);
        }else {
            holder.imgMyImmediateItemLock.setVisibility(View.GONE);
        }
        holder.llyMyImmediateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isState()){
                    fragment.showPopView(position,1,false,"",type);
                }else {
                    showIntent(position);
                }
            }
        });
        holder.llyMyImmediateItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (list.get(position).isState()){
                    fragment.showLockView(position,false,list.get(position).getAlias(),mContext.getString(R.string.unlock_or_not),type);
                }else {
                    fragment.showLockView(position,true,list.get(position).getAlias(),mContext.getString(R.string.lock_or_not),type);
                }
                return true;
            }
        });
        holder.imgMyImmediateItemIcon.setImageResource(list.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showIntent(int pos){
        Intent intent = new Intent();
        switch (list.get(pos).getId()){
            case 1:
                intent.setClass(mContext, ManualScannerActivity.class);
                mContext.startActivity(intent);
                break;
            case 2:
                intent.setClass(mContext, ImmediatelActivationActivity.class);
                mContext.startActivity(intent);
                break;
            case 3:
                intent.setClass(mContext, ImmediatelSettlementActivity.class);
                mContext.startActivity(intent);
                break;
            case 4:
                intent.setClass(mContext, LotteryPurchaseActivity.class);
                mContext.startActivity(intent);
                break;
            case 5:
                intent.setClass(mContext, ReceivingRecordsActivity.class);
                mContext.startActivity(intent);
                break;
            case 6:
//                fragment.cashRecordIntent();
//                break;
            case 7:
                intent.setClass(mContext, DeliveryRecordsActivity.class);
                mContext.startActivity(intent);
                break;
            case 8:
                intent.setClass(mContext, PaymentRecordActivity.class);
                mContext.startActivity(intent);
                break;
            case 9:
                intent.setClass(mContext, RechargeNewActivity.class);
                mContext.startActivity(intent);
                break;
            case 10:
                intent.setClass(mContext, StatisticsAmountsActivity.class);
                mContext.startActivity(intent);
                break;
            case 11:
                intent.setClass(mContext, OrderTrackActivity.class);
                mContext.startActivity(intent);
                break;
            case 12:
                intent.setClass(mContext, ActivationListActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }

}

