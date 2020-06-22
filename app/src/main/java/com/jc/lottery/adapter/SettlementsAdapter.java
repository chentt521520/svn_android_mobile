package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.ImmediatelSettlementActivity;
import com.jc.lottery.bean.SettleBookBean;
import com.jc.lottery.bean.resp.SettlementQueryBean;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.view.SmoothCheckBox;

import java.util.List;

//结算记录
public class SettlementsAdapter extends BaseAdapter {

    private ImmediatelSettlementActivity context;
    private List<SettleBookBean> allGroup;
    private LayoutInflater inflater;

    public SettlementsAdapter(ImmediatelSettlementActivity context, List<SettleBookBean> allGroup) {
        this.context = context;
        this.allGroup = allGroup;
        inflater = LayoutInflater.from(context);
    }

    public SettlementsAdapter(ImmediatelSettlementActivity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public List<SettleBookBean> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(List<SettleBookBean> allGroup) {
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
        return allGroup.get(position).getBookNum();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.settlement_item, null);
            holder.llySettlementItemContent = (LinearLayout) convertView.findViewById(R.id.lly_settlement_content);
            holder.tvSettlementItemBook = (TextView) convertView.findViewById(R.id.tv_settlement_book);
            holder.tvSettlementItemMoney = (TextView) convertView.findViewById(R.id.tv_settlement_money);
            holder.tvSettlementItemScheme = (TextView) convertView.findViewById(R.id.tv_settlement_scheme);
            holder.tvSettlementItemStatus = (TextView) convertView.findViewById(R.id.tv_settlement_status);
            holder.scbSettlementItemSelect = (SmoothCheckBox) convertView.findViewById(R.id.scb_settlement_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSettlementItemBook.setText(allGroup.get(position).getBookNum());
        holder.tvSettlementItemScheme.setTextIsSelectable(true);
        if (allGroup.get(position).getSettleStatus().equals("00")){
            holder.tvSettlementItemStatus.setText(context.getString(R.string.waiting_for_settlement));
            holder.tvSettlementItemStatus.setTextColor(Color.rgb(255, 102, 0));
        }else if (allGroup.get(position).getSettleStatus().equals("01")){
            holder.tvSettlementItemStatus.setText(context.getString(R.string.settled));
            holder.tvSettlementItemStatus.setTextColor(Color.rgb(0,146,61));
        }else {
            holder.tvSettlementItemStatus.setText(context.getString(R.string.unsettled));
            holder.tvSettlementItemStatus.setTextColor(Color.rgb(255, 102, 0));
        }
        holder.scbSettlementItemSelect.setClickable(false);
//        Log.d("pos",position + ",pos," + allGroup.get(position).isType() + ",type:" + holder.scbSettlementItemSelect.isChecked());
        if (allGroup.get(position).isType()){
            if (!holder.scbSettlementItemSelect.isChecked()) {
                holder.scbSettlementItemSelect.setChecked(true, true);
            }
        }else {
            if (holder.scbSettlementItemSelect.isChecked()) {
                holder.scbSettlementItemSelect.setChecked(false, true);
            }
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout llySettlementItemContent;
        TextView tvSettlementItemMoney;
        TextView tvSettlementItemStatus;
        TextView tvSettlementItemBook;
        TextView tvSettlementItemScheme;
        SmoothCheckBox scbSettlementItemSelect;
    }

}
