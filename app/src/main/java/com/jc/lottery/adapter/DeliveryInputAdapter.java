package com.jc.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryInputActivity;
import com.jc.lottery.bean.resp.DeliveryBookBean;
import com.jc.lottery.bean.resp.SettlementQueryBean;
import com.jc.lottery.view.SmoothCheckBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class DeliveryInputAdapter extends RecyclerView.Adapter<DeliveryInputHolderView> {

    private List<DeliveryBookBean> list;
    private LayoutInflater mInflater;
    private DeliveryInputActivity mContext = null;

    public DeliveryInputAdapter(DeliveryInputActivity context) {
        this.mContext = context;
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
    public DeliveryInputHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.delivery_input_item, parent, false);
        DeliveryInputHolderView holder = new DeliveryInputHolderView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DeliveryInputHolderView holder, final int position) {
        String bean = list.get(position).getBookNum();
        holder.tvDeliveryInputItemName.setText(mContext.getString(R.string.case_number) + " " + list.get(position).getCartonNo());
        holder.tvDeliveryInputItemTwo.setText(mContext.getString(R.string.book_numbers) + " " + bean);
        holder.tvDeliveryInputItemThree.setText(mContext.getString(R.string.batch_number) + " " + list.get(position).getSchemeNum());
        holder.imgDeliveryInputItemDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        holder.imgRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!list.get(position).getCartonType().equals("")){
                    mContext.showPopBook(list.get(position).getCartonNo());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class DeliveryInputHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_delivery_input_item_name)
    TextView tvDeliveryInputItemName;
    @BindView(R.id.tv_delivery_input_item_two)
    TextView tvDeliveryInputItemTwo;
    @BindView(R.id.tv_delivery_input_item_three)
    TextView tvDeliveryInputItemThree;
    @BindView(R.id.img_delivery_input_item_del)
    ImageView imgDeliveryInputItemDel;
    @BindView(R.id.lly_delivery_item)
    RelativeLayout imgRly;

    public DeliveryInputHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}