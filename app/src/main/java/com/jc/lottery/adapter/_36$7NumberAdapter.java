package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jc.lottery.R;

import java.util.List;

public class _36$7NumberAdapter extends BaseAdapter {

    private List<String> numsList;

    private List<String> selectedList;

    private LayoutInflater inflater;

    public _36$7NumberAdapter(Context context, List<String> numsList, List<String> selectedList) {
        this.numsList = numsList;
        this.selectedList = selectedList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return numsList.size();
    }

    @Override
    public Object getItem(int position) {
        return numsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.select_kl8_number_adapter, null);
            holder.text = (TextView) convertView.findViewById(R.id.sna_number);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (selectedList.contains(numsList.get(position))) {
            holder.text.setBackgroundResource(R.drawable.red_ball);
            holder.text.setTextColor(Color.WHITE);
        } else {
            holder.text.setBackgroundResource(R.drawable.gray_ball);
            holder.text.setTextColor(Color.parseColor("#00923d"));
        }

        holder.text.setText(numsList.get(position));
        return convertView;
    }

    class Holder {
        TextView text;
    }
}
