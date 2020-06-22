package com.jc.lottery.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.WinEachListBean;
import com.jc.lottery.bean.resp.BettingDetailInfo;
import com.jc.lottery.bean.resp.GetCashPrizeBean;
import com.jc.lottery.util.MoneyUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class LottoScannerDetailAdapter extends RecyclerView.Adapter<LottoScannerDetailHolderView> {

    private List<WinEachListBean> list;
    private LayoutInflater mInflater;
    private Activity mContext = null;
    private ActivationTwoAdapter activationTwoAdapter;

    public LottoScannerDetailAdapter(Activity context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<WinEachListBean> list) {
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
    public LottoScannerDetailHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.lotto_scanner_detail_item, parent, false);
        LottoScannerDetailHolderView holder = new LottoScannerDetailHolderView(view);
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
    public void onBindViewHolder(final LottoScannerDetailHolderView holder, final int position) {
//        holder.setIsRecyclable(false);
        String bean = list.get(position).getWinNum();
        if (bean != null) {
            holder.tvItemNo.setText((position + 1) + "");
            holder.tvItemThree.setText(list.get(position).getDrawNumber());
            holder.tvItemOne.setText(list.get(position).getWinNum());
            holder.tvItemTwo.setText(MoneyUtil.getIns().GetMoney(list.get(position).getWinMoney()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class LottoScannerDetailHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_scanner_item_no)
    TextView tvItemNo;
    @BindView(R.id.tv_scanner_item_three)
    TextView tvItemThree;
    @BindView(R.id.tv_scanner_item_one)
    TextView tvItemOne;
    @BindView(R.id.tv_scanner_item_two)
    TextView tvItemTwo;

    public LottoScannerDetailHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}