package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jc.lottery.R;
import com.jc.lottery.bean.resp.ReceivingActivationBean;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


//领取记录激活信息
public class ReceivingActivationAdapter extends BaseAdapter {

    private List<ReceivingActivationBean> allGroup;
    private LayoutInflater inflater;
    private Context context;

    public ReceivingActivationAdapter(Context context, List<ReceivingActivationBean> allGroup) {
        this.allGroup = allGroup;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public ReceivingActivationAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public List<ReceivingActivationBean> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(List<ReceivingActivationBean> allGroup) {
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
            convertView = inflater.inflate(R.layout.receiving_activation_item, null);
            holder.tvReceivingItemNo = (TextView) convertView.findViewById(R.id.tv_receiving_item_no);
            holder.tvReceivingItemOne = (TextView) convertView.findViewById(R.id.tv_receiving_item_one);
            holder.tvReceivingItemTwo = (TextView) convertView.findViewById(R.id.tv_receiving_item_two);
            holder.tvReceivingItemType = (TextView) convertView.findViewById(R.id.tv_receiving_item_type);
            holder.tvReceivingItemTime = (TextView) convertView.findViewById(R.id.tv_receiving_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String bean = getItem(position);
        holder.tvReceivingItemNo.setText(allGroup.get(position).getIndex() + "");
        holder.tvReceivingItemOne.setText(allGroup.get(position).getSchemeNum());
        holder.tvReceivingItemTwo.setText(allGroup.get(position).getBookNum());
        if (allGroup.get(position).getActiveState().equals("00")) {
            holder.tvReceivingItemType.setText(context.getString(R.string.not_active));
            holder.tvReceivingItemType.setTextColor(Color.rgb(0,165,83));
        }else {
            holder.tvReceivingItemType.setText(context.getString(R.string.activated));
            holder.tvReceivingItemType.setTextColor(Color.rgb(22,119,255));
        }
        if (null != allGroup.get(position).getActiveTime()) {
            if (allGroup.get(position).getActiveTime().equals("") || allGroup.get(position).getActiveTime().equals("0")) {
                holder.tvReceivingItemTime.setText("--");
            } else {
                holder.tvReceivingItemTime.setText(timeStamp2Date(context, Long.parseLong(allGroup.get(position).getActiveTime())));
            }
        }else {
            holder.tvReceivingItemTime.setText("--");
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvReceivingItemNo;
        TextView tvReceivingItemOne;
        TextView tvReceivingItemTwo;
        TextView tvReceivingItemType;
        TextView tvReceivingItemTime;
    }

    public String timeStamp2Date(Context context,long time) {
        String language = SPUtils.look(context, SPkey.Language);
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
