package com.jc.lottery.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.ReceivingRecordsActivity;
import com.jc.lottery.activity.immediate.ReceivingRecordsDetailActivity;
import com.jc.lottery.bean.RecordInfoBean;
import com.jc.lottery.bean.req.pos_GetCloseOrder;
import com.jc.lottery.bean.req.pos_GetLogisticsCancel;
import com.jc.lottery.content.Constant;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 清单列表adapter
 * <p>
 * Created by DavidChen on 2018/5/30.
 */

public class InventoryAdapter extends BaseRecyclerViewAdapter<RecordInfoBean> {

    private OnDeleteClickLister mDeleteClickListener;
    private ReceivingRecordsActivity shortMessageFragments;
    private Context context;

    public InventoryAdapter(ReceivingRecordsActivity context, List<RecordInfoBean> data, ReceivingRecordsActivity shortMessageFragments) {
        super(context, data, R.layout.reward_record_item);
        this.context = context;
        this.shortMessageFragments = shortMessageFragments;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, final RecordInfoBean bean, final int position) {
        View view = holder.getView(R.id.back);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (mDeleteClickListener != null) {
//                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    shortMessageFragments.showViewPop(bean.getId() + "",bean.getPayState());
//                        showAlert(context,bean.getId() + "");
//                    }
                }
            });
        }
        LinearLayout linearLayout=(LinearLayout) holder.getView(R.id.font);
        RelativeLayout relativeLayout=(RelativeLayout) holder.getView(R.id.delete_button);
        TextView tvRewardRecordItemName = (TextView) holder.getView(R.id.tv_reward_record_item_name);
        TextView tvRewardRecordItemTime = (TextView) holder.getView(R.id.tv_reward_record_item_time);
        TextView tvRewardRecordItemStatus = (TextView) holder.getView(R.id.tv_reward_record_item_status);
        TextView tvRewardRecordItemMoney = (TextView) holder.getView(R.id.tv_reward_record_item_money);
        TextView tvRewardRecordItemNumber = (TextView) holder.getView(R.id.tv_reward_record_item_number);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortMessageFragments.showViewPop(bean.getId() + "",bean.getPayState());
//                showAlert(context,bean.getId() + "");
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.putExtra("beidou_id",bean.getS());
//                intent.putExtra("boat_id",bean.getBoat_id());
//                intent.setClass(context, MessageQueryActivity.class);
                intent.putExtra("activityType", "00");
                intent.putExtra("recordDetailsId", bean.getId() + "");
                intent.setClass(context, ReceivingRecordsDetailActivity.class);
                context.startActivity(intent);
            }
        });

        tvRewardRecordItemName.setText(bean.getIndex() + ". " + bean.getGameName());
        tvRewardRecordItemTime.setText(bean.getCreateTime());
        tvRewardRecordItemMoney.setText(MoneyUtil.getIns().GetMoney(bean.getPayMoney()) + context.getString(R.string.price_unit));
        tvRewardRecordItemNumber.setText(bean.getTicketNum() + " " + context.getString(R.string.book));

        if (bean.getShutState().equals("00")){
            tvRewardRecordItemStatus.setText(context.getString(R.string.in_closing));
            tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
            view.setVisibility(View.GONE);
        }else {
            if (bean.getStatus().equals("00")||bean.getStatus().equals("05")){
                view.setVisibility(View.GONE);
            }
            if (bean.getPayState().equals("02")) {
                if (bean.getStatus().equals("02")) {
                    tvRewardRecordItemStatus.setText(context.getString(R.string.to_be_paid));
                    tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
                } else if (bean.getStatus().equals("04")) {
                    tvRewardRecordItemStatus.setText(context.getString(R.string.cancellation_of_order));
                    tvRewardRecordItemStatus.setTextColor(Color.rgb(153, 153, 153));
                } else {
                    tvRewardRecordItemStatus.setText(context.getString(R.string.order_invalidation));
                    tvRewardRecordItemStatus.setTextColor(Color.rgb(153, 153, 153));
                }
            } else if (bean.getPayState().equals("01")) {
                tvRewardRecordItemStatus.setText(context.getString(R.string.failure_to_pay));
                tvRewardRecordItemStatus.setTextColor(Color.rgb(253, 8, 8));
            } else if (bean.getPayState().equals("03")) {
                tvRewardRecordItemStatus.setText(context.getString(R.string.to_be_confirmed));
                tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
            } else {
                if (bean.getStatus().equals("00")) {
                    tvRewardRecordItemStatus.setText(context.getString(R.string.has_been_received));
                    tvRewardRecordItemStatus.setTextColor(Color.rgb(0,75,255));
                } else if (bean.getStatus().equals("01")) {
                    tvRewardRecordItemStatus.setText(context.getString(R.string.chupiao_fail));
                    tvRewardRecordItemStatus.setTextColor(Color.rgb(253, 8, 8));
                } else if (bean.getStatus().equals("02")) {
//                holder.tvRewardRecordItemStatus.setText(context.getString(R.string.to_be_dispatched));
                    tvRewardRecordItemStatus.setText(context.getString(R.string.unclaimed));
                    tvRewardRecordItemStatus.setTextColor(Color.rgb(255, 102, 0));
                } else if (bean.getStatus().equals("03")) {
                    tvRewardRecordItemStatus.setText(context.getString(R.string.order_invalidation));
                    tvRewardRecordItemStatus.setTextColor(Color.rgb(153, 153, 153));
                } else if (bean.getStatus().equals("04")) {
                    tvRewardRecordItemStatus.setText(context.getString(R.string.cancellation_of_order));
                    tvRewardRecordItemStatus.setTextColor(Color.rgb(153, 153, 153));
                } else {
                    tvRewardRecordItemStatus.setText(context.getString(R.string.in_delivery));
                    tvRewardRecordItemStatus.setTextColor(Constant.in_delivery);
                }
            }
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }

    private void showAlert(final Context mContext, final String orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.tips));
        builder.setMessage(mContext.getString(R.string.close_order_not));
        builder.setIcon(R.drawable.logo);
        builder.setPositiveButton(mContext.getString(R.string.str_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getOrderCancelHttp(mContext,orderId);
                    }
                }
        );
        builder.setNeutralButton(mContext.getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        builder.show();
    }

    private void getOrderCancelHttp(final Context context, String orderId) {
        ProgressUtil.showProgressDialog(context, context.getString(R.string.waitting));
        String account_name = SPUtils.look(context, SPkey.username);
        String account_password = SPUtils.look(context, SPkey.password);
        pos_GetCloseOrder.OrderInfo orderInfo = new pos_GetCloseOrder.OrderInfo(orderId);
        pos_GetCloseOrder.DataBean dataBean = new pos_GetCloseOrder.DataBean(orderInfo);
        pos_GetCloseOrder pos_getLogisticsCancel = new pos_GetCloseOrder(account_name, account_password, "3", dataBean);
        String json = new Gson().toJson(pos_getLogisticsCancel);
        OkGo.<String>post(MyUrl.pos_GetCloseOrder)
                .upJson(json)
                .execute(new vStringCallback(context) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject json = new JSONObject(response.body());
                            ToastUtils.showShort(json.getString("message"));
                            if (json.getString("code").equals("00000")) {
                                shortMessageFragments.onRefresh();
//                                ReceivingRecordsActivity.ins.onRefresh();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }
//    private void getDeleteChatHttp(final Context context, String boat_id, String beidou_id, final int pos) {
//        String token = SPUtils.look(context, SPkey.token);
//        OkGo.<String>post(MyUrl.GetDeleteChat + "?token=" + token + "&boat_id=" + boat_id + "&beidou_id=" + beidou_id)
//                .upString("")
//                .execute(new StringCallback() {
//
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response.body());
////                            TokenUtil.ins.getIns().getTokenUtil(context,jsonObject.getString("state"));
//                            String state = jsonObject.getString("state");
//                            if (state.equals("1")) {
////                                list.remove(pos);
////                                notifyDataSetChanged();
////                                shortMessageFragments.showList();
//                                Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
//                            } else {
//                                Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        response.body();
//                    }
//                });
//    }
}
