package com.jc.lottery.adapter.betting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.ImmediateCashRecordActivity;
import com.jc.lottery.activity.scanner.RewardRecordDetailActivity;
import com.jc.lottery.bean.resp.RewardInfoBean;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class MyImmediateRecordAdapter extends RecyclerView.Adapter<MyImmediateHolderView> {

    private List<RewardInfoBean> list;
    private LayoutInflater mInflater;
    private Context mContext = null;

    public MyImmediateRecordAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<RewardInfoBean> list) {
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
    public MyImmediateHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.immediate_record_en_item, parent, false);
        MyImmediateHolderView holder = new MyImmediateHolderView(view);
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
    public void onBindViewHolder(final MyImmediateHolderView holder, final int position) {
        String bean = list.get(position).getGameName();
        if (bean != null) {
            holder.itemNum.setText(list.get(position).getIndex() + ".");
            holder.itemName.setText(list.get(position).getOrderCode());
            holder.itemTime.setText(list.get(position).getCashTime());
            holder.itemBook.setText(mContext.getString(R.string.book_numberss) + " " + list.get(position).getBookNum());
            holder.itemTicket.setText(mContext.getString(R.string.ticket_nos) + " " + list.get(position).getTicketNum());
            holder.itemMoney.setText(MoneyUtil.getIns().GetMoney(list.get(position).getCashMoney()) + mContext.getString(R.string.price_unit));
            holder.itemLly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("data", list.get(position));
                    intent.setClass(mContext, RewardRecordDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class MyImmediateHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.lly_record_item)
    LinearLayout itemLly;
    @BindView(R.id.tv_betting_item_num)
    TextView itemNum;
    @BindView(R.id.tv_betting_item_name)
    TextView itemName;
    @BindView(R.id.tv_betting_item_time)
    TextView itemTime;
    @BindView(R.id.tv_betting_item_money)
    TextView itemMoney;
    @BindView(R.id.tv_betting_item_book)
    TextView itemBook;
    @BindView(R.id.tv_betting_item_ticket)
    TextView itemTicket;

    public MyImmediateHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }

}
