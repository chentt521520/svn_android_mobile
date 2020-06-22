package com.jc.lottery.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryAgentInputActivity;
import com.jc.lottery.activity.immediate.ImmediatelSettlementActivity;
import com.jc.lottery.bean.SettleBookBean;
import com.jc.lottery.bean.resp.DeliveryBookBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class SettlementSelectAdapter extends RecyclerView.Adapter<SettlementSelectHolderView> {

    private List<SettleBookBean> list;
    private LayoutInflater mInflater;
    private ImmediatelSettlementActivity mContext = null;
    private String type = "1"; //1 全部 2 已选择

    public SettlementSelectAdapter(ImmediatelSettlementActivity context) {
        this.mContext = context;
        this.type = type;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<SettleBookBean> list) {
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
    public SettlementSelectHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.settlement_select_items, parent, false);
        SettlementSelectHolderView holder = new SettlementSelectHolderView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SettlementSelectHolderView holder, final int position) {
        String bean = list.get(position).getBookNum();
        holder.tvDeliveryInputItemNo.setText((position + 1) + "");
        holder.tvDeliveryInputItemBook.setText(bean);
        holder.tvDeliveryInputItemBatch.setText(list.get(position).getSchemeNum());
        if (list.get(position).getSettleStatus().equals("00")){
            holder.tvDeliveryInputItemStatus.setText(mContext.getString(R.string.waiting_for_settlement));
            holder.tvDeliveryInputItemStatus.setTextColor(Color.rgb(255, 102, 0));
        }else if (list.get(position).getSettleStatus().equals("01")){
            holder.tvDeliveryInputItemStatus.setText(mContext.getString(R.string.settled));
            holder.tvDeliveryInputItemStatus.setTextColor(Color.rgb(0,146,61));
        }else {
            holder.tvDeliveryInputItemStatus.setText(mContext.getString(R.string.unsettled));
            holder.tvDeliveryInputItemStatus.setTextColor(Color.rgb(255, 102, 0));
        }
        holder.itemRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.showDelList(list.get(position));
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.imgDeliveryInputItemSelect.setImageResource(R.drawable.settle_yes);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class SettlementSelectHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.lly_settlement_item)
    LinearLayout itemRel;
    @BindView(R.id.tv_receiving_item_no)
    TextView tvDeliveryInputItemNo;
    @BindView(R.id.tv_settlement_batch)
    TextView tvDeliveryInputItemBatch;
    @BindView(R.id.tv_settlement_book)
    TextView tvDeliveryInputItemBook;
    @BindView(R.id.tv_settlement_status)
    TextView tvDeliveryInputItemStatus;
    @BindView(R.id.img_settlement_select)
    ImageView imgDeliveryInputItemSelect;

    public SettlementSelectHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}