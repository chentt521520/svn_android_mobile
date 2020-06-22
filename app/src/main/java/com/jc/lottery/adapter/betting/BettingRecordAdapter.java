package com.jc.lottery.adapter.betting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.lottery.BettingRecordsDetailActivity;
import com.jc.lottery.bean.resp.BettingListInfo;
import com.jc.lottery.util.MoneyUtil;
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

public class BettingRecordAdapter extends RecyclerView.Adapter<ParamHolderView> {

    private List<BettingListInfo> list;
    private LayoutInflater mInflater;
    private Context mContext = null;
    private String gameAlias = "";
    private String type = "";

    public BettingRecordAdapter(Context context,String gameAlias,String type) {
        this.mContext = context;
        this.gameAlias = gameAlias;
        this.type = type;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<BettingListInfo> list) {
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
    public ParamHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.betting_record_item, parent, false);
        ParamHolderView holder = new ParamHolderView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ParamHolderView holder, final int position) {
        String bean = list.get(position).getGameName();
        if (bean != null) {
            holder.itemName.setText(list.get(position).getOrderCode());
            holder.itemTime.setText(timeStamp2Date(Long.parseLong(list.get(position).getBuyTime())));
            if (type.equals("1")) {
                holder.itemMoney.setText(MoneyUtil.getIns().GetMoney(list.get(position).getOrderMoney()) + mContext.getString(R.string.price_unit));
            }else {
                holder.itemMoney.setText(MoneyUtil.getIns().GetMoney(list.get(position).getWinAmount()) + mContext.getString(R.string.price_unit));
            }
            holder.itemLly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("gameAlias",gameAlias);
                    intent.putExtra("order",list.get(position).getOrderCode());
                    intent.putExtra("type",type);
                    intent.setClass(mContext, BettingRecordsDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
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
class ParamHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.lly_betting_item)
    LinearLayout itemLly;
    @BindView(R.id.tv_betting_item_name)
    TextView itemName;
    @BindView(R.id.tv_betting_item_time)
    TextView itemTime;
    @BindView(R.id.tv_betting_item_money)
    TextView itemMoney;

    public ParamHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }

}