package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryAdminDetailsActivity;
import com.jc.lottery.activity.immediate.DeliveryInputActivity;
import com.jc.lottery.bean.resp.DeliveryBookBean;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lr
 * @description:
 * @date:${DATA} 11:03
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private DeliveryAdminDetailsActivity mContext;
    private LayoutInflater mInflater;
    private static final int VIEW_TYPE_TITLE= 0;
    private static final int VIEW_TYPE_ITEM = 1;
    int IS_TITLE_OR_NOT =1;
    int MESSAGE = 2;
    int ColumnNum;
    List<DeliveryBookBean> mData;


    public RecyclerViewAdapter(DeliveryAdminDetailsActivity context , List<DeliveryBookBean> mData , int ColumnNum) {
        this.mContext=context;
        this.ColumnNum=ColumnNum;
        this.mData= mData;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder  vh = null;
        mInflater = LayoutInflater.from(mContext);
        //判断viewtype类型返回不同Viewholder
        switch (viewType) {
            case VIEW_TYPE_TITLE:
                vh = new HolderOne(mInflater.inflate(R.layout.delivery_input_title, parent, false));
                break;
            case VIEW_TYPE_ITEM:
                vh = new HolderTwo(mInflater.inflate(R.layout.delivery_input_item, parent,false));
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if("true".equals(mData.get(position).getTitleNot())){
            holder.mTitle.setText(mContext.getString(R.string.batch_number) + " " +mData.get(position).getBookNum());
        }else {
            String[] book = mData.get(position).getBookNum().split("-");
            if (book.length > 1){
                holder.tvDeliveryInputItemTwo.setText(mContext.getString(R.string.book_number) + " " + book[0]  + "-" + book[1]);
                holder.tvDeliveryInputItemThree.setText(mContext.getString(R.string.end_book_numbers) + "\n" + book[1]);
            }else {
                holder.tvDeliveryInputItemTwo.setText(mContext.getString(R.string.book_number) + " " + book[0]  + "-" + book[0]);
//                holder.tvDeliveryInputItemTwo.setText(mContext.getString(R.string.start_book_numbers) + "\n" + book[0]);
                holder.tvDeliveryInputItemThree.setText(mContext.getString(R.string.end_book_numbers) + "\n" + book[0]);
            }
            holder.tvDeliveryInputItemName.setText(mContext.getString(R.string.case_number) + " " + mData.get(position).getCartonNo());
            holder.tvDeliveryInputItemNum.setText("No." + (position) + "");

            holder.imgDeliveryInputItemDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.bookDel(position);
                }
            });
            holder.imgRly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mData.get(position).getCartonType().equals("")){
//                        mContext.showPopBook(mData.get(position).getCartonNo());
                    }
                }
            });
            if (mData.get(position).getCartonType().equals("")){
                holder.imgRly.setBackgroundResource(R.drawable.delivery_admin_bgs);
                holder.imgLlyTop.setBackgroundResource(R.drawable.delivery_admin_top_bgs);
//                holder.tvDeliveryInputItemName.setTextColor(Color.rgb(0,146,61));
//                holder.tvDeliveryInputItemTwo.setTextColor(Color.rgb(0,146,61));
//                holder.tvDeliveryInputItemThree.setTextColor(Color.rgb(0,146,61));
            }else {
                holder.imgRly.setBackgroundResource(R.drawable.delivery_admin_bg);
                holder.imgLlyTop.setBackgroundResource(R.drawable.delivery_admin_top_bg);
//                holder.tvDeliveryInputItemName.setTextColor(Color.rgb(255,255,255));
//                holder.tvDeliveryInputItemTwo.setTextColor(Color.rgb(255,255,255));
//                holder.tvDeliveryInputItemThree.setTextColor(Color.rgb(255,255,255));
            }
        }

    }

    //判断RecyclerView的子项样式，返回一个int值表示
    @Override
    public int getItemViewType(int position) {
        if ("true".equals(mData.get(position).getTitleNot())) {
            return VIEW_TYPE_TITLE;
        }
        return VIEW_TYPE_ITEM;
    }

    //判断是否是title，如果是，title占满一行的所有子项，则是ColumnNum个，如果是item，占满一个子项
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据2个单元格(重点)
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(!"true".equals(mData.get(position).getTitleNot())){
                    return 1;
                }else {
                    return ColumnNum;
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position , List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        } else {
            onBindViewHolder(holder,position);
        }
    }

    //对于不同布局的子项，需要对它进行初始化
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        TextView tvDeliveryInputItemNum;
        TextView tvDeliveryInputItemName;
        TextView tvDeliveryInputItemTwo;
        TextView tvDeliveryInputItemThree;
        ImageView imgDeliveryInputItemDel;
        PercentLinearLayout imgRly;
        LinearLayout imgLlyTop;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class HolderOne extends ViewHolder {

        public HolderOne(View viewHolder) {
            super(viewHolder);
            mTitle= (TextView) viewHolder.findViewById(R.id.title);
        }
    }

    public class HolderTwo extends ViewHolder{

        public HolderTwo(final View viewHolder) {
            super(viewHolder);
            tvDeliveryInputItemNum= (TextView) viewHolder.findViewById(R.id.tv_delivery_input_item_num);
            tvDeliveryInputItemName= (TextView) viewHolder.findViewById(R.id.tv_delivery_input_item_name);
            tvDeliveryInputItemTwo= (TextView) viewHolder.findViewById(R.id.tv_delivery_input_item_two);
            tvDeliveryInputItemThree= (TextView) viewHolder.findViewById(R.id.tv_delivery_input_item_three);
            imgDeliveryInputItemDel= (ImageView) viewHolder.findViewById(R.id.img_delivery_input_item_del);
            imgRly= (PercentLinearLayout) viewHolder.findViewById(R.id.lly_delivery_item);
            imgLlyTop= (LinearLayout) viewHolder.findViewById(R.id.lly_delivery_item_top);

        }
    }
}
