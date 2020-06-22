package com.jc.lottery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryAgentDetailsActivity;
import com.jc.lottery.activity.immediate.DeliveryAgentInputActivity;
import com.jc.lottery.bean.resp.DeliveryBookBean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/23.
 */

public class DeliveryAgentInputsAdapter extends RecyclerView.Adapter<DeliveryAgentInputHolderView> {

    private List<DeliveryBookBean> list;
    private LayoutInflater mInflater;
    private DeliveryAgentDetailsActivity mContext = null;
    private String type = "1"; //1 全部 2 已选择

    public DeliveryAgentInputsAdapter(DeliveryAgentDetailsActivity context, String type) {
        this.mContext = context;
        this.type = type;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<DeliveryBookBean> list) {
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
    public DeliveryAgentInputHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.delivery_agent_input_item, parent, false);
        DeliveryAgentInputHolderView holder = new DeliveryAgentInputHolderView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DeliveryAgentInputHolderView holder, final int position) {
        String bean = list.get(position).getBookNum();
        holder.tvDeliveryInputItemTwo.setText(bean);
        holder.tvDeliveryInputItemBatch.setText(list.get(position).getSchemeNum());
        holder.tvDeliveryInputItemNo.setText((position + 1) + "");
        holder.imgDeliveryInputItemDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.addSelectList(type,position);
//                list.remove(position);
//                notifyItemRemoved(position);
//                notifyDataSetChanged();
            }
        });
        holder.itemRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if (type.equals("1")) {
                mContext.addSelectList(type, position);
//                    }else {
//                        mContext.addSelectList(type,position);
//                    }
            }
        });
        if (list.get(position).isBookType()) {
            holder.imgDeliveryInputItemSelect.setImageResource(R.drawable.settle_yes);
        }else {
            holder.imgDeliveryInputItemSelect.setImageResource(R.drawable.settle_no);
        }
//        if (type.equals("1")){
////            holder.imgDeliveryInputItemDel.setVisibility(View.GONE);
//            if (list.get(position).isBookType()) {
//                holder.imgDeliveryInputItemSelect.setImageResource(R.drawable.settle_yes);
//            }else {
//                holder.imgDeliveryInputItemSelect.setImageResource(R.drawable.settle_no);
//            }
//        }else {
////            holder.imgDeliveryInputItemDel.setVisibility(View.VISIBLE);
//            holder.imgDeliveryInputItemSelect.setImageResource(R.drawable.settle_yes);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

