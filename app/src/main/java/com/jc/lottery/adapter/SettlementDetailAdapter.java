package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.SettlementGetDetailBean;
import com.jc.lottery.bean.resp.ReceivingActivationBean;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;


//结算详情本数信息
public class SettlementDetailAdapter extends BaseAdapter {

    private List<SettlementGetDetailBean.GetList> allGroup;
    private LayoutInflater inflater;
    private Context context;

    public SettlementDetailAdapter(Context context, List<SettlementGetDetailBean.GetList> allGroup) {
        this.allGroup = allGroup;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public SettlementDetailAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public List<SettlementGetDetailBean.GetList> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(List<SettlementGetDetailBean.GetList> allGroup) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.receiving_settlement_item, null);
            holder.tvReceivingItemNo = (TextView) convertView.findViewById(R.id.tv_receiving_item_no);
            holder.tvReceivingItemOne = (TextView) convertView.findViewById(R.id.tv_receiving_item_one);
            holder.tvReceivingItemTwo = (TextView) convertView.findViewById(R.id.tv_receiving_item_two);
            holder.tvReceivingItemThree = (TextView) convertView.findViewById(R.id.tv_receiving_item_three);
//            holder.tvReceivingItemType = (TextView) convertView.findViewById(R.id.tv_receiving_item_type);
//            holder.tvReceivingItemTime = (TextView) convertView.findViewById(R.id.tv_receiving_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String bean = getItem(position);
        holder.tvReceivingItemNo.setText((position + 1) + "");
        holder.tvReceivingItemOne.setText(allGroup.get(position).getSchemeNum());
        holder.tvReceivingItemTwo.setText(allGroup.get(position).getBookNum());
        holder.tvReceivingItemThree.setText(MoneyUtil.getIns().GetMoney(allGroup.get(position).getWinMoney()) + context.getString(R.string.price_unit));
//        if (allGroup.get(position).getSettleStatus().equals("00")) {
//            holder.tvReceivingItemType.setText(context.getString(R.string.wait_shenhe));
//            holder.tvReceivingItemType.setTextColor(Color.rgb(0,165,83));
//        }else {
//            holder.tvReceivingItemType.setText(context.getString(R.string.settled));
//            holder.tvReceivingItemType.setTextColor(Color.rgb(48,178,102));
//        }
//        if (!allGroup.get(position).getAuditTime().equals("")) {
//            holder.tvReceivingItemTime.setText(allGroup.get(position).getAuditTime());
//        }else {
//            holder.tvReceivingItemTime.setText("--");
//        }
        return convertView;
    }

    class ViewHolder {
        TextView tvReceivingItemNo;
        TextView tvReceivingItemOne;
        TextView tvReceivingItemTwo;
        TextView tvReceivingItemThree;
//        TextView tvReceivingItemType;
//        TextView tvReceivingItemTime;
    }

}
