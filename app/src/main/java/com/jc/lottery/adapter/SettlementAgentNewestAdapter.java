package com.jc.lottery.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryAgentDetailsActivity;
import com.jc.lottery.activity.immediate.DeliveryDetailsActivity;
import com.jc.lottery.bean.resp.DeliveryBookBean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/23.
 */

public class SettlementAgentNewestAdapter extends RecyclerView.Adapter<SettlementHolderView> {

    private List<DeliveryBookBean> list;
    private LayoutInflater mInflater;
    private DeliveryAgentDetailsActivity mContext = null;

    public SettlementAgentNewestAdapter(DeliveryAgentDetailsActivity context) {
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
