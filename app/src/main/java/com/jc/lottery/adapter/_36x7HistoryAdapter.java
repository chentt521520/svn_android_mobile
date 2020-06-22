package com.jc.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.resp.Resp_36x7_history;

import java.util.List;

/**
 * @classDescription： 36选7  开奖历史
 * @author： vchao
 * @createTime： 2018/10/08 on 16:01.
 */
public class _36x7HistoryAdapter extends BaseAdapter {
    private Context context;
    private List<Resp_36x7_history.DrawListBean> datas;
    private LayoutInflater inflater;

    public _36x7HistoryAdapter(Context context, List<Resp_36x7_history.DrawListBean> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Resp_36x7_history.DrawListBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_k3_history, null);
            holder.lin = (LinearLayout) convertView.findViewById(R.id.adapter_ssq_jqkj_lin);
            holder.qihao = (TextView) convertView.findViewById(R.id.adapter_ssq_jqkj_tv_qihao);
            holder.num = (TextView) convertView.findViewById(R.id.adapter_ssq_jqkj_tv_num);
            holder.daxiao = (TextView) convertView.findViewById(R.id.adapter_ssq_jqkj_tv_daxiao);
            holder.jiou = (TextView) convertView.findViewById(R.id.adapter_ssq_jqkj_tv_jiou);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Resp_36x7_history.DrawListBean bean = getItem(position);
        int n = position % 2;
        if(n == 0) {
            holder.lin.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.lin.setBackgroundColor(Color.parseColor("#f1f2ed"));
        }
        holder.qihao.setText(String.format(context.getString(R.string.qici_no), bean.getDraw()));
        holder.jiou.setVisibility(View.GONE);

        String nums = bean.getPrizeNum();
        String[] number = nums.split("-");
        String str = "";
        if (number.length > 1) {
            str = "<font color='#00923d'>" + number[0] + "</font>" + " " + "<font color='#ff0000'>" + number[1] + "</font>";
        }else {
            str = "<font color='#00923d'>" + nums + "</font>";
        }
        holder.num.setText(Html.fromHtml(str));
        return convertView;
    }

    class ViewHolder {
        private LinearLayout lin;
        private TextView qihao;
        private TextView num;
        private TextView daxiao;
        private TextView jiou;
    }
}
