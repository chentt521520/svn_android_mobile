package com.jc.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryRecordsActivity;
import com.jc.lottery.activity.immediate.ImmediatelActivationActivity;
import com.jc.lottery.activity.immediate.ImmediatelSettlementActivity;
import com.jc.lottery.activity.immediate.LotteryPurchaseActivity;
import com.jc.lottery.activity.immediate.PaymentRecordActivity;
import com.jc.lottery.activity.immediate.ReceivingRecordsActivity;
import com.jc.lottery.activity.immediate.StatisticsAmountActivity;
import com.jc.lottery.activity.lottery._37x6SelectNumActivity;
import com.jc.lottery.activity.lottery._49x6SelectNumActivity;
import com.jc.lottery.activity.lottery.s90x5Activity;
import com.jc.lottery.activity.my.MyImmediateActivity;
import com.jc.lottery.activity.scanner.ManualScannerActivity;
import com.jc.lottery.activity.victory.VictoryDefeatActivity;
import com.jc.lottery.bean.MyImmediateBean;
import com.jc.lottery.bean.req.pos_GetDrawNotOpenQuery;
import com.jc.lottery.bean.resp.Resp_3_7_1_drawNotOpenQuery;
import com.jc.lottery.fragment.MyLotteryFragment;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeManager;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.rebound.LeCrash;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class MyLotteryAdapter extends RecyclerView.Adapter<MyLotteryAdapter.MyLotteryHolderView> {

    private List<MyImmediateBean> list;
    private LayoutInflater mInflater;
    private MyLotteryFragment fragment;
    private Context mContext = null;

    public MyLotteryAdapter(Context context,MyLotteryFragment fragment) {
        this.mContext = context;
        this.fragment = fragment;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<MyImmediateBean> list) {
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
    public MyLotteryHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_lottery_item, parent, false);
        MyLotteryHolderView holder = new MyLotteryHolderView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyLotteryHolderView holder, final int position) {
        String bean = list.get(position).getName();
        if (bean != null) {
            holder.tvMyImmediateItemName.setText(bean);
        }
        if (list.get(position).isState()){
            holder.llyMyLotteryItemTwo.setVisibility(View.VISIBLE);
        }else {
            holder.llyMyLotteryItemTwo.setVisibility(View.GONE);
        }
//        holder.llyMyLotteryItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showIntent(position);
//            }
//        });
        holder.llyMyLotteryItem.setBackgroundResource(list.get(position).getIcon());

        holder.llyMyLotteryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isState()){
                    fragment.showPopView(position,1,false,"","");
                }else {
                    showIntent(position);
                }
            }
        });
        holder.llyMyLotteryItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (list.get(position).isState()){
                    fragment.showLockView(position,false,list.get(position).getAlias(),mContext.getString(R.string.unlock_or_not),"");
                }else {
                    fragment.showLockView(position,true,list.get(position).getAlias(),mContext.getString(R.string.lock_or_not),"");
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showIntent(int pos){
        Intent intent = new Intent();
        switch (list.get(pos).getId()){
            case 1:
                intent.setClass(mContext, LotteryPurchaseActivity.class);
                mContext.startActivity(intent);
                break;
            case 2:
                intent.setClass(mContext, ImmediatelActivationActivity.class);
                mContext.startActivity(intent);
                break;
            case 3:
                intent.setClass(mContext, ImmediatelSettlementActivity.class);
                mContext.startActivity(intent);
                break;
            case 4:
                jump37x6("37x6");
                break;
            case 5:
                jump90x5("90x5");
                break;
            case 6:
                intent.setClass(mContext, VictoryDefeatActivity.class);
                mContext.startActivity(intent);
//                intent.setClass(mContext, LeCrash.class);
//                mContext.startActivity(intent);
                break;
            case 7:
                jump49x6("49x6");
//                jump90x5("90x5");
                break;
        }
    }

    class MyLotteryHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.lly_my_lottery_item)
        LinearLayout llyMyLotteryItem;
        @BindView(R.id.lly_my_lottery_item_two)
        LinearLayout llyMyLotteryItemTwo;
        @BindView(R.id.tv_my_lottery_item)
        TextView tvMyImmediateItemName;

        public MyLotteryHolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }

    private void jump90x5(String gameAlias) {
        ProgressUtil.showProgressDialog(mContext, mContext.getString(R.string.get_jiangqi_info));
        String account_name = SPUtils.look(mContext, SPkey.username);
        pos_GetDrawNotOpenQuery.DrawListInfo drawListInfo = new pos_GetDrawNotOpenQuery.DrawListInfo(gameAlias);
        pos_GetDrawNotOpenQuery.DataBean dataBean = new pos_GetDrawNotOpenQuery.DataBean(drawListInfo);
        pos_GetDrawNotOpenQuery pos_getDrawNotOpenQuery = new pos_GetDrawNotOpenQuery(account_name,dataBean );
        String s = new Gson().toJson(pos_getDrawNotOpenQuery);
        LogUtils.e("  获取90选5 奖期 参数  ===" + s);
        OkGo.<String>post(MyUrl.pos_drawNotOpenQuery)
                .upJson(s)
                .execute(new vStringCallback(mContext) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e("  90选5   " + response.body());
//                        if (!response.equals("")){
//                            mContext.startActivity(new Intent(mContext, s90x5Activity.class));
//                            return;
//                        }
                        try {
                            Resp_3_7_1_drawNotOpenQuery resp_3_7_1_drawNotOpenQuery = new Gson().fromJson(response.body(), Resp_3_7_1_drawNotOpenQuery.class);
                            long start_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getStartTime();
                            if (TimeManager.getInstance().getServiceTime() < start_time) {
                                ToastUtils.showShort(mContext.getString(R.string.now_is_not_start));
                                return;
                            }
                            long end_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getEndTime();
                            if (TimeManager.getInstance().getServiceTime() < end_time) {
                                if (Config.s90x5_R001_NoteNum_max == 0 || Config.s90x5_R002_NoteMultiple_max == 0|| Config.s90x5_R003_NotePeriod_max == 0|| Config.s90x5_R005_NoteMoney_max == 0|| Config.s90x5_R007_NoteMoney_min == 0){
                                    ToastUtils.showShort(mContext.getString(R.string.rules_game));
                                    return;
                                }
                                mContext.startActivity(new Intent(mContext, s90x5Activity.class));
                            } else {
                                ToastUtils.showShort(mContext.getString(R.string.now_is_end));
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(mContext.getString(R.string.get_info_fail));
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(mContext.getString(R.string.temp_stop));
                        }
                    }
                });
    }

    private void jump37x6(String gameAlias) {
        ProgressUtil.showProgressDialog(mContext, mContext.getString(R.string.get_jiangqi_info));
        String account_name = SPUtils.look(mContext, SPkey.username);
        pos_GetDrawNotOpenQuery.DrawListInfo drawListInfo = new pos_GetDrawNotOpenQuery.DrawListInfo(gameAlias);
        pos_GetDrawNotOpenQuery.DataBean dataBean = new pos_GetDrawNotOpenQuery.DataBean(drawListInfo);
        pos_GetDrawNotOpenQuery pos_getDrawNotOpenQuery = new pos_GetDrawNotOpenQuery(account_name,dataBean );
        String s = new Gson().toJson(pos_getDrawNotOpenQuery);
        LogUtils.e("  获取37选6 奖期 参数  ===" + s);
        OkGo.<String>post(MyUrl.pos_drawNotOpenQuery)
                .upJson(s)
                .execute(new vStringCallback(mContext) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e("  37选6   " + response.body());
//                        if (!response.equals("")){
//                            mContext.startActivity(new Intent(mContext, _37x6SelectNumActivity.class));
//                            return;
//                        }
                        try {
                            Resp_3_7_1_drawNotOpenQuery resp_3_7_1_drawNotOpenQuery = new Gson().fromJson(response.body(), Resp_3_7_1_drawNotOpenQuery.class);
                            long start_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getStartTime();
                            if (TimeManager.getInstance().getServiceTime() < start_time) {
                                ToastUtils.showShort(mContext.getString(R.string.now_is_not_start));
                                return;
                            }
                            long end_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getEndTime();
                            if (TimeManager.getInstance().getServiceTime() < end_time) {
                                if (Config.s37x6_R001_NoteNum_max == 0 || Config.s37x6_R002_NoteMultiple_max == 0|| Config.s37x6_R003_NotePeriod_max == 0|| Config.s37x6_R005_NoteMoney_max == 0|| Config.s37x6_R007_NoteMoney_min == 0){
                                    ToastUtils.showShort(mContext.getString(R.string.rules_game));
                                    return;
                                }
                                mContext.startActivity(new Intent(mContext, _37x6SelectNumActivity.class));
                            } else {
                                ToastUtils.showShort(mContext.getString(R.string.now_is_end));
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(mContext.getString(R.string.get_info_fail));
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(mContext.getString(R.string.temp_stop));
                        }
                    }
                });
    }

    private void jump49x6(String gameAlias) {
        ProgressUtil.showProgressDialog(mContext, mContext.getString(R.string.get_jiangqi_info));
        String account_name = SPUtils.look(mContext, SPkey.username);
        pos_GetDrawNotOpenQuery.DrawListInfo drawListInfo = new pos_GetDrawNotOpenQuery.DrawListInfo(gameAlias);
        pos_GetDrawNotOpenQuery.DataBean dataBean = new pos_GetDrawNotOpenQuery.DataBean(drawListInfo);
        pos_GetDrawNotOpenQuery pos_getDrawNotOpenQuery = new pos_GetDrawNotOpenQuery(account_name,dataBean );
        String s = new Gson().toJson(pos_getDrawNotOpenQuery);
        LogUtils.e("  获取49选6 奖期 参数  ===" + s);
        OkGo.<String>post(MyUrl.pos_drawNotOpenQuery)
                .upJson(s)
                .execute(new vStringCallback(mContext) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e("  49选6   " + response.body());
//                        if (!response.equals("")){
//                            mContext.startActivity(new Intent(mContext, _37x6SelectNumActivity.class));
//                            return;
//                        }
                        try {
                            Resp_3_7_1_drawNotOpenQuery resp_3_7_1_drawNotOpenQuery = new Gson().fromJson(response.body(), Resp_3_7_1_drawNotOpenQuery.class);
                            long start_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getStartTime();
                            if (TimeManager.getInstance().getServiceTime() < start_time) {
                                ToastUtils.showShort(mContext.getString(R.string.now_is_not_start));
                                return;
                            }
                            long end_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getEndTime();
                            if (TimeManager.getInstance().getServiceTime() < end_time) {
                                if (Config.s49x6_R001_NoteNum_max == 0 || Config.s49x6_R002_NoteMultiple_max == 0|| Config.s49x6_R003_NotePeriod_max == 0|| Config.s49x6_R005_NoteMoney_max == 0|| Config.s49x6_R007_NoteMoney_min == 0){
                                    ToastUtils.showShort(mContext.getString(R.string.rules_game));
                                    return;
                                }
                                mContext.startActivity(new Intent(mContext, _49x6SelectNumActivity.class));
                            } else {
                                ToastUtils.showShort(mContext.getString(R.string.now_is_end));
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(mContext.getString(R.string.get_info_fail));
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(mContext.getString(R.string.temp_stop));
                        }
                    }
                });
    }

}

