package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.SettlementRecordBean;
import com.jc.lottery.bean.VictorySettlementRecordBean;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;


//结算记录
public class SettleRecordAdapter extends BaseAdapter {

    private Context context;
    private List<VictorySettlementRecordBean> allGroup;
    private LayoutInflater inflater;
    private String settleStatus;

    public SettleRecordAdapter(Context context, List<VictorySettlementRecordBean> allGroup) {
        this.context = context;
        this.allGroup = allGroup;
        inflater = LayoutInflater.from(context);
    }

    public SettleRecordAdapter(Context context, String settleStatus) {
        this.context = context;
        this.settleStatus = settleStatus;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    public List<VictorySettlementRecordBean> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(List<VictorySettlementRecordBean> allGroup) {
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
        return allGroup.get(position).getUser_name();
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
            convertView = inflater.inflate(R.layout.settlement_record_item, null);
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
        holder.tvSettleRecordItemChannels.setText(allGroup.get(position).getChannels());
        holder.tvSettleRecordItemOrder.setText(allGroup.get(position).getOrder_code());
        holder.tvSettleRecordItemBook.setText(allGroup.get(position).getUser_name());
        holder.tvSettleRecordItemTime.setText(allGroup.get(position).getCreate_time());
//        holder.tvSettleRecordItemGame.setText(allGroup.get(position).getGameName());
        if (allGroup.get(position).getTotal_money().equals("0")){
            holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(255, 122, 0));
            holder.tvSettleRecordItemMoney.setText(MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotal_money())  + " " + context.getString(R.string.price_unit));
        }else {
            if (settleStatus.equals("00")) {
                if (allGroup.get(position).getMoney_status().equals("00")) {
                    holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(0,75,255));
                    holder.tvSettleRecordItemMoney.setText(context.getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotal_money())  + " " + context.getString(R.string.price_unit));
                } else {
                    holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(253, 8, 8));
                    holder.tvSettleRecordItemMoney.setText(context.getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotal_money())  + " " + context.getString(R.string.price_unit));
                }
            } else {
                if (allGroup.get(position).getMoney_status().equals("00")) {
                    holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(253, 8, 8));
                    holder.tvSettleRecordItemMoney.setText(context.getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotal_money())  + " " + context.getString(R.string.price_unit));
                } else {
                    holder.tvSettleRecordItemMoney.setTextColor(Color.rgb(0,75,255));
                    holder.tvSettleRecordItemMoney.setText(context.getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotal_money())  + " " + context.getString(R.string.price_unit));
                }
            }
        }
//        holder.tvSettleRecordItemMoney.setText(MoneyUtil.getIns().GetMoney(allGroup.get(position).getTotalMoney()) + " ");
        if (allGroup.get(position).getSettle_status().equals("00")){
            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.waiting_for_settlement));
//            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.to_be_audited));
        }else if (allGroup.get(position).getSettle_status().equals("03")){
            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.rejected));
        }else {
            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.settled));
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
