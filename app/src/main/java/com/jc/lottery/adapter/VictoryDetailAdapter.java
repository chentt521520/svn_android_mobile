package com.jc.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.MatchListBean;
import com.jc.lottery.bean.VictoryBetsList;
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

public class VictoryDetailAdapter extends RecyclerView.Adapter<VictoryDetailHolderView> {

    private List<MatchListBean> list;
    private LayoutInflater mInflater;
    private Context mContext = null;

    public VictoryDetailAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<MatchListBean> list) {
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
    public VictoryDetailHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.victory_detail_item, parent, false);
        VictoryDetailHolderView holder = new VictoryDetailHolderView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, viewType);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final VictoryDetailHolderView holder, final int position) {
        String bean = list.get(position).getGame_name();
        if (bean != null) {
            holder.itemNum.setText((position + 1) + "");
            holder.itemHostName.setText(list.get(position).getHost_name());
            holder.itemAwayName.setText(list.get(position).getAway_name());
            holder.itemScore.setText(list.get(position).getHost_score() + " : " + list.get(position).getAway_score());
            if (list.get(position).getPlay_type().equals("0")){
                if (list.get(position).getResult().equals("1")){
                    holder.itemResult.setText(mContext.getString(R.string.win));
                }else if(list.get(position).getResult().equals("0")){
                    holder.itemResult.setText(mContext.getString(R.string.flat));
                }else {
                    holder.itemResult.setText(mContext.getString(R.string.fail));
                }
            }else {
                holder.itemResult.setText(list.get(position).getResult());
            }


//            if (list.get(position).getPlay_type().equals("0")) {
//                holder.itemLlyOne.setVisibility(View.VISIBLE);
//                holder.itemLlyTwo.setVisibility(View.GONE);
//            } else {
//                holder.itemLlyOne.setVisibility(View.GONE);
//                holder.itemLlyTwo.setVisibility(View.VISIBLE);
//            }

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
class VictoryDetailHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_item_away_num)
    TextView itemNum;
    @BindView(R.id.tv_item_host_name)
    TextView itemHostName;
    @BindView(R.id.tv_item_away_name)
    TextView itemAwayName;
    @BindView(R.id.tv_item_score)
    TextView itemScore;
    @BindView(R.id.tv_item_result)
    TextView itemResult;


    public VictoryDetailHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}