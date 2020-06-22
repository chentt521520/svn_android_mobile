package com.jc.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.StatisticsBean;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class StatisticsAmountAdapter extends RecyclerView.Adapter<StatisticsAmountHolderView> {

    private List<StatisticsBean> list;
    private LayoutInflater mInflater;
    private Context mContext = null;

    public StatisticsAmountAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<StatisticsBean> list) {
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
    public StatisticsAmountHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.statistics_amount_item, parent, false);
        StatisticsAmountHolderView holder = new StatisticsAmountHolderView(view);
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
    public void onBindViewHolder(final StatisticsAmountHolderView holder, final int position) {
        String bean = list.get(position).getName();
        if (bean != null) {
            holder.itemName.setText(list.get(position).getName());
            holder.itemContent.setText(MoneyUtil.getIns().GetMoney(list.get(position).getContent()));
        }
        holder.itemIcon.setImageResource(showView(list.get(position).getId()));
        if (position == 0 || position == 1 || position == 4 || position == 5){
            holder.itemLly.setBackgroundResource(R.drawable.statistics_item_bg_one);
        }else {
            holder.itemLly.setBackgroundResource(R.drawable.statistics_item_bg_two);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private int showView(int id){
        switch (id){
            case 1:
                return R.drawable.statistics_one;
            case 2:
                return R.drawable.statistics_two;
            case 3:
                return R.drawable.statistics_three;
            case 33:
                return R.drawable.statistics_three;
            case 4:
                return R.drawable.statistics_four;
            case 44:
                return R.drawable.statistics_four;
            case 5:
                return R.drawable.statistics_five;
            case 6:
                return R.drawable.statistics_six;
            case 7:
                return R.drawable.statistics_seven;
            case 77:
                return R.drawable.statistics_seven;
            case 8:
                return R.drawable.statistics_eight;
            case 88:
                return R.drawable.statistics_eight;
            case 11:
                return R.drawable.statistics_three;
            case 12:
                return R.drawable.statistics_three;
        }
        return R.drawable.statistics_one;
    }

}

class StatisticsAmountHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.lly_statistics_amount_item)
    RelativeLayout itemLly;
    @BindView(R.id.img_statistics_item)
    ImageView itemIcon;
    @BindView(R.id.tv_statistics_item_name)
    TextView itemName;
    @BindView(R.id.tv_statistics_item_content)
    TextView itemContent;

    public StatisticsAmountHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}