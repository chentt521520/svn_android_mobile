package com.jc.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.victory.VictoryBettingDetailActivity;
import com.jc.lottery.bean.VictoryBettingBean;
import com.jc.lottery.bean.resp.PaymentBean;
import com.jc.lottery.content.Constant;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class VictoryBettingRecordAdapter extends RecyclerView.Adapter<VictoryBettingRecordHolderView> {

    private List<VictoryBettingBean.BettingList> list;
    private LayoutInflater mInflater;
    private Context mContext = null;
    private String type = "";

    public VictoryBettingRecordAdapter(Context context,String type) {
        this.mContext = context;
        this.type = type;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<VictoryBettingBean.BettingList> list) {
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
    public VictoryBettingRecordHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.victory_betting_item, parent, false);
        VictoryBettingRecordHolderView holder = new VictoryBettingRecordHolderView(view);
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
    public void onBindViewHolder(final VictoryBettingRecordHolderView holder, final int position) {
        String bean = list.get(position).getOrder_code();
        if (bean != null) {
            holder.itemOrder.setText(mContext.getString(R.string.victory_ticket_number) + ": " + list.get(position).getOrder_code());
            if (list.get(position).getOrder_time() != null && !list.get(position).getOrder_time().equals("")) {
                holder.itemTime.setText(mContext.getString(R.string.order_time) + ": " +timeStamp2Date(list.get(position).getOrder_time()));
            }else {
                holder.itemTime.setText("--");
            }
            if (type.equals("1")){
//                if (list.get(position).getBet_status().equals("03")){
//                    holder.itemType.setText(mContext.getString(R.string.refunded));
//                    holder.itemType.setTextColor(Color.rgb(155, 155, 155));
//                    holder.itemMoney.setTextColor(Color.rgb(155, 155, 155));
//                    holder.itemMoney.setText("--");
//                }else {
//                    if (list.get(position).getWin_status().equals("00")) {
//                        holder.itemType.setText(mContext.getString(R.string.weizhongjiang));
//                        holder.itemType.setTextColor(Color.rgb(255, 146, 0));
//                        holder.itemMoney.setTextColor(Color.rgb(255, 146, 0));
//                        holder.itemMoney.setText("--");
//                    }else if (list.get(position).getWin_status().equals("01")) {
//                        holder.itemType.setText(mContext.getString(R.string.zhongjiang));
//                        holder.itemType.setTextColor(Color.rgb(0, 217, 9));
//                        holder.itemMoney.setTextColor(Color.rgb(0, 217, 9));
//                        holder.itemMoney.setText("" + MoneyUtil.getIns().GetMoney(list.get(position).getWin_money()) + mContext.getString(R.string.price_unit));
//                    }else {
//                        holder.itemType.setText(mContext.getString(R.string.daikaijiang));
//                        holder.itemType.setTextColor(Color.rgb(155, 155, 155));
//                        holder.itemMoney.setTextColor(Color.rgb(155, 155, 155));
//                        holder.itemMoney.setText("--");
//                    }
//                }
                if (list.get(position).getBet_status().equals("00")){
                    holder.itemType.setText(mContext.getString(R.string.chupiao_scuess));
                    holder.itemType.setTextColor(Color.rgb(0, 217, 9));
                    holder.itemMoney.setTextColor(Color.rgb(0, 217, 9));
                    if (list.get(position).getWin_status().equals("01")) {
                        holder.itemMoney.setText("" + MoneyUtil.getIns().GetMoney(list.get(position).getWin_money()) + mContext.getString(R.string.price_unit));
                    }else {
                        holder.itemMoney.setText("--");
                    }
                }else if (list.get(position).getCash_status().equals("01")){
                    holder.itemType.setText(mContext.getString(R.string.chupiao_fail));
                    holder.itemType.setTextColor(Color.rgb(255, 146, 0));
                    holder.itemMoney.setTextColor(Color.rgb(255, 146, 0));
                    holder.itemMoney.setText("--");
                }else if (list.get(position).getCash_status().equals("02")){
                    holder.itemType.setText(mContext.getString(R.string.chupiao_daichupiao));
                    holder.itemType.setTextColor(Color.rgb(155, 155, 155));
                    holder.itemMoney.setTextColor(Color.rgb(155, 155, 155));
                    holder.itemMoney.setText("--");
                }else {
                    holder.itemType.setText(mContext.getString(R.string.refund));
                    holder.itemType.setTextColor(Color.rgb(155, 155, 155));
                    holder.itemMoney.setTextColor(Color.rgb(155, 155, 155));
                    holder.itemMoney.setText("--");
                }
            }else {
                if (list.get(position).getCash_status().equals("00")){
                    holder.itemType.setText(mContext.getString(R.string.no_convertibility));
                    holder.itemType.setTextColor(Color.rgb(155, 155, 155));
                    holder.itemMoney.setTextColor(Color.rgb(155, 155, 155));
                    if (list.get(position).getWin_status().equals("01")) {
                        holder.itemMoney.setText("" + MoneyUtil.getIns().GetMoney(list.get(position).getWin_money()) + mContext.getString(R.string.price_unit));
                    }else {
                        holder.itemMoney.setText("--");
                    }
                }else if (list.get(position).getCash_status().equals("01")){
                    holder.itemType.setText(mContext.getString(R.string.yiduijiang));
                    holder.itemType.setTextColor(Color.rgb(0, 217, 9));
                    holder.itemMoney.setTextColor(Color.rgb(0, 217, 9));
                    holder.itemMoney.setText("" + MoneyUtil.getIns().GetMoney(list.get(position).getWin_money()) + mContext.getString(R.string.price_unit));
                }else {
                    holder.itemType.setText(mContext.getString(R.string.qijiang));
                    holder.itemType.setTextColor(Color.rgb(155, 155, 155));
                    holder.itemMoney.setTextColor(Color.rgb(155, 155, 155));
                    holder.itemMoney.setText("--");
                }
            }

            holder.relContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("type",type);
                    intent.putExtra("orderCode",list.get(position).getOrder_code());
                    intent.setClass(mContext, VictoryBettingDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(mContext, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
            format = "dd-MM-yyyy HH:mm:ss";
        } else {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

}

class VictoryBettingRecordHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.rel_content)
    RelativeLayout relContent;
    @BindView(R.id.tv_item_order)
    TextView itemOrder;
    @BindView(R.id.tv_item_time)
    TextView itemTime;
    @BindView(R.id.tv_item_type)
    TextView itemType;
    @BindView(R.id.tv_item_money)
    TextView itemMoney;

    public VictoryBettingRecordHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}