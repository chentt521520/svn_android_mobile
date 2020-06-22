/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.jc.lottery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;


public class XListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;
	
	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}
	
	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	
	public void setState(int state) {
//		mHintView.setVisibility(View.INVISIBLE);
		mHintView.setText(mContext.getString(R.string.xlistview_header_hint_loading));
		mProgressBar.setVisibility(View.INVISIBLE);
//		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			mHintView.setText(mContext.getString(R.string.xlistview_footer_hint_normal));
//			mHintView.setVisibility(View.VISIBLE);
//			mHintView.setText(R.string.xlistview_footer_hint_ready);
		} else if (state == STATE_LOADING) {
			mHintView.setText(mContext.getString(R.string.xlistview_header_hint_loading));
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			mHintView.setText(mContext.getString(R.string.xlistview_footer_hint_normal));
//			mHintView.setVisibility(View.VISIBLE);
//			mHintView.setText(R.string.xlistview_footer_hint_normal);
		}
	}
	
	public void setBottomMargin(int height) {
		if (height < 0) return ;
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}


	/**
	 * normal status
	 */
	public void normal() {
//		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText("查看更多");
		mProgressBar.setVisibility(View.GONE);
	}


	/**
	 * loading status
	 */
	public void loading() {
//		mHintView.setVisibility(View.GONE);
		mHintView.setText("加载中...");
		mProgressBar.setVisibility(View.VISIBLE);
	}

	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView)moreView.findViewById(R.id.xlistview_footer_hint_textview);
	}
	
	
}