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
import com.jc.lottery.activity.immediate.DeliveryInputActivity;
import com.jc.lottery.activity.immediate.ImmediatelSettlementActivity;
import com.jc.lottery.bean.SettleBookBean;
import com.jc.lottery.bean.req.pos_GetLogisticsDelivery;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.SmoothCheckBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class SettlementNewsAdapter extends RecyclerView.Adapter<SettlementNewsHolderView> {

    private List<SettleBookBean> list;
    private LayoutInflater mInflater;
    private ImmediatelSettlementActivity mContext = null;

    public SettlementNewsAdapter(ImmediatelSettlementActivity context) {
        this.mContext = context;
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
    public SettlementNewsHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.settlement_item, parent, false);
        SettlementNewsHolderView holder = new SettlementNewsHolderView(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mOnItemClickListener != null){
//                    mOnItemClickListener.onItemClick(v, viewType);
//                }
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final SettlementNewsHolderView holder, final int position) {
        String bean = list.get(position).getBookNum();
        if (bean != null) {
            holder.tvSettlementItemNo.setText((position + 1) + "");
            holder.tvSettlementItemBatch.setText(list.get(position).getSchemeNum());
            holder.tvSettlementItemBook.setText(list.get(position).getBookNum());
            holder.tvSettlementItemScheme.setTextIsSelectable(true);
            if (list.get(position).getSettleStatus().equals("00")){
                holder.tvSettlementItemStatus.setText(mContext.getString(R.string.waiting_for_settlement));
                holder.tvSettlementItemStatus.setTextColor(Color.rgb(255, 102, 0));
            }else if (list.get(position).getSettleStatus().equals("01")){
                holder.tvSettlementItemStatus.setText(mContext.getString(R.string.settled));
                holder.tvSettlementItemStatus.setTextColor(Color.rgb(0,146,61));
            }else {
                holder.tvSettlementItemStatus.setText(mContext.getString(R.string.unsettled));
                holder.tvSettlementItemStatus.setTextColor(Color.rgb(255, 102, 0));
            }
            if (list.get(position).isType()){
                holder.imgSettlementItemSelect.setImageResource(R.drawable.settle_yes);
            }else {
                holder.imgSettlementItemSelect.setImageResource(R.drawable.settle_no);
            }
            holder.llySettlementItemContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean selectType = !list.get(position).isType();
                    if (selectType){
                        if (mContext.getNum() == 100){
                            ToastUtils.showShort(mContext.getString(R.string.you_please_re_select));
                        }else {
                            list.get(position).setType(selectType);
                            notifyItemChanged(position);
                            mContext.showNum("1",list.get(position));
                        }
                    }else {
                        list.get(position).setType(selectType);
                        notifyItemChanged(position);
                        mContext.showNum("2",list.get(position));
                    }
//                    if (selectType){
//                        mContext.showNum("1",list.get(position));
//                    }else {
//                        mContext.showNum("2",list.get(position));
//                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
class SettlementNewsHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.lly_settlement_content)
    LinearLayout llySettlementItemContent;
    @BindView(R.id.tv_receiving_item_no)
    TextView tvSettlementItemNo;
    @BindView(R.id.tv_settlement_batch)
    TextView tvSettlementItemBatch;
    @BindView(R.id.tv_settlement_money)
    TextView tvSettlementItemMoney;
    @BindView(R.id.tv_settlement_status)
    TextView tvSettlementItemStatus;
    @BindView(R.id.tv_settlement_book)
    TextView tvSettlementItemBook;
    @BindView(R.id.tv_settlement_scheme)
    TextView tvSettlementItemScheme;
    @BindView(R.id.img_settlement_select)
    ImageView imgSettlementItemSelect;

    public SettlementNewsHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }

}