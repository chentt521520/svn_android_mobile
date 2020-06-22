package com.jc.lottery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.BluetoothNewActivity;
import com.jc.lottery.bean.BluetoothBean;

import java.util.List;

/**
 * Created by Administrator on 2019/4/28.
 */

public class BluetoothNewAdapter extends RecyclerView.Adapter<BluetoothNewAdapter.ViewHolder> {

    private List<BluetoothBean> mDatas;
    private BluetoothNewActivity activity;
    public BluetoothNewAdapter(List<BluetoothBean> data, BluetoothNewActivity activity) {
        this.mDatas = data;
        this.activity = activity;
    }

    public void setData(List<BluetoothBean> data){
        this.mDatas = data;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(mDatas.get(position).getName());
        if (mDatas.get(position).getType().equals("256")){
            holder.type.setImageResource(R.drawable.bluetooth_pc);
        }else {
            holder.type.setImageResource(R.drawable.bluetooth_no);
        }
        holder.llyBluetoothItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = holder.title.getText().toString().trim();
                String address = title.substring(title.length() - 17);
                activity.showBluetooth(address);
//                Intent intent = new Intent();
//                intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
//                activity.setResult(Activity.RESULT_OK, intent);
//                activity.finish();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bluetooth_item, parent, false);
        return new ViewHolder(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final LinearLayout llyBluetoothItem;
        public final TextView title;
        public final ImageView type;
        public ViewHolder(View v) {
            super(v);
            llyBluetoothItem = (LinearLayout) v.findViewById(R.id.lly_bluetooth_item);
            title = (TextView) v.findViewById(R.id.tv_bluetooth_name);
            type = (ImageView) v.findViewById(R.id.img_bluetooth_type);
        }
    }
}
