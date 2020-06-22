package com.jc.lottery.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryDetailsActivity;
import com.jc.lottery.activity.immediate.DeliveryInputActivity;
import com.jc.lottery.bean.req.pos_GetLogisticsDelivery;
import com.jc.lottery.bean.resp.DeliveryBookBean;
import com.jc.lottery.view.SmoothCheckBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class DeliveryInputCartAdapter extends RecyclerView.Adapter<DeliveryInputCartHolderView> {

    private List<pos_GetLogisticsDelivery.BookNoListInfo> list;
    private LayoutInflater mInflater;
    private DeliveryInputActivity mContext = null;

    public DeliveryInputCartAdapter(DeliveryInputActivity context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<pos_GetLogisticsDelivery.BookNoListInfo> list) {
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
    public DeliveryInputCartHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.delivery_input_cart_item, parent, false);
        DeliveryInputCartHolderView holder = new DeliveryInputCartHolderView(view);
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
    public void onBindViewHolder(final DeliveryInputCartHolderView holder, final int position) {
        String bean = list.get(position).getBookNo();
        if (bean != null) {
            holder.tvSettlementItemBook.setText(list.get(position).getBookNo());
            holder.scbSettlementItemSelect.setClickable(false);
            holder.scbSettlementItemSelect.setChecked(true, false);
//            holder.llySettlementContent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean selectType = !list.get(position).isType();
//                    list.get(position).setType(selectType);
//                    notifyItemChanged(position);
//                    mContext.showNumber();
//                }
//            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
class DeliveryInputCartHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_settlement_book)
    TextView tvSettlementItemBook;
    @BindView(R.id.scb_settlement_select)
    SmoothCheckBox scbSettlementItemSelect;
    @BindView(R.id.lly_settlement_content)
    LinearLayout llySettlementContent;

    public DeliveryInputCartHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }

}