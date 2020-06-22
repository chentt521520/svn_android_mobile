package com.jc.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.live.PLVideoViewActivity;
import com.jc.lottery.activity.live.PLVideoViewsActivity;
import com.jc.lottery.activity.live.VideoListActivity;
import com.jc.lottery.bean.DrawListInfo;
import com.jc.lottery.bean.LiveBean;
import com.jc.lottery.fragment.LiveBroadcastFragment;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/23.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListHolderView> {

    private List<DrawListInfo> list;
    private LayoutInflater mInflater;
    private Context mContext = null;
    private VideoListActivity liveBroadcastFragment = null;

    public VideoListAdapter(Context context, VideoListActivity liveBroadcastFragment) {
        this.mContext = context;
        this.liveBroadcastFragment = liveBroadcastFragment;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<DrawListInfo> list) {
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
    public VideoListHolderView onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.video_list_item, parent, false);
        VideoListHolderView holder = new VideoListHolderView(view);
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
    public void onBindViewHolder(final VideoListHolderView holder, final int position) {
        String bean = list.get(position).getGameName();
        if (bean != null) {
            holder.itemName.setText(list.get(position).getGameName() + " " + list.get(position).getDrawNumber());
            holder.itemVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, PLVideoViewsActivity.class);
                    intent.putExtra("url", MyUrl.Video_Host + list.get(position).getUrl());
//                    intent.putExtra("url", "http://jicaikeji.qicp.vip:8810/srv/lottery/video/90x52020109.mp4");
                    intent.putExtra("game", list.get(position).getDrawNumber() + " " + list.get(position).getGameAlias());
                    mContext.startActivity(intent);
                }
            });
            if (list.get(position).getGameAlias().equals("90x5")) {
                String[] num = list.get(position).getPrizeNum().split(" ");
                for (int i = 0; i < num.length; i++) {
                    LayoutInflater inflater = liveBroadcastFragment.getLayoutInflater();
                    View view = inflater.inflate(R.layout.cash_prize_item_text, null);
                    TextView tv = view.findViewById(R.id.sna_number);
                    tv.setText(num[i]);
                    holder.itemLly.addView(view);
//                tvCashPrizeOneTime.setText(drawListBean.get);
//                tvCashPrizeOneTime.setText(drawListBean.get());
                }
            } else {
                String[] num = list.get(position).getPrizeNum().split(" ");
                List<String> numList = showList(num);
                for (int i = 0; i < numList.size(); i++) {
                    LayoutInflater inflater = liveBroadcastFragment.getLayoutInflater();
                    View view = inflater.inflate(R.layout.cash_prize_item_text, null);
                    TextView tv = view.findViewById(R.id.sna_number);
                    if (i == numList.size() - 1){
                        tv.setBackgroundResource(R.drawable.red_balls);
                    }
                    tv.setText(numList.get(i));
                    holder.itemLly.addView(view);
                }
            }
        }
    }

    private List<String> showList(String[] s){
        List<String> list = new ArrayList<String>();
        String[] endString = s[s.length - 1].split("-");
        if (endString.length > 1){
            for (int i = 0; i < s.length - 1; i++) {
                list.add(s[i]);
            }
            for (int i = 0; i < endString.length; i++) {
                list.add(endString[i]);
            }
        }else {
            for (int i = 0; i < s.length; i++) {
                list.add(s[i]);
            }
//            list = s.
        }
        return list;
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

class VideoListHolderView extends RecyclerView.ViewHolder {

    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.lly_video)
    LinearLayout itemVideo;
    @BindView(R.id.item_lly)
    LinearLayout itemLly;


    public VideoListHolderView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}