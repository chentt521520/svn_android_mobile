package com.jc.lottery.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import com.jc.lottery.bean.resp.ActivationQueryBean;
import com.jc.lottery.bean.resp.BettingDetailInfo;
import com.jc.lottery.content.Constant;
import com.jc.lottery.util.MoneyUtil;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class BettingDetailAdapter extends RecyclerView.Adapter<BettingDetailHolderView> {

    private List<BettingDetailInfo.BetsListInfo> list;
    private LayoutInflater mInflater;
    private Activity mContext = null;
    private String type = "";
    private ActivationTwoAdapter activationTwoAdapter;

    public BettingDetailAdapter(Activity context,String type) {
        this.mContext = context;
        this.type = type;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<BettingDetailInfo.BetsListInfo> list) {
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
    public BettingDetailHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.betting_detail_item, parent, false);
        BettingDetailHolderView holder = new BettingDetailHolderView(view);
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
    public void onBindViewHolder(final BettingDetailHolderView holder, final int position) {
//        holder.setIsRecyclable(false);
        String bean = list.get(position).getBetMode();
        if (bean != null) {
            holder.tvItemNo.setText((position + 1) + "");
            if (list.get(position).getBetMode().equals("01")) {
                holder.tvItemOne.setText(mContext.getString(R.string.danshi));
            }else if (list.get(position).getBetMode().equals("02")){
                holder.tvItemOne.setText(mContext.getString(R.string.fushi));
            }else {
                holder.tvItemOne.setText(mContext.getString(R.string.dantuofushi));
            }
            holder.tvItemTwo.setText(MoneyUtil.getIns().GetMoney(list.get(position).getBetMoney()));
            holder.tvItemThree.setText(list.get(position).getBetNum());
            String money = list.get(position).getWinMoney();
            if (null != money && !money.equals("")){
                if (money.equals("0")){
                    if (type.equals("1")) {
                        holder.tvItemFour.setText("--");
                    }else {
                        holder.tvItemFour.setText("0");
                    }
                }else {
                    holder.tvItemFour.setText(MoneyUtil.getIns().GetMoney(money));
                }
            }else {
                if (type.equals("1")) {
                    holder.tvItemFour.setText("--");
                }else {
                    holder.tvItemFour.setText("0");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class BettingDetailHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_betting_item_no)
    TextView tvItemNo;
    @BindView(R.id.tv_betting_item_one)
    TextView tvItemOne;
    @BindView(R.id.tv_betting_item_two)
    TextView tvItemTwo;
    @BindView(R.id.tv_betting_item_three)
    TextView tvItemThree;
    @BindView(R.id.tv_betting_item_four)
    TextView tvItemFour;

    public BettingDetailHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}