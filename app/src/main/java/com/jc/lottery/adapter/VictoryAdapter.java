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
import com.jc.lottery.activity.victory.VictoryDefeatActivity;
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

public class VictoryAdapter extends RecyclerView.Adapter<VictoryHolderView> {

    private List<VictoryBean.MatchList> list;
    private LayoutInflater mInflater;
    private Context mContext = null;
    private VictoryDefeatActivity victoryDefeatActivity = null;

    public VictoryAdapter(Context context,VictoryDefeatActivity victoryDefeatActivity) {
        this.mContext = context;
        this.victoryDefeatActivity = victoryDefeatActivity;
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
    public VictoryHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.victory_item, parent, false);
        VictoryHolderView holder = new VictoryHolderView(view);
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
    public void onBindViewHolder(final VictoryHolderView holder, final int position) {
        String bean = list.get(position).getGame_name();
        if (bean != null) {
            holder.itemNum.setText((position + 1) + ". ");
            holder.itemHostName.setText(list.get(position).getHost_name());
            holder.itemAwayName.setText(list.get(position).getAway_name());
            holder.itemTime.setText(list.get(position).getGame_name() + ". " + timeStamp2Date(Long.parseLong(list.get(position).getGame_time())));

//            if (list.get(position).getPlay_type().equals("0")) {
//                holder.itemLlyOne.setVisibility(View.VISIBLE);
//                holder.itemLlyTwo.setVisibility(View.GONE);
//            } else {
//                holder.itemLlyOne.setVisibility(View.GONE);
//                holder.itemLlyTwo.setVisibility(View.VISIBLE);
//            }
            if (position < 14) {
                holder.itemLlyOne.setVisibility(View.VISIBLE);
                holder.itemLlyTwo.setVisibility(View.GONE);
            } else {
                if (list.get(position).getPlay_type().equals("0")){
                    holder.itemLlyOne.setVisibility(View.VISIBLE);
                    holder.itemLlyTwo.setVisibility(View.GONE);
                }else {
                    holder.itemLlyOne.setVisibility(View.GONE);
                    holder.itemLlyTwo.setVisibility(View.VISIBLE);
                }

            }
            showPosView(holder,list.get(position).getPlay_type(),list.get(position).getTypeSelect());
            holder.tvVictoryOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showItemView(holder);
//                    list.get(position).setType("0");
                    if (position == 14){
                        showBoolList(false,holder);
                    }
                    if (list.get(position).getTypeSelect().get(0)){
                        holder.tvVictoryOne.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictoryOne.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictoryOne.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictoryOne.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(0,!list.get(position).getTypeSelect().get(0));
                    showListNum();
                }
            });
            holder.tvVictoryTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showItemView(holder);
//                    list.get(position).setType("1");
//                    holder.tvVictoryTwo.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                    holder.tvVictoryTwo.setTextColor(Color.rgb(255,255,255));
                    if (position == 14){
                        showBoolList(false,holder);
                    }
                    if (list.get(position).getTypeSelect().get(1)){
                        holder.tvVictoryTwo.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictoryTwo.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictoryTwo.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictoryTwo.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(1,!list.get(position).getTypeSelect().get(1));
                    showListNum();
                }
            });
            holder.tvVictoryThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showItemView(holder);
//                    list.get(position).setType("2");
//                    holder.tvVictoryThree.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                    holder.tvVictoryThree.setTextColor(Color.rgb(255,255,255));
                    if (position == 14){
                        showBoolList(false,holder);
                    }
                    if (list.get(position).getTypeSelect().get(2)){
                        holder.tvVictoryThree.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictoryThree.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictoryThree.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictoryThree.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(2,!list.get(position).getTypeSelect().get(2));
                    showListNum();
                }
            });
            holder.tvVictoryFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showItemView(holder);
                    showBoolList(true,holder);
                    if (list.get(position).getTypeSelect().get(3)){
                        holder.tvVictoryFour.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictoryFour.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictoryFour.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictoryFour.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(3,!list.get(position).getTypeSelect().get(3));
                    showListNum();
//                    list.get(position).setType("0");
//                    holder.tvVictoryFour.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                    holder.tvVictoryFour.setTextColor(Color.rgb(255,255,255));
                }
            });
            holder.tvVictoryFive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBoolList(true,holder);
                    if (list.get(position).getTypeSelect().get(4)){
                        holder.tvVictoryFive.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictoryFive.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictoryFive.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictoryFive.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(4,!list.get(position).getTypeSelect().get(4));
                    showListNum();
//                    showItemView(holder);
//                    list.get(position).setType("1");
//                    holder.tvVictoryFive.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                    holder.tvVictoryFive.setTextColor(Color.rgb(255,255,255));
                }
            });
            holder.tvVictorySix.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBoolList(true,holder);
                    if (list.get(position).getTypeSelect().get(5)){
                        holder.tvVictorySix.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictorySix.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictorySix.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictorySix.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(5,!list.get(position).getTypeSelect().get(5));
                    showListNum();
//                    showItemView(holder);
//                    list.get(position).setType("2");
//                    holder.tvVictorySix.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                    holder.tvVictorySix.setTextColor(Color.rgb(255,255,255));
                }
            });
            holder.tvVictorySeven.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBoolList(true,holder);
                    if (list.get(position).getTypeSelect().get(6)){
                        holder.tvVictorySeven.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictorySeven.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictorySeven.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictorySeven.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(6,!list.get(position).getTypeSelect().get(6));
                    showListNum();
//                    showItemView(holder);
//                    list.get(position).setType("3");
//                    holder.tvVictorySeven.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                    holder.tvVictorySeven.setTextColor(Color.rgb(255,255,255));
                }
            });
            holder.tvVictoryEight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBoolList(true,holder);
                    if (list.get(position).getTypeSelect().get(7)){
                        holder.tvVictoryEight.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictoryEight.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictoryEight.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictoryEight.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(7,!list.get(position).getTypeSelect().get(7));
                    showListNum();
//                    showItemView(holder);
//                    list.get(position).setType("4");
//                    holder.tvVictoryEight.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                    holder.tvVictoryEight.setTextColor(Color.rgb(255,255,255));
                }
            });
            holder.tvVictoryNine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBoolList(true,holder);
                    if (list.get(position).getTypeSelect().get(8)){
                        holder.tvVictoryNine.setBackgroundResource(R.drawable.shape_bjk_001);
                        holder.tvVictoryNine.setTextColor(Color.rgb(122,122,122));
                    }else {
                        holder.tvVictoryNine.setBackgroundResource(R.drawable.recharge_shape_bgs);
                        holder.tvVictoryNine.setTextColor(Color.rgb(255,255,255));
                    }
                    list.get(position).getTypeSelect().set(8,!list.get(position).getTypeSelect().get(8));
                    showListNum();
//                    showItemView(holder);
//                    list.get(position).setType("5");
//                    holder.tvVictoryNine.setBackgroundResource(R.drawable.recharge_shape_bgs);
//                    holder.tvVictoryNine.setTextColor(Color.rgb(255,255,255));
                }
            });

        }

    }

    private void showBoolList(boolean b,final VictoryHolderView holder){
        if (b) {
            for (int i = 0; i < 3; i++) {
                list.get(14).getTypeSelect().set(i, false);
                holder.tvVictoryOne.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictoryOne.setTextColor(Color.rgb(122,122,122));
                holder.tvVictoryTwo.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictoryTwo.setTextColor(Color.rgb(122,122,122));
                holder.tvVictoryThree.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictoryThree.setTextColor(Color.rgb(122,122,122));
            }
        }else {
            for (int i = 3; i < 9; i++) {
                list.get(14).getTypeSelect().set(i, false);
                holder.tvVictoryFour.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictoryFour.setTextColor(Color.rgb(122,122,122));
                holder.tvVictoryFive.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictoryFive.setTextColor(Color.rgb(122,122,122));
                holder.tvVictorySix.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictorySix.setTextColor(Color.rgb(122,122,122));
                holder.tvVictorySeven.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictorySeven.setTextColor(Color.rgb(122,122,122));
                holder.tvVictoryEight.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictoryEight.setTextColor(Color.rgb(122,122,122));
                holder.tvVictoryNine.setBackgroundResource(R.drawable.shape_bjk_001);
                holder.tvVictoryNine.setTextColor(Color.rgb(122,122,122));
            }
        }
    }

    private void showListNum(){
        int num = 0;
        boolean b = true;
        for (int i = 0; i < list.size(); i++) {
            boolean s = false;
            for (int j = 0; j < list.get(i).getTypeSelect().size(); j++) {
                if (list.get(i).getTypeSelect().get(j)){
                    s = true;
                    num++;
                }
            }
            if (!s){
                b = false;
            }
        }
        if (b){
            victoryDefeatActivity.showText(num);
        }else {
            victoryDefeatActivity.showText(0);
        }
    }
    public void showPosView(final VictoryHolderView holder,String playType,List<Boolean> select){
        if (playType.equals("0")) {
            for (int i = 0; i < 3; i++) {
                switch (i) {
                    case 0:
//                    showItemView(holder);
                        if (select.get(0)){
                            holder.tvVictoryOne.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictoryOne.setTextColor(Color.rgb(255,255,255));
                        }else {
                            holder.tvVictoryOne.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictoryOne.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                    case 1:
//                    showItemView(holder);
                        if (select.get(1)) {
                            holder.tvVictoryTwo.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictoryTwo.setTextColor(Color.rgb(255, 255, 255));
                        }else {
                            holder.tvVictoryTwo.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictoryTwo.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                    case 2:
//                    showItemView(holder);
                        if (select.get(2)) {
                            holder.tvVictoryThree.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictoryThree.setTextColor(Color.rgb(255, 255, 255));
                        }else {
                            holder.tvVictoryThree.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictoryThree.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                }
            }
        }else {
            for (int i = 3; i < 9; i++) {
                switch (i) {
                    case 3:
                        if (select.get(3)) {
                            holder.tvVictoryFour.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictoryFour.setTextColor(Color.rgb(255, 255, 255));
                        }else {
                            holder.tvVictoryFour.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictoryFour.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                    case 4:
                        if (select.get(4)) {
                            holder.tvVictoryFive.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictoryFive.setTextColor(Color.rgb(255, 255, 255));
                        }else {
                            holder.tvVictoryFive.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictoryFive.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                    case 5:
                        if (select.get(5)) {
                            holder.tvVictorySix.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictorySix.setTextColor(Color.rgb(255, 255, 255));
                        }else {
                            holder.tvVictorySix.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictorySix.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                    case 6:
                        if (select.get(6)) {
                            holder.tvVictorySeven.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictorySeven.setTextColor(Color.rgb(255, 255, 255));
                        }else {
                            holder.tvVictorySeven.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictorySeven.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                    case 7:
                        if (select.get(7)) {
                            holder.tvVictoryEight.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictoryEight.setTextColor(Color.rgb(255, 255, 255));
                        }else {
                            holder.tvVictoryEight.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictoryEight.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                    case 8:
                        if (select.get(8)) {
                            holder.tvVictoryNine.setBackgroundResource(R.drawable.recharge_shape_bgs);
                            holder.tvVictoryNine.setTextColor(Color.rgb(255, 255, 255));
                        }else {
                            holder.tvVictoryNine.setBackgroundResource(R.drawable.shape_bjk_001);
                            holder.tvVictoryNine.setTextColor(Color.rgb(122,122,122));
                        }
                        break;
                }
            }
        }
    }
    public void showItemView(final VictoryHolderView holder){
//        holder.tvVictoryOne.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictoryTwo.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictoryThree.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictoryFour.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictoryFive.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictorySix.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictorySeven.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictoryEight.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictoryNine.setBackgroundResource(R.drawable.shape_bjk_001);
//        holder.tvVictoryOne.setTextColor(Color.rgb(122,122,122));
//        holder.tvVictoryTwo.setTextColor(Color.rgb(122,122,122));
//        holder.tvVictoryThree.setTextColor(Color.rgb(122,122,122));
//        holder.tvVictoryFour.setTextColor(Color.rgb(122,122,122));
//        holder.tvVictoryFive.setTextColor(Color.rgb(122,122,122));
//        holder.tvVictorySix.setTextColor(Color.rgb(122,122,122));
//        holder.tvVictorySeven.setTextColor(Color.rgb(122,122,122));
//        holder.tvVictoryEight.setTextColor(Color.rgb(122,122,122));
//        holder.tvVictoryNine.setTextColor(Color.rgb(122,122,122));
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

class VictoryHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_item_away_num)
    TextView itemNum;
    @BindView(R.id.tv_item_host_name)
    TextView itemHostName;
    @BindView(R.id.tv_item_away_name)
    TextView itemAwayName;
    @BindView(R.id.tv_item_time)
    TextView itemTime;
    @BindView(R.id.lly_item_one)
    LinearLayout itemLlyOne;
    @BindView(R.id.lly_item_two)
    LinearLayout itemLlyTwo;
    @BindView(R.id.tv_victory_one)
    TextView tvVictoryOne;
    @BindView(R.id.tv_victory_two)
    TextView tvVictoryTwo;
    @BindView(R.id.tv_victory_three)
    TextView tvVictoryThree;
    @BindView(R.id.tv_victory_four)
    TextView tvVictoryFour;
    @BindView(R.id.tv_victory_five)
    TextView tvVictoryFive;
    @BindView(R.id.tv_victory_six)
    TextView tvVictorySix;
    @BindView(R.id.tv_victory_seven)
    TextView tvVictorySeven;
    @BindView(R.id.tv_victory_eight)
    TextView tvVictoryEight;
    @BindView(R.id.tv_victory_nine)
    TextView tvVictoryNine;

    public VictoryHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}
