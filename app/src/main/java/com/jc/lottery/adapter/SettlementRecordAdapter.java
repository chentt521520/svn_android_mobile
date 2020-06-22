package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jc.lottery.R;
import com.jc.lottery.bean.SettlementRecordBean;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;


//结算记录
public class SettlementRecordAdapter extends BaseAdapter {

    private Context context;
    private List<SettlementRecordBean> allGroup;
    private LayoutInflater inflater;
    private String settleStatus;

    public SettlementRecordAdapter(Context context, List<SettlementRecordBean> allGroup) {
        this.context = context;
        this.allGroup = allGroup;
        inflater = LayoutInflater.from(context);
    }

    public SettlementRecordAdapter(Context context,String settleStatus) {
        this.context = context;
        this.settleStatus = settleStatus;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    public List<SettlementRecordBean> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(List<SettlementRecordBean> allGroup) {
        this.allGroup = allGroup;
    }

    @Override
    public int getCount() {
        if (allGroup == null) {
            return 0;
        }
        return allGroup.size();
    }

    @Override
    public String getItem(int position) {
        if (allGroup == null) {
            return null;
        }
        return allGroup.get(position).getCreateName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.settle_record_en_item, null);
            holder.tvSettleRecordItemNum = (TextView) convertView.findViewById(R.id.tv_settle_record_num);
            holder.tvSettleRecordItemChannels = (TextView) convertView.findViewById(R.id.tv_settle_record_channels);
            holder.tvSettleRecordItemStatus = (TextView) convertView.findViewById(R.id.tv_settle_record_status);
            holder.tvSettleRecordItemOrder = (TextView) convertView.findViewById(R.id.tv_settle_record_order);
            holder.tvSettleRecordItemBook = (TextView) convertView.findViewById(R.id.tv_settle_record_book);
            holder.tvSettleRecordItemTime = (TextView) convertView.findViewById(R.id.tv_settle_record_time);
            holder.tvSettleRecordItemMoney = (TextView) convertView.findViewById(R.id.tv_settle_record_money);
            holder.tvSettleRecordItemGame = (TextView) convertView.findViewById(R.id.tv_settle_record_game);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvSettleRecordItemNum.setText((position + 1) + ". ");
        holder.tvSettleRecordItemChannels.setText(context.getString(R.string.settlement_channel) + allGroup.get(position).getChannels());
        holder.tvSettleRecordItemOrder.setText(allGroup.get(position).getOrderCode());
        holder.tvSettleRecordItemBook.setText(allGroup.get(position).getCreateName());
        holder.tvSettleRecordItemTime.setText(allGroup.get(position).getCreateTime());
        holder.tvSettleRecordItemGame.setText(context.getString(R.string.game_types) + allGroup.get(position).getGameName());
        if (allGroup.get(position).getTotalMoney().equals("0")){
            holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(255, 122, 0));
            holder.tvSettleRecordItemMoney.setText(MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotalMoney())  + " " + context.getString(R.string.price_unit));
        }else {
            if (settleStatus.equals("00")) {
                if (allGroup.get(position).getMoneyStatus().equals("00")) {
//                    holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(0,75,255));
                    holder.tvSettleRecordItemMoney.setText(Html.fromHtml(context.getString(R.string.total_settlement_amounts) + "<font color='#004BFF'>" + context.getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotalMoney())  + " " + context.getString(R.string.price_unit) + "</font>"));
//                    SpannableStringBuilder builder = new SpannableStringBuilder(holder.tvSettleRecordItemMoney.getText().toString());
//                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(0,75,255));
//                    builder.setSpan(redSpan, 5, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
//                    holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(253, 8, 8));
                    holder.tvSettleRecordItemMoney.setText(Html.fromHtml(context.getString(R.string.total_settlement_amounts) + "<font color='#FD0808'>" + context.getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotalMoney())  + " " + context.getString(R.string.price_unit) + "</font>"));
//                    SpannableStringBuilder builder = new SpannableStringBuilder(holder.tvSettleRecordItemMoney.getText().toString());
//                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(253, 8, 8));
//                    builder.setSpan(redSpan, 5, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                if (allGroup.get(position).getMoneyStatus().equals("00")) {
//                    holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(253, 8, 8));
                    holder.tvSettleRecordItemMoney.setText(Html.fromHtml(context.getString(R.string.total_settlement_amounts) + "<font color='#FD0808'>" + context.getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotalMoney())  + " " + context.getString(R.string.price_unit) + "</font>"));
//                    SpannableStringBuilder builder = new SpannableStringBuilder(holder.tvSettleRecordItemMoney.getText().toString());
//                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(253, 8, 8));
//                    builder.setSpan(redSpan, 5, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
//                    holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(0,75,255));
                    holder.tvSettleRecordItemMoney.setText(Html.fromHtml(context.getString(R.string.total_settlement_amounts) + "<font color='#004BFF'>" + context.getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotalMoney())  + " " + context.getString(R.string.price_unit) + "</font>"));
//                    SpannableStringBuilder builder = new SpannableStringBuilder(holder.tvSettleRecordItemMoney.getText().toString());
//                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(0,75,255));
//                    builder.setSpan(redSpan, 5, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
//        holder.tvSettleRecordItemMoney.setText(MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotalMoney()) + " ");
        if (allGroup.get(position).getSettleStatus().equals("00")){
            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.waiting_for_settlement));
            holder.tvSettleRecordItemStatus.setTextColor(Color.rgb(255, 171, 0));
//            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.to_be_audited));
        }else if (allGroup.get(position).getSettleStatus().equals("03")){
            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.rejected));
            holder.tvSettleRecordItemStatus.setTextColor(Color.rgb(255, 93, 67));
        }else {
            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.settled));
            holder.tvSettleRecordItemStatus.setTextColor(Color.rgb(22, 119, 255));
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvSettleRecordItemNum;
        TextView tvSettleRecordItemChannels;
        TextView tvSettleRecordItemStatus;
        TextView tvSettleRecordItemOrder;
        TextView tvSettleRecordItemBook;
        TextView tvSettleRecordItemTime;
        TextView tvSettleRecordItemMoney;
        TextView tvSettleRecordItemGame;
    }

}
