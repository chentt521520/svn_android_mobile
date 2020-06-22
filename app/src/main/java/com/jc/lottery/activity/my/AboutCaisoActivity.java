package com.jc.lottery.activity.my;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.util.AppUtils;


/**
 * 关于按钮
 *
 * @author office
 */
public class AboutCaisoActivity extends BaseActivity implements OnClickListener {
    private TextView about_service_agreement;
    private TextView about_user_agreement;
    private LinearLayout header_type_one_back;

    @Override
    public int getLayoutId() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.about_caiso;
    }

    @Override
    public void initView() {
        header_type_one_back = (LinearLayout) findViewById(R.id.header_type_one_back);
        ((TextView) findViewById(R.id.header_type_one_title)).setText(R.string.about_we);

        String versionName = AppUtils.getAppVersion(this);
        ((TextView) findViewById(R.id.about_caiso_version)).setText(getString(R.string.version) + versionName);

        about_service_agreement = (TextView) findViewById(R.id.about_service_agreement);
        about_user_agreement = (TextView) findViewById(R.id.about_user_agreement);

        header_type_one_back.setOnClickListener(this);
        about_service_agreement.setOnClickListener(this);
        about_user_agreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.header_type_one_back:
                finish();
                break;
            case R.id.about_service_agreement: // 服务条款
               /* intent = new Intent(this, PlayTypesActivity.class);
                intent.putExtra("type", "platform_protocol");
                intent.putExtra("title", getString(R.string.service_agreement));
                startActivity(intent);*/
                break;
//			case R.id.about_user_agreement: // 用户协议
//				intent = new Intent(this, PlayTypesActivity.class);
//				intent.putExtra("type", "user_protocol");
//				intent.putExtra("title", "用户协议");
//				startActivity(intent);
//				break;
        }
    }


}