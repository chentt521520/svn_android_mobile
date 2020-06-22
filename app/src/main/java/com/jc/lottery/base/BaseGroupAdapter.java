package com.jc.lottery.base;

import android.content.Context;
import android.widget.BaseAdapter;

import com.jc.lottery.bean.type.Group;
import com.jc.lottery.bean.type.LotteryType;


public abstract class BaseGroupAdapter<T extends LotteryType> extends BaseAdapter {

    public Group<T> group = null;

    public BaseGroupAdapter(Context context) {
    }

    @Override
    public int getCount() {

        return (group == null) ? 0 : group.size();
    }

    @Override
    public Object getItem(int position) {

        return (group == null) ? null : group.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {

        return (group == null) ? true : group.isEmpty();
    }

    public void setGroup(Group<T> g) {
        group = g;
        notifyDataSetInvalidated();
    }

}
