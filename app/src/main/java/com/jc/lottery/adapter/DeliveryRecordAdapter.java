package com.jc.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryAdminDetailsActivity;
import com.jc.lottery.activity.immediate.DeliveryAgentDetailsActivity;
import com.jc.lottery.activity.immediate.DeliveryDetailsActivity;
import com.jc.lottery.activity.immediate.DeliveryRecordsActivity;
import com.jc.lottery.activity.immediate.DeliveryRecordsDetailActivity;
import com.jc.lottery.activity.immediate.ReceivingRecordsDetailActivity;
import com.jc.lottery.bean.RecordInfoBean;
import com.jc.lottery.content.Constant;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;

import java.util.List;


public class DeliveryRecordAdapter extends BaseAdapter {

    private DeliveryRecordsActivity context;
    private List<RecordInfoBean> allGroup;
    private LayoutInflater inflater;
    private String type;

    public DeliveryRecordAdapter(DeliveryRecordsActivity context, List<RecordInfoBean> allGroup) {
        this.context = context;
        this.allGroup = allGroup;
        inflater = LayoutInflater.from(context);
    }

    public DeliveryRecordAdapter(DeliveryRecordsActivity context,String type) {
        this.context = context;
        this.type = type;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.delivery_records_item, null);
            holder.llyDeliveryItem = (LinearLayout) convertView.findViewById(R.id.lly_delivery_item);
            holder.llyDeliveryItemTwo = (LinearLayout) convertView.findViewById(R.id.lly_delivery_record_item_two);
            holder.tvDeliveryItemOrder = (TextView) convertView.findViewById(R.id.tv_delivery_item_order);
            holder.tvDeliveryItemTime = (TextView) convertView.findViewById(R.id.tv_delivery_item_time);
            holder.tvDeliveryItemName = (TextView) convertView.findViewById(R.id.tv_delivery_item_name);
            holder.tvDeliveryItemEquipment = (TextView) convertView.findViewById(R.id.tv_delivery_item_equipment);
            holder.tvDeliveryItemReceiver  = (TextView) convertView.findViewById(R.id.tv_delivery_item_receiver);
            holder.tvDeliveryItemNumber  = (TextView) convertView.findViewById(R.id.tv_delivery_item_number);
            holder.tvDeliveryItemStatus  = (TextView) convertView.findViewById(R.id.tv_delivery_item_status);
//            holder.btnDeliveryItemSendTo  = (Button) convertView.findViewById(R.id.btn_delivery_item_send_to);
            holder.btnDeliveryItem  = (TextView) convertView.findViewById(R.id.btn_delivery_item);
            holder.btnDeliveryItemManagement  = (Button) convertView.findViewById(R.id.btn_delivery_item_management);
            holder.btnDeliveryItemGo  = (Button) convertView.findViewById(R.id.btn_delivery_item_go);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String bean = getItem(position);
        holder.tvDeliveryItemOrder.setText(allGroup.get(position).getIndex() +  ". " + context.getString(R.string.delivery_number) + allGroup.get(position).getOrderCode());
        holder.tvDeliveryItemTime.setText(context.getString(R.string.time) + allGroup.get(position).getCreateTime());
        holder.tvDeliveryItemName.setText(allGroup.get(position).getGameName());
        holder.tvDeliveryItemEquipment.setText(allGroup.get(position).getGetDevice());
        holder.tvDeliveryItemReceiver.setText(allGroup.get(position).getRecipient());
        holder.tvDeliveryItemNumber.setText(allGroup.get(position).getTicketNum());
        if (allGroup.get(position).getPayState().equals("00")){
            holder.tvDeliveryItemStatus.setText(context.getString(R.string.successful_payment));
            holder.tvDeliveryItemStatus.setTextColor(Constant.success);
        }else if (allGroup.get(position).getPayState().equals("01")){
            holder.tvDeliveryItemStatus.setText(context.getString(R.string.failure_to_pay));
            holder.tvDeliveryItemStatus.setTextColor(Constant.chupiao_fail);
        }else {
            holder.tvDeliveryItemStatus.setText(context.getString(R.string.to_be_paid));
            holder.tvDeliveryItemStatus.setTextColor(Constant.dispatched);
        }
        if (type.equals("02")) {
//            holder.llyDeliveryItemTwo.setVisibility(View.GONE);
//            holder.btnDeliveryItem.setVisibility(View.VISIBLE);
            holder.btnDeliveryItem.setText(context.getString(R.string.to_be_dispatched));
            holder.btnDeliveryItem.setTextColor(Color.rgb(255, 171, 0));
            holder.btnDeliveryItemGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("recordInfoBean", allGroup.get(position));
                    intent.putExtra("gameAlias", allGroup.get(position).getGameName());
                    intent.putExtra("orderCode", allGroup.get(position).getOrderCode());
                    if (SPUtils.look(context, SPkey.roleAlias).equals("gly")) {
//                        intent.setClass(context, DeliveryDetailsActivity.class);
                        intent.setClass(context, DeliveryAdminDetailsActivity.class);
                    }else {
                        intent.setClass(context, DeliveryAgentDetailsActivity.class);
                    }
                    context.startActivity(intent);
                }
            });
            holder.btnDeliveryItemManagement.setVisibility(View.GONE);
            holder.btnDeliveryItemGo.setVisibility(View.VISIBLE);
        }else if (type.equals("00")){
            holder.btnDeliveryItem.setText(context.getString(R.string.delivered));
            holder.btnDeliveryItem.setTextColor(Color.rgb(22, 119, 255));
            holder.btnDeliveryItemManagement.setVisibility(View.VISIBLE);
            holder.btnDeliveryItemGo.setVisibility(View.GONE);
//            holder.btnDeliveryItemSendTo.setVisibility(View.GONE);
        }else {
            holder.btnDeliveryItem.setText(context.getString(R.string.in_delivery));
            holder.btnDeliveryItem.setTextColor(Color.rgb(255, 51, 51));
            holder.btnDeliveryItemManagement.setVisibility(View.VISIBLE);
            holder.btnDeliveryItemGo.setVisibility(View.GONE);
//            holder.btnDeliveryItemSendTo.setVisibility(View.GONE);
        }
        holder.llyDeliveryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("activityType", "01");
                intent.putExtra("recordDetailsId", allGroup.get(position).getId() + "");
                intent.setClass(context, DeliveryRecordsDetailActivity.class);
                context.startActivity(intent);
            }
        });
        holder.btnDeliveryItemManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showManagementInfo(allGroup.get(position).getId() + "",allGroup.get(position).getOrderCode());
            }
        });
//        holder.tvRewardRecordItemName.setText(bean.toString().trim());
//        holder.tvRewardRecordItemTime.setText(allGroup.get(position).getCreateTime());
//        if (!allGroup.get(position).getPayState().equals("02")) {
//            if (allGroup.get(position).getStatus().equals("00")) {
//                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.has_been_received));
//                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(48, 178, 102));
//            } else if (allGroup.get(position).getStatus().equals("01")) {
//                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.chupiao_fail));
//                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
//            } else if (allGroup.get(position).getStatus().equals("02")) {
//                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.to_be_dispatched));
//                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(0, 165, 83));
//            } else {
//                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.order_invalidation));
//                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
//            }
//        }else {
//            if (allGroup.get(position).getStatus().equals("02")) {
//                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.to_be_paid));
//                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(0, 165, 83));
//            }else {
//                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.order_invalidation));
//                holder.tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
//            }
//        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout llyDeliveryItem;
        LinearLayout llyDeliveryItemTwo;
        TextView tvDeliveryItemOrder;
        TextView tvDeliveryItemTime;
        TextView tvDeliveryItemName;
        TextView tvDeliveryItemEquipment;
        TextView tvDeliveryItemReceiver;
        TextView tvDeliveryItemNumber;
        TextView tvDeliveryItemStatus;
//        Button btnDeliveryItemSendTo;
        TextView btnDeliveryItem;
        Button btnDeliveryItemManagement;
        Button btnDeliveryItemGo;
    }

}
