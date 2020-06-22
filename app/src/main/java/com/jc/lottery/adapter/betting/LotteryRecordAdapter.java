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
import com.jc.lottery.activity.lottery.OpenResultDetailActivity;
import com.jc.lottery.bean.NumberBean;
import com.jc.lottery.bean.resp.Resp_36x7_history;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class LotteryRecordAdapter extends RecyclerView.Adapter<LotteryHolderView> {

    private List<Resp_36x7_history.DrawListBean> list;
    private LayoutInflater mInflater;
    private Context mContext = null;
    private String gameAlias = "";

    public LotteryRecordAdapter(Context context,String gameAlias) {
        this.mContext = context;
        this.gameAlias = gameAlias;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<Resp_36x7_history.DrawListBean> list) {
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
    public LotteryHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lottery_record_item, parent, false);
        LotteryHolderView holder = new LotteryHolderView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final LotteryHolderView holder, final int position) {
        String bean = list.get(position).getDraw();
        if (bean != null) {
            holder.itemName.setText("No." + list.get(position).getDraw());
            holder.itemTime.setText(list.get(position).getPrizeTime());
            String[] s = list.get(position).getPrizeNum().split(" ");
            holder.itemNumber.removeAllViews();
//            String[] end = s[s.length - 1].split("-");
//            s[s.length - 1] = end[0];
//            for (int i = 1; i < end.length; i++) {
//                s[s.length - 1 ] = end[i];
//            }
            List<NumberBean> number = showList(s);
            if (number.size() < 2){
                holder.itemNumber.setVisibility(View.GONE);
            }else {
                holder.itemNumber.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < number.size(); i++) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                LinearLayout view = (LinearLayout) inflater.inflate(R.layout.lottery_record_item_text, null);
                TextView tvNumber = view.findViewById(R.id.tv_lottery_record_item);
                if (number.get(i).getType().equals("2")){
                    tvNumber.setBackgroundResource(R.drawable.red_balls);
                }else {
                    tvNumber.setBackgroundResource(R.drawable.red_ball);
                }
                tvNumber.setText(number.get(i).getNumber());
                holder.itemNumber.addView(view);
            }
            holder.itemLly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("gameAlias",gameAlias);
                    intent.putExtra("draw",list.get(position).getDraw());
                    intent.setClass(mContext, OpenResultDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private List<NumberBean> showList(String[] s){
        List<NumberBean> list = new ArrayList<NumberBean>();
        String[] endString = s[s.length - 1].split("-");
        if (endString.length > 1){
            for (int i = 0; i < s.length - 1; i++) {
                NumberBean numberBean = new NumberBean();
                numberBean.setNumber(s[i]);
                numberBean.setType("1");
                list.add(numberBean);
            }
            for (int i = 0; i < endString.length; i++) {
                NumberBean numberBean = new NumberBean();
                numberBean.setNumber(endString[i]);
                if (i == endString.length - 1) {
                    numberBean.setType("2");
                }else {
                    numberBean.setType("1");
                }
                list.add(numberBean);
            }
        }else {
            for (int i = 0; i < s.length; i++) {
                NumberBean numberBean = new NumberBean();
                numberBean.setNumber(s[i]);
                numberBean.setType("1");
                list.add(numberBean);
            }
//            list = s.
        }
        return list;
    }

}

class LotteryHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_lottery_item_name)
    TextView itemName;
    @BindView(R.id.tv_lottery_item_time)
    TextView itemTime;
    @BindView(R.id.lly_lottery_item_number)
    LinearLayout itemNumber;
    @BindView(R.id.lly_lottery_item)
    LinearLayout itemLly;

    public LotteryHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }

}
