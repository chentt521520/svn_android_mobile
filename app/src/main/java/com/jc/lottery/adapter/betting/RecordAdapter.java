package com.jc.lottery.adapter.betting;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.RecordInfoBean;
import com.jc.lottery.content.Constant;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;


public class RecordAdapter extends BaseAdapter {

    private Context context;
    private List<RecordInfoBean> allGroup;
    private LayoutInflater inflater;

    public RecordAdapter(Context context, List<RecordInfoBean> allGroup) {
        this.context = context;
        this.allGroup = allGroup;
        inflater = LayoutInflater.from(context);
    }

    public RecordAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public List<RecordInfoBean> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(List<RecordInfoBean> allGroup) {
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
        return allGroup.get(position).getGameName();
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
            convertView = inflater.inflate(R.layout.reward_record_item, null);
            holder.tvRewardRecordItemName = (TextView) convertView.findViewById(R.id.tv_reward_record_item_name);
            holder.tvRewardRecordItemTime = (TextView) convertView.findViewById(R.id.tv_reward_record_item_time);
            holder.tvRewardRecordItemStatus = (TextView) convertView.findViewById(R.id.tv_reward_record_item_status);
            holder.tvRewardRecordItemMoney = (TextView) convertView.findViewById(R.id.tv_reward_record_item_money);
            holder.tvRewardRecordItemNumber = (TextView) convertView.findViewById(R.id.tv_reward_record_item_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String bean = getItem(position);
        holder.tvRewardRecordItemName.setText(allGroup.get(position).getIndex() + ". " + bean.toString().trim());
        holder.tvRewardRecordItemTime.setText(allGroup.get(position).getCreateTime());
        holder.tvRewardRecordItemMoney.setText(MoneyUtil.getIns().GetMoney(allGroup.get(position).getPayMoney()) + context.getString(R.string.price_unit));
        holder.tvRewardRecordItemNumber.setText(allGroup.get(position).getTicketNum() + " " + context.getString(R.string.book));
        if (allGroup.get(position).getPayState().equals("02")) {
            if (allGroup.get(position).getStatus().equals("02")) {
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.to_be_paid));
                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
            }else if (allGroup.get(position).getStatus().equals("04")){
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.cancellation_of_order));
                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(153,153,153));
            }else {
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.order_invalidation));
                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(153,153,153));
            }
        }else if (allGroup.get(position).getPayState().equals("01")){
            holder.tvRewardRecordItemStatus.setText(context.getString(R.string.failure_to_pay));
            holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(253,8,8));
        }else if (allGroup.get(position).getPayState().equals("03")){
            holder.tvRewardRecordItemStatus.setText(context.getString(R.string.to_be_confirmed));
            holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
        } else {
            if (allGroup.get(position).getStatus().equals("00")) {
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.has_been_received));
                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(0,146,61));
            } else if (allGroup.get(position).getStatus().equals("01")) {
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.chupiao_fail));
                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(253,8,8));
            } else if (allGroup.get(position).getStatus().equals("02")) {
//                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.to_be_dispatched));
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.unclaimed));
                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
            } else if (allGroup.get(position).getStatus().equals("03")){
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.order_invalidation));
                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(153,153,153));
            }else if (allGroup.get(position).getStatus().equals("04")){
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.cancellation_of_order));
                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(153,153,153));
            }else {
                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.in_delivery));
                holder.tvRewardRecordItemStatus.setTextColor(Constant.in_delivery);
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvRewardRecordItemName;
        TextView tvRewardRecordItemTime;
        TextView tvRewardRecordItemMoney;
        TextView tvRewardRecordItemNumber;
        TextView tvRewardRecordItemStatus;
    }

}
