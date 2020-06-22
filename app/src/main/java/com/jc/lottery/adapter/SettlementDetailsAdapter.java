package com.jc.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.SettlementGetDetailBean;

import java.util.List;

//结算详情
public class SettlementDetailsAdapter extends BaseAdapter {

    private Context context;
    private List<SettlementGetDetailBean.GetList> allGroup;
    private LayoutInflater inflater;

    public SettlementDetailsAdapter(Context context, List<SettlementGetDetailBean.GetList> allGroup) {
        this.context = context;
        this.allGroup = allGroup;
        inflater = LayoutInflater.from(context);
    }

    public SettlementDetailsAdapter(Context context) {
        this.context = context;
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
        return allGroup.get(position).getGameName();
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
            convertView = inflater.inflate(R.layout.settlement_details_item, null);
            holder.llySettlementItemClick = (LinearLayout) convertView.findViewById(R.id.lly_settle_details_click);
            holder.llySettlementItemContent = (LinearLayout) convertView.findViewById(R.id.lly_settle_details_content);
            holder.llySettlementItemOrder = (LinearLayout) convertView.findViewById(R.id.lly_settle_details_order);
            holder.tvSettlementItemOrder = (TextView) convertView.findViewById(R.id.tv_settle_details_order);
            holder.tvSettlementItemType = (TextView) convertView.findViewById(R.id.tv_settle_details_type);
            holder.tvSettlementItemContentOrder = (TextView) convertView.findViewById(R.id.tv_settle_details_content_order);
            holder.tvSettlementItemContentAuditor = (TextView) convertView.findViewById(R.id.tv_settle_details_content_auditor);
            holder.tvSettlementItemContentTime = (TextView) convertView.findViewById(R.id.tv_settle_details_content_time);
            holder.tvSettlementItemContentMerchant = (TextView) convertView.findViewById(R.id.tv_settle_details_content_merchant);
            holder.tvSettlementItemContentCash = (TextView) convertView.findViewById(R.id.tv_settle_details_content_cash);
            holder.tvSettlementItemContentMoney = (TextView) convertView.findViewById(R.id.tv_settle_details_content_money);
            holder.tvSettlementItemContentGame = (TextView) convertView.findViewById(R.id.tv_settle_details_content_game);
            holder.imgSettlementItemArrow = (ImageView) convertView.findViewById(R.id.img_settle_details_arrow);
            holder.viewSettlementItemLine = (View) convertView.findViewById(R.id.view_settle_details_line);
            holder.viewSettleDetailsOval = (View) convertView.findViewById(R.id.view_settle_details_oval);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.tvSettlementItemBook.setText(allGroup.get(position).getBookNum());
        holder.tvSettlementItemOrder.setText(StringOrSpace(allGroup.get(position).getOrderCode()));
        holder.tvSettlementItemContentOrder.setText(StringOrSpace(allGroup.get(position).getOrderCode()));
        holder.tvSettlementItemContentAuditor.setText(StringOrSpace(allGroup.get(position).getAuditor()));
        holder.tvSettlementItemContentTime.setText(StringOrSpace(allGroup.get(position).getAuditTime()));
//        holder.tvSettlementItemContentMerchant.setText(allGroup.get(position).getMerchantName());
//        holder.tvSettlementItemContentCash.setText(allGroup.get(position).getCashTime());
        holder.tvSettlementItemContentMoney.setText(StringOrSpace(allGroup.get(position).getWinMoney()));
        holder.tvSettlementItemContentGame.setText(StringOrSpace(allGroup.get(position).getGameName()));
//        holder.tvSettlementItemOrder.setTextIsSelectable(true);
        if (allGroup.get(position).getSettleStatus().equals("00")){
            holder.tvSettlementItemType.setText(context.getString(R.string.in_audit));
            holder.viewSettleDetailsOval.setBackgroundResource(R.drawable.settle_details_item_oval_red);
        }else {
            holder.tvSettlementItemType.setText(context.getString(R.string.settled));
            holder.viewSettleDetailsOval.setBackgroundResource(R.drawable.settle_details_item_oval_group);
        }
        if (allGroup.get(position).isType()){
            holder.llySettlementItemContent.setVisibility(View.VISIBLE);
            holder.viewSettlementItemLine.setVisibility(View.VISIBLE);
            holder.llySettlementItemOrder.setVisibility(View.VISIBLE);
            holder.imgSettlementItemArrow.setImageResource(R.drawable.arrow_top);
        }else {
            holder.llySettlementItemContent.setVisibility(View.GONE);
            holder.viewSettlementItemLine.setVisibility(View.GONE);
            holder.llySettlementItemOrder.setVisibility(View.GONE);
            holder.imgSettlementItemArrow.setImageResource(R.drawable.arrow_bottom);
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout llySettlementItemClick;
        LinearLayout llySettlementItemContent;
        LinearLayout llySettlementItemOrder;
        TextView tvSettlementItemOrder;
        TextView tvSettlementItemType;
        TextView tvSettlementItemContentOrder;
        TextView tvSettlementItemContentAuditor;
        TextView tvSettlementItemContentTime;
        TextView tvSettlementItemContentMerchant;
        TextView tvSettlementItemContentCash;
        TextView tvSettlementItemContentMoney;
        TextView tvSettlementItemContentGame;
        ImageView imgSettlementItemArrow;
        View viewSettlementItemLine;
        View viewSettleDetailsOval;
    }

    private String StringOrSpace(String content){
        if (content.equals("")){
            return "--";
        }
        return content;
    }

}
