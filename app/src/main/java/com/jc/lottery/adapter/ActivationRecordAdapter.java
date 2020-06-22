package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.ActivationRecordBean;
import com.jc.lottery.bean.SettlementRecordBean;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;

//激活记录
public class ActivationRecordAdapter extends BaseAdapter {

    private Context context;
    private List<ActivationRecordBean> allGroup;
    private LayoutInflater inflater;

    public ActivationRecordAdapter(Context context, List<ActivationRecordBean> allGroup) {
        this.context = context;
        this.allGroup = allGroup;
        inflater = LayoutInflater.from(context);
    }

    public ActivationRecordAdapter(Context context) {
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    public List<ActivationRecordBean> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(List<ActivationRecordBean> allGroup) {
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
            convertView = inflater.inflate(R.layout.activation_record_en_item, null);
            holder.tvActivationRecordItemNum = (TextView) convertView.findViewById(R.id.tv_record_num);
            holder.tvActivationRecordItemStatus = (TextView) convertView.findViewById(R.id.tv_record_state);
            holder.tvActivationRecordItemOrder = (TextView) convertView.findViewById(R.id.tv_record_order);
            holder.tvActivationRecordItemTime = (TextView) convertView.findViewById(R.id.tv_record_time);
            holder.tvActivationRecordItemGame = (TextView) convertView.findViewById(R.id.tv_record_game);
            holder.tvActivationRecordItemPai = (TextView) convertView.findViewById(R.id.tv_record_pai);
            holder.tvActivationRecordItemPerson = (TextView) convertView.findViewById(R.id.tv_record_person);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvActivationRecordItemNum.setText((position + 1) + "");
        holder.tvActivationRecordItemOrder.setText(allGroup.get(position).getOrderCode());
        holder.tvActivationRecordItemPerson.setText(allGroup.get(position).getRecipient());
        holder.tvActivationRecordItemGame.setText(context.getString(R.string.game_type) + allGroup.get(position).getGameName());
        if (allGroup.get(position).getSendPerson().equals("")){
            holder.tvActivationRecordItemPai.setText(context.getString(R.string.delivery_persons) + "--");
        }else {
            holder.tvActivationRecordItemPai.setText(context.getString(R.string.delivery_persons) + allGroup.get(position).getSendPerson());
        }
        if (allGroup.get(position).getActiveState().equals("00")){
            holder.tvActivationRecordItemStatus.setText(context.getString(R.string.not_active));
            holder.tvActivationRecordItemStatus.setTextColor(Color.rgb(255, 171, 0));
//            holder.tvSettleRecordItemStatus.setText(context.getString(R.string.to_be_audited));
        }else{
            holder.tvActivationRecordItemStatus.setText(context.getString(R.string.activated));
            holder.tvActivationRecordItemStatus.setTextColor(Color.rgb(21, 119, 255));
        }
        holder.tvActivationRecordItemTime.setText(allGroup.get(position).getActiveTime());

        return convertView;
    }

    class ViewHolder {
        TextView tvActivationRecordItemNum;
        TextView tvActivationRecordItemStatus;
        TextView tvActivationRecordItemOrder;
        TextView tvActivationRecordItemTime;
        TextView tvActivationRecordItemPerson;
        TextView tvActivationRecordItemPai;
        TextView tvActivationRecordItemGame;
    }

}
