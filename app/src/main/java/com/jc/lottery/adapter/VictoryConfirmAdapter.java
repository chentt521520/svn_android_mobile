package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.VictoryBean;
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

public class VictoryConfirmAdapter extends RecyclerView.Adapter<VictoryConfirmView> {

    private List<VictoryBean.MatchList> list;
    private LayoutInflater mInflater;
    private Context mContext = null;

    public VictoryConfirmAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<VictoryBean.MatchList> list) {
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
    public VictoryConfirmView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.victory_confirm_item, parent, false);
        VictoryConfirmView holder = new VictoryConfirmView(view);
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
    public void onBindViewHolder(final VictoryConfirmView holder, final int position) {
        String bean = list.get(position).getGame_name();
        if (bean != null) {
            holder.itemNum.setText((position + 1) + "");
            holder.itemHostName.setText(list.get(position).getHost_name());
            holder.itemAwayName.setText(list.get(position).getAway_name());

//            if (list.get(position).getPlay_type().equals("0")) {
//                holder.itemLlyOne.setVisibility(View.VISIBLE);
//                holder.itemLlyTwo.setVisibility(View.GONE);
//            } else {
//                holder.itemLlyOne.setVisibility(View.GONE);
//                holder.itemLlyTwo.setVisibility(View.VISIBLE);
//            }
            showPosView(holder,list.get(position).getPlay_type(),list.get(position).getTypeSelect());

        }

    }
    public void showPosView(final VictoryConfirmView holder, String playType, List<Boolean> select){
        if (select.size() < 4) {
            for (int i = 0; i < 3; i++) {
                switch (i) {
                    case 0:
//                    showItemView(holder);
                        if (select.get(0)){
                            holder.tvVictoryOne.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictoryOne.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
//                    showItemView(holder);
                        if (select.get(1)) {
                            holder.tvVictoryTwo.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictoryTwo.setVisibility(View.GONE);
                        }
                        break;
                    case 2:
//                    showItemView(holder);
                        if (select.get(2)) {
                            holder.tvVictoryThree.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictoryThree.setVisibility(View.GONE);
                        }
                        break;
                }
            }
            holder.tvVictoryFour.setVisibility(View.GONE);
        }else {
            for (int i = 0; i < 9; i++) {
                switch (i) {
                    case 0:
//                    showItemView(holder);
                        if (select.get(0)){
                            holder.tvVictoryOne.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictoryOne.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
//                    showItemView(holder);
                        if (select.get(1)) {
                            holder.tvVictoryTwo.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictoryTwo.setVisibility(View.GONE);
                        }
                        break;
                    case 2:
//                    showItemView(holder);
                        if (select.get(2)) {
                            holder.tvVictoryThree.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictoryThree.setVisibility(View.GONE);
                        }
                        break;
                    case 3:
//                    showItemView(holder);
                        if (select.get(3)){
                            holder.tvVictoryFour.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictoryFour.setVisibility(View.GONE);
                        }
                        break;
                    case 4:
//                    showItemView(holder);
                        if (select.get(4)) {
                            holder.tvVictoryOne.setVisibility(View.VISIBLE);
                            holder.tvVictoryOne.setText("1");
                        }else {
                             if (!select.get(0)) {
                                 holder.tvVictoryOne.setVisibility(View.GONE);
                             }
                        }
                        break;
                    case 5:
//                    showItemView(holder);
                        if (select.get(5)) {
                            holder.tvVictoryThree.setVisibility(View.VISIBLE);
                            holder.tvVictoryThree.setText("2");
                        }else {
                            if (!select.get(2)) {
                                holder.tvVictoryThree.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case 6:
//                    showItemView(holder);
                        if (select.get(6)){
                            holder.tvVictoryFive.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictoryFive.setVisibility(View.GONE);
                        }
                        break;
                    case 7:
//                    showItemView(holder);
                        if (select.get(7)) {
                            holder.tvVictorySix.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictorySix.setVisibility(View.GONE);
                        }
                        break;
                    case 8:
//                    showItemView(holder);
                        if (select.get(8)) {
                            holder.tvVictorySeven.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvVictorySeven.setVisibility(View.GONE);
                        }
                        break;
                }
            }
//            holder.tvVictoryFour.setVisibility(View.VISIBLE);
//            for (int i = 0; i < 6; i++) {
//                switch (i) {
//                    case 0:
//                        showItemView(holder);
//                        holder.tvVictoryFour.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                        holder.tvVictoryFour.setTextColor(Color.rgb(255, 255, 255));
//                        break;
//                    case 1:
//                        showItemView(holder);
//                        holder.tvVictoryFive.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                        holder.tvVictoryFive.setTextColor(Color.rgb(255, 255, 255));
//                        break;
//                    case 2:
//                        showItemView(holder);
//                        holder.tvVictorySix.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                        holder.tvVictorySix.setTextColor(Color.rgb(255, 255, 255));
//                        break;
//                    case 3:
//                        showItemView(holder);
//                        holder.tvVictorySeven.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                        holder.tvVictorySeven.setTextColor(Color.rgb(255, 255, 255));
//                        break;
//                    case 4:
//                        showItemView(holder);
//                        holder.tvVictoryEight.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                        holder.tvVictoryEight.setTextColor(Color.rgb(255, 255, 255));
//                        break;
//                    case 5:
//                        showItemView(holder);
//                        holder.tvVictoryNine.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                        holder.tvVictoryNine.setTextColor(Color.rgb(255, 255, 255));
//                        break;
//                }
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

class VictoryConfirmView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_item_away_num)
    TextView itemNum;
    @BindView(R.id.tv_item_host_name)
    TextView itemHostName;
    @BindView(R.id.tv_item_away_name)
    TextView itemAwayName;
    @BindView(R.id.tv_item_type_one)
    TextView tvVictoryOne;
    @BindView(R.id.tv_item_type_two)
    TextView tvVictoryTwo;
    @BindView(R.id.tv_item_type_three)
    TextView tvVictoryThree;
    @BindView(R.id.tv_item_type_four)
    TextView tvVictoryFour;
    @BindView(R.id.tv_item_type_five)
    TextView tvVictoryFive;
    @BindView(R.id.tv_item_type_six)
    TextView tvVictorySix;
    @BindView(R.id.tv_item_type_seven)
    TextView tvVictorySeven;


    public VictoryConfirmView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}