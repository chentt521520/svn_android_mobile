package com.jc.lottery.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.BluetoothNewActivity;
import com.jc.lottery.activity.LoginActivity;
import com.jc.lottery.activity.my.AboutCaisoActivity;
import com.jc.lottery.activity.my.MyImmediateActivity;
import com.jc.lottery.activity.my.MyLottoryActivity;
import com.jc.lottery.activity.my.MyVictoryActivity;
import com.jc.lottery.activity.my.UpdateLockPasswordActivity;
import com.jc.lottery.activity.my.UpdatePasswordActivity;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.content.Constant;
import com.jc.lottery.content.PartnerConfig;
import com.jc.lottery.util.DeviceConnFactoryManager;
import com.jc.lottery.util.GetPermissionsUtil;
import com.jc.lottery.util.PrintDeviceUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class PersonalCenterFragment extends BaseFragment {


    @BindView(R.id.lly_personal_center_password)
    PercentLinearLayout llyPersonalCenterPassword;
    @BindView(R.id.lly_personal_center_phone)
    PercentLinearLayout llyPersonalCenterPhone;
    @BindView(R.id.lly_personal_center_we)
    PercentLinearLayout llyPersonalCenterWe;
    @BindView(R.id.lly_personal_center_back)
    PercentLinearLayout llyPersonalCenterBack;
    @BindView(R.id.lly_personal_center_immediate)
    PercentLinearLayout llyPersonalCenterImmediate;
    @BindView(R.id.lly_personal_center_lottory)
    PercentLinearLayout llyPersonalCenterLottory;
    @BindView(R.id.tv_personal_center_name)
    TextView tvPersonalCenterName;
    @BindView(R.id.tv_personal_center_time)
    TextView tvPersonalCenterTime;
    @BindView(R.id.lly_personal_center_lock_password)
    PercentLinearLayout llyPersonalCenterLockPassword;
    @BindView(R.id.account_center_setting)
    PercentLinearLayout accountCenterSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    protected void initView(View view) {
//        rl_guide = (RelativeLayout) view.findViewById(R.id.rl_guide);
//        rl_guide.setVisibility(View.GONE);
        showInfoView();
    }

    @Override
    public void initData() {
//        GetGameList();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }

    @OnClick({R.id.lly_personal_center_password, R.id.lly_personal_center_phone, R.id.lly_personal_center_we, R.id.lly_personal_center_back, R.id.lly_personal_center_immediate, R.id.lly_personal_center_lottory, R.id.lly_personal_center_lock_password,R.id.account_center_setting,R.id.lly_personal_center_victory})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
        switch (view.getId()) {
            case R.id.lly_personal_center_password:
                intent.setClass(getActivity(), UpdatePasswordActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                break;
            case R.id.lly_personal_center_lock_password:
                intent.setClass(getActivity(), UpdateLockPasswordActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                break;
            case R.id.lly_personal_center_phone:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PartnerConfig.PHONE));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
                getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                break;
            case R.id.lly_personal_center_we:
                intent.setClass(getActivity(), AboutCaisoActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                break;
            case R.id.lly_personal_center_back:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                break;
            case R.id.lly_personal_center_immediate:
                intent.setClass(getActivity(), MyImmediateActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                break;
            case R.id.lly_personal_center_lottory:
                if (getPermissionsUtil.getPermissionsLt("lt")) {
                    intent.setClass(getActivity(), MyLottoryActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                } else {
                    ToastUtils.showShort(getString(R.string.no_operation_permission));
                }
                break;
            case R.id.lly_personal_center_victory:
                if (getPermissionsUtil.getPermissionsLt("lt")) {
                    intent.setClass(getActivity(), MyVictoryActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                } else {
                    ToastUtils.showShort(getString(R.string.no_operation_permission));
                }
                break;
            case R.id.account_center_setting:
                startActivityForResult(new Intent(getActivity(), BluetoothNewActivity.class), Constant.BLUETOOTH_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.BLUETOOTH_REQUEST_CODE: {
                    /*获取蓝牙mac地址*/
                    ProgressUtil.showProgressDialog(getActivity(),getString(R.string.waitting));
                    String macAddress = data.getStringExtra(Constant.EXTRA_DEVICE_ADDRESS);
                    coo(macAddress,0);
//                    ToastUtils.showShort(getString(R.string.connectedscusses));
//                    PrintDeviceUtil.cancelDialog();
                    break;
                }
            }
        }
    }

    private void coo(final String macAddress,final int sId) {
         /*获取蓝牙mac地址*/
        final DeviceConnFactoryManager deviceConnFactoryManager = new DeviceConnFactoryManager.Build()
                .setId(sId)
                //设置连接方式
                .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
                //设置连接的蓝牙mac地址
                .setMacAddress(macAddress)
                .build();
        new Thread() {
            @Override
            public void run() {
//                ProgressUtil.showProgressDialog("");
                //初始化话DeviceConnFactoryManager
                //打开端口
//                DeviceConnFactoryManager.getDeviceConnFactoryManagers()[sId].openPort();
                deviceConnFactoryManager.getDeviceConnFactoryManagers()[sId].openPort();
                PrintDeviceUtil.cancelDialog();
                ProgressUtil.dismissProgressDialog();
                Looper.prepare();
                ToastUtils.showShort(getString(R.string.connectedscusses));
                Looper.loop();
            }
        }.start();
    }

    private void showInfoView() {
        String roleAlias = SPUtils.look(getContext(), SPkey.roleAlias);
        String name = SPUtils.look(getContext(), SPkey.username);
        String time = SPUtils.look(getContext(), SPkey.registrationTime);
        if (time != null && !time.equals("") && !time.equals("null")) {
            time = timeStamp2Date(Long.parseLong(SPUtils.look(getContext(), SPkey.registrationTime)));
        }
        if (roleAlias.equals("gly")) {
            roleAlias = getString(R.string.administrators);
        } else if (roleAlias.equals("dls")) {
            roleAlias = getString(R.string.agents);
        } else if (roleAlias.equals("fxs")) {
            roleAlias = getString(R.string.distributor);
        } else if (roleAlias.equals("cjgly")) {
            roleAlias = getString(R.string.super_administrator);
        }
//        headerTypeOneTitle.setText(roleAlias + "");
        GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
        if (getPermissionsUtil.getPermissions("yddxtgnxgmm")) {
            llyPersonalCenterPassword.setVisibility(View.VISIBLE);
        }
        tvPersonalCenterName.setText(roleAlias + ": " + name);
        tvPersonalCenterTime.setText(getString(R.string.registration_time) + ": " + time);
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(getActivity(), SPkey.Language);
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
