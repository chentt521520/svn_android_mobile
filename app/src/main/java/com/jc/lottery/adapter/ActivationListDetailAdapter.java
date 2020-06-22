package com.jc.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.DeliveryDetailsListBean;
import com.jc.lottery.bean.SettlementGetDetailBean;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;


//激活详情
public class ActivationListDetailAdapter extends BaseAdapter {

    private List<DeliveryDetailsListBean> allGroup;
    private LayoutInflater inflater;
    private Context context;

    public ActivationListDetailAdapter(Context context, List<DeliveryDetailsListBean> allGroup) {
        this.allGroup = allGroup;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public ActivationListDetailAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public List<DeliveryDetailsListBean> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(List<DeliveryDetailsListBean> allGroup) {
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
            convertView = inflater.inflate(R.layout.activation_list_details_item, null);
            holder.tvItemNo = (TextView) convertView.findViewById(R.id.tv_details_item_no);
            holder.tvItemScheme = (TextView) convertView.findViewById(R.id.tv_details_item_scheme);
            holder.tvItemCarton = (TextView) convertView.findViewById(R.id.tv_details_item_carton);
            holder.tvItemBook = (TextView) convertView.findViewById(R.id.tv_details_item_book);
//            holder.tvReceivingItemType = (TextView) convertView.findViewById(R.id.tv_receiving_item_type);
//            holder.tvReceivingItemTime = (TextView) convertView.findViewById(R.id.tv_receiving_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        String bean = getItem(position);
        holder.tvItemNo.setText((position + 1) + "");
        holder.tvItemScheme.setText(allGroup.get(position).getSchemeNum());
        holder.tvItemCarton.setText(allGroup.get(position).getCartonNo());
        holder.tvItemBook.setText(allGroup.get(position).getBookNum());
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
        TextView tvItemNo;
        TextView tvItemScheme;
        TextView tvItemCarton;
        TextView tvItemBook;
//        TextView tvReceivingItemType;
//        TextView tvReceivingItemTime;
    }

}
