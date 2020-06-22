package com.jc.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryDetailsActivity;
import com.jc.lottery.bean.resp.DeliveryBookBean;
import com.jc.lottery.bean.resp.SettlementQueryBean;
import com.jc.lottery.view.SmoothCheckBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class SettlementNewestAdapter extends RecyclerView.Adapter<SettlementHolderView> {

    private List<DeliveryBookBean> list;
    private LayoutInflater mInflater;
    private DeliveryDetailsActivity mContext = null;

    public SettlementNewestAdapter(DeliveryDetailsActivity context) {
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
    public SettlementHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.settlement_newest_item, parent, false);
        SettlementHolderView holder = new SettlementHolderView(view);
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
    public void onBindViewHolder(final SettlementHolderView holder, final int position) {
        String bean = list.get(position).getBookNum();
        if (bean != null) {
            holder.tvSettlementItemBook.setText(list.get(position).getBookNum());
            holder.scbSettlementItemSelect.setClickable(false);
            Log.d("pos",position + ",pos," + list.get(position).isType() + ",type:" + holder.scbSettlementItemSelect.isChecked());
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
class SettlementHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_settlement_book)
    TextView tvSettlementItemBook;
    @BindView(R.id.scb_settlement_select)
    SmoothCheckBox scbSettlementItemSelect;
    @BindView(R.id.lly_settlement_content)
    LinearLayout llySettlementContent;

    public SettlementHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }

}