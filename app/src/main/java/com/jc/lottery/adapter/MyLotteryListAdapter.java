package com.jc.lottery.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryRecordsActivity;
import com.jc.lottery.activity.immediate.ImmediatelActivationActivity;
import com.jc.lottery.activity.immediate.ImmediatelSettlementActivity;
import com.jc.lottery.activity.immediate.LotteryPurchaseActivity;
import com.jc.lottery.activity.immediate.PaymentRecordActivity;
import com.jc.lottery.activity.immediate.ReceivingRecordsActivity;
import com.jc.lottery.activity.immediate.StatisticsAmountActivity;
import com.jc.lottery.activity.lottery.LotteryPaymentRecordActivity;
import com.jc.lottery.activity.lottery.LotterySettlementActivity;
import com.jc.lottery.activity.lottery.LotterySettlementsActivity;
import com.jc.lottery.activity.lottery.LottoCashRecordActivity;
import com.jc.lottery.activity.lottery.LottoStatisticsAmountActivity;
import com.jc.lottery.activity.lottery.RechargeLotteryActivity;
import com.jc.lottery.activity.money.RechargeNewActivity;
import com.jc.lottery.activity.my.MyImmediateActivity;
import com.jc.lottery.activity.my.MyLottoryActivity;
import com.jc.lottery.activity.scanner.BettingRecordActivity;
import com.jc.lottery.activity.scanner.CashRecordActivity;
import com.jc.lottery.activity.scanner.ManualScannerActivity;
import com.jc.lottery.bean.MyImmediateBean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/23.
 */

public class MyLotteryListAdapter extends RecyclerView.Adapter<MyImmediateHolderView> {

    private List<MyImmediateBean> list;
    private LayoutInflater mInflater;
    private MyLottoryActivity mContext = null;

    public MyLotteryListAdapter(MyLottoryActivity context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
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
        View view = mInflater.inflate(R.layout.my_immediate_item, parent, false);
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
                    mContext.showPopView(position,1,false,"");
                }else {
                    showIntent(position);
                }
            }
        });
        holder.llyMyImmediateItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (list.get(position).isState()){
                    mContext.showLockView(position,false,list.get(position).getAlias(),mContext.getString(R.string.unlock_or_not));
                }else {
                    mContext.showLockView(position,true,list.get(position).getAlias(),mContext.getString(R.string.lock_or_not));
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
                intent.setClass(mContext, LotteryPaymentRecordActivity.class);
                mContext.startActivity(intent);
                break;
            case 2:
                intent.setClass(mContext, LottoCashRecordActivity.class);
                mContext.startActivity(intent);
                break;
            case 3:
                intent.setClass(mContext, BettingRecordActivity.class);
                mContext.startActivity(intent);
                break;
            case 4:
                intent.setClass(mContext, LotterySettlementsActivity.class);
                mContext.startActivity(intent);
                break;
            case 5:
                intent.setClass(mContext, RechargeLotteryActivity.class);
                mContext.startActivity(intent);
                break;
            case 6:
                intent.setClass(mContext, LottoStatisticsAmountActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }

}

