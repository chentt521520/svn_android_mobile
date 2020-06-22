package com.jc.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.WalletListBean;
import com.jc.lottery.bean.resp.GetCashPrizeBean;
import com.jc.lottery.content.Constant;
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

public class LottoScannerNumberAdapter extends RecyclerView.Adapter<LottoScannerNumberHolderView> {

    private List<GetCashPrizeBean.CashPrizeBean> list;
    private LayoutInflater mInflater;
    private Context mContext = null;

    public LottoScannerNumberAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<GetCashPrizeBean.CashPrizeBean> list) {
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
    public LottoScannerNumberHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.lorro_scanner_number_item, parent, false);
        LottoScannerNumberHolderView holder = new LottoScannerNumberHolderView(view);
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
    public void onBindViewHolder(final LottoScannerNumberHolderView holder, final int position) {
        String bean = list.get(position).getBetNum();
        if (bean != null) {
//            holder.itemName.setText(list.get(position).getChannel());
//            if (list.get(position).getChannel().equals("0")) {
//                holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.terminal));
//            } else if (list.get(position).getChannel().equals("1")) {
//                holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.third_party_app));
//            } else if (list.get(position).getChannel().equals("2")) {
//                holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.block_chain));
//            } else {
//                holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.mobile_end));
//            }
//            holder.itemNum.setText(list.get(position).getIndex() +  ". ");
//            holder.itemName.setText(mContext.getString(R.string.channel_merchants) + ":" + mContext.getString(R.string.mobile_end));
//            holder.itemTime.setText(mContext.getString(R.string.remarks) + ":" + list.get(position).getRemark());
            holder.itemNum.setText(list.get(position).getBetNum().replace("-"," "));
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

class LottoScannerNumberHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_scanner_number)
    TextView itemNum;

    public LottoScannerNumberHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}