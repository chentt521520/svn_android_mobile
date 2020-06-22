package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.ReceivingRecordsActivity;

import java.util.List;

public class ReceivingRecordFilterAdapter extends BaseAdapter {

    private Context context;
    private List<ReceivingRecordsActivity.ItemCheck> strings;

    public ReceivingRecordFilterAdapter(Context context, List<ReceivingRecordsActivity.ItemCheck> strings) {
        this.context = context;
        this.strings = strings;
    }

    public void refresh(List<ReceivingRecordsActivity.ItemCheck> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strings == null ? 0 : strings.size();
    }

    @Override
    public Object getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_text, null);
        TextView text = view.findViewById(R.id.item_text);
        text.setText(strings.get(i).getText());

        if (strings.get(i).isCheck()) {
            text.setTextColor(Color.WHITE);
            text.setBackgroundResource(R.drawable.reward_et_red_bg);
        } else {
            text.setBackgroundResource(R.drawable.reward_et_bg);
            text.setTextColor(Color.GRAY);
        }
        return view;
    }
}
