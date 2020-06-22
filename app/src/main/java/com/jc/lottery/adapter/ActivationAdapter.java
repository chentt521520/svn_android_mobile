package com.jc.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.ActivationDetailsActivity;
import com.jc.lottery.activity.immediate.ImmediatelActivationActivity;
import com.jc.lottery.bean.WalletListBean;
import com.jc.lottery.bean.resp.ActivationQueryBean;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.SmoothCheckBox;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class ActivationAdapter extends RecyclerView.Adapter<ActivationHolderView> {

    private List<ActivationQueryBean> list;
    private LayoutInflater mInflater;
    private ImmediatelActivationActivity mContext = null;
    private ActivationTwoAdapter activationTwoAdapter;

    public ActivationAdapter(ImmediatelActivationActivity context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<ActivationQueryBean> list) {
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
    public ActivationHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.activation_one_items, parent, false);
        ActivationHolderView holder = new ActivationHolderView(view);
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
    public void onBindViewHolder(final ActivationHolderView holder, final int position) {
//        holder.setIsRecyclable(false);
        String bean = list.get(position).getOrderCode();
        if (bean != null) {
            holder.tvActivationOneItemOrder.setText(bean);
            holder.tvActivationOneItemBook.setText(list.get(position).getTicketNum() + mContext.getString(R.string.book));
            holder.tvActivationOneItemTime.setText(list.get(position).getCreateTime());
//            holder.relActivationOneItem.setLayoutManager(new GridLayoutManager(mContext,3));
//            activationTwoAdapter = new ActivationTwoAdapter(mContext);
//            activationTwoAdapter.setList(list.get(position).getBookInfo());
//            holder.relActivationOneItem.setAdapter(activationTwoAdapter);
            if (list.get(position).isType()){
                holder.imgActivationOneItemSelect.setImageResource(R.drawable.activation_yes);
            }else {
                holder.imgActivationOneItemSelect.setImageResource(R.drawable.activation_no);
            }
//            if (list.get(position).isOpenType()){
//                holder.llyActivationOneItemBottom.setVisibility(View.VISIBLE);
//            }else {
//                holder.llyActivationOneItemBottom.setVisibility(View.GONE);
//            }
            holder.btnActivationOneItemBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("deliveryId",list.get(position).getDeliveryId());
                    intent.setClass(mContext, ActivationDetailsActivity.class);
                    mContext.startActivity(intent);
                }
            });
            holder.llyActivationItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean selectType = !list.get(position).isType();
                    list.get(position).setType(selectType);
                    notifyItemChanged(position);
                    mContext.showAllBtn();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class ActivationHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.img_activation_one_item_select)
    ImageView imgActivationOneItemSelect;
    @BindView(R.id.tv_activation_one_item_order)
    TextView tvActivationOneItemOrder;
    @BindView(R.id.tv_activation_one_item_book)
    TextView tvActivationOneItemBook;
    @BindView(R.id.tv_activation_time)
    TextView tvActivationOneItemTime;
    @BindView(R.id.lly_activation_one_item_top)
    PercentLinearLayout llyActivationOneItemTop;
    @BindView(R.id.lly_activation_one_item_smooth)
    PercentLinearLayout llyActivationOneItemSmooth;
    @BindView(R.id.lly_activation_item)
    LinearLayout llyActivationItem;
//    @BindView(R.id.rel_activation_one_item)
//    RecyclerView relActivationOneItem;
    @BindView(R.id.lly_activation_one_item_bottom)
    PercentLinearLayout llyActivationOneItemBottom;
    @BindView(R.id.btn_activation_one_item_book)
    Button btnActivationOneItemBook;

    public ActivationHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}