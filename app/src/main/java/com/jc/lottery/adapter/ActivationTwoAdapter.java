package com.jc.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.DeliveryDetailsBean;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class ActivationTwoAdapter extends RecyclerView.Adapter<ActivationTwoHolderView> {

    private List<DeliveryDetailsBean> list;
    private LayoutInflater mInflater;
    private Context mContext = null;

    public ActivationTwoAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<DeliveryDetailsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

    @Override
    public ActivationTwoHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.activation_two_item, parent, false);
        ActivationTwoHolderView holder = new ActivationTwoHolderView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v, viewType);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ActivationTwoHolderView holder, final int position) {
        String bean = list.get(position).getBookNum();
        holder.tvNo.setText((position + 1) + "");
        holder.tvActivationItemBatch.setText(list.get(position).getSchemeNum());
        if (bean != null) {
            holder.tvActivationItemBook.setText(bean);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(mContext, SPkey.Language);
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
class ActivationTwoHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_activation_item_book)
    TextView tvActivationItemBook;
    @BindView(R.id.tv_activation_item_batch)
    TextView tvActivationItemBatch;
    @BindView(R.id.tv_activation_item_number)
    TextView tvNo;

    public ActivationTwoHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}
