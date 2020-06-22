package com.jc.lottery.adapter;

import android.view.ViewGroup;

import com.jc.lottery.R;

import xyz.zpayh.adapter.BaseMultiAdapter;
import xyz.zpayh.adapter.BaseViewHolder;

/**
 * @ 创建时间: 2018/09/25 on 12:21.
 * @ 描述：通用 adapter
 * @ 作者: vchao
 */
public class TongAdapter extends BaseMultiAdapter {

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int layoutRes) {
        setEmptyLayout(R.layout.layout_empty);
        return super.onCreateViewHolder(parent, layoutRes);
    }
}
