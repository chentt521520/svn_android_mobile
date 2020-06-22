package com.jc.lottery.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.lottery.R;
import com.jc.lottery.adapter.BluetoothNewAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.BluetoothBean;
import com.jc.lottery.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/4/28.
 */

public class BluetoothNewActivity extends BaseActivity {
    @BindView(R.id.lly_bluetooth_back)
    LinearLayout llyBluetoothBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rv_bluetooth_new)
    RecyclerView rvBluetoothNew;
    @BindView(R.id.img_bluetooth_loading)
    ImageView imgBluetoothLoading;
    @BindView(R.id.img_bluetooth_search)
    ImageView imgBluetoothSearch;
    @BindView(R.id.tv_bluetooth_cancel)
    TextView tvBluetoothCancel;
    @BindView(R.id.lly_bluetooth_loading)
    LinearLayout llyBluetoothLoading;
    @BindView(R.id.lly_bluetooth_new)
    LinearLayout llyBluetoothNew;
    @BindView(R.id.lly_bluetooth_right)
    LinearLayout llyBluetoothRight;
    private BluetoothNewAdapter bluetoothNewAdapter = null;
    private BluetoothAdapter mBluetoothAdapter;
    public static final int REQUEST_ENABLE_BT = 2;
    public static final int REQUEST_ENABLE_START = 2005;
    private List<BluetoothBean> bluetoothDevicesList = new ArrayList<BluetoothBean>();
    private int bluetoothType = 0;
//    private static WeakReference<BluetoothNewActivity> sActivityRef;
//    public static void finishActivity() {
//        if ( sActivityRef != null && sActivityRef.get() != null){
//            sActivityRef.get().finish();
//        }
//    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bluetooth_new;
    }

    @Override
    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvBluetoothNew.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvBluetoothNew.setItemAnimator(new DefaultItemAnimator());
        bluetoothNewAdapter = new BluetoothNewAdapter(bluetoothDevicesList, this);
        rvBluetoothNew.setAdapter(bluetoothNewAdapter);
        startImageLoading(imgBluetoothSearch);
        startImageLoading(imgBluetoothLoading);
//        sActivityRef = new WeakReference<>(this);
    }

    @Override
    public void initListener() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initData() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        initBluetooth();
        mBluetoothAdapter.startDiscovery();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick({R.id.lly_bluetooth_back, R.id.tv_bluetooth_cancel,R.id.lly_bluetooth_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_bluetooth_back:
                finish();
                break;
            case R.id.tv_bluetooth_cancel:
                finish();
                break;
            case R.id.lly_bluetooth_right:
                startImageLoading(imgBluetoothLoading);
                bluetoothDevicesList.clear();
                initBluetooth();
                mBluetoothAdapter.startDiscovery();
                break;
        }
    }

    private final BroadcastReceiver mFindBlueToothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                stopImageLoading(imgBluetoothSearch);
                llyBluetoothLoading.setVisibility(View.GONE);
                llyBluetoothNew.setVisibility(View.VISIBLE);
                // If it's already paired, skip it, because it's been listed
                // already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    BluetoothBean bluetoothBean = new BluetoothBean();
                    if (null != device.getName() && !device.getName().equals("")) {
                        bluetoothBean.setName(device.getName() + "\n" + device.getAddress());
                    } else {
                        bluetoothBean.setName(device.getAddress());
                    }
                    bluetoothBean.setType(device.getBluetoothClass().getMajorDeviceClass() + "");
                    if (bluetoothBean.getType().equals("1536")) {
                        for (int i = 0; i < bluetoothDevicesList.size(); i++) {
                            if (bluetoothDevicesList.get(i).getName().equals(bluetoothBean.getName())) {
                                return;
                            }
                        }
                        bluetoothDevicesList.add(bluetoothBean);
                    }
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                stopImageLoading(imgBluetoothLoading);
//                setTitle(R.string.select_bluetooth_device);
//                if (mNewDevicesArrayAdapter.getCount() == 0) {
//                    String noDevices = getResources().getText(
//                            R.string.none_bluetooth_device_found).toString();
//                    mNewDevicesArrayAdapter.add(noDevices);
//                }
            }
            bluetoothNewAdapter.notifyDataSetChanged();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // bluetooth is opened
//                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//                this.registerReceiver(mFindBlueToothReceiver, filter);
//                // Register for broadcasts when discovery has finished
//                filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//                this.registerReceiver(mFindBlueToothReceiver, filter);
//                getDeviceList();
//                new Thread() {
//                    @Override
//                    public void run() {
                initBluetooth();
                mBluetoothAdapter.startDiscovery();
//                    }
//                }.start();
            } else {
                // bluetooth is not open
                Toast.makeText(this, R.string.bluetooth_is_not_enabled,
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_ENABLE_START) {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Utils.toast(this, "Bluetooth is not supported by the device");
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent,
                        REQUEST_ENABLE_BT);
            } else {
                getDeviceList();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    protected void getDeviceList() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                BluetoothBean bluetoothBean = new BluetoothBean();
                if (null != device.getName() && !device.getName().equals("")) {
                    bluetoothBean.setName(device.getName() + "\n" + device.getAddress());
                } else {
                    bluetoothBean.setName(device.getAddress());
                }
                bluetoothBean.setType(device.getBluetoothClass().getMajorDeviceClass() + "");
                if (bluetoothBean.getType().equals("1536")) {
                    bluetoothDevicesList.add(bluetoothBean);
                }
//                bluetoothDevicesList.add(pairedDevices);
            }
        }
        if (llyBluetoothLoading.getVisibility() == View.VISIBLE) {
            llyBluetoothLoading.setVisibility(View.GONE);
            llyBluetoothNew.setVisibility(View.VISIBLE);
        }
        bluetoothNewAdapter.notifyDataSetChanged();
    }

    private void startImageLoading(ImageView img) {
        img.setVisibility(View.VISIBLE);
        RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1500);//设置动画持续周期
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(false);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(0);//执行前的等待时间
        img.setAnimation(rotate);
//        img.startAnimation(rotate);
    }

    private void stopImageLoading(ImageView img) {
        img.clearAnimation();
        img.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure we're not doing discovery anymore
        if (bluetoothType != 1) {
//            ProgressUtil.dismissProgressDialog();
        }
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        if (mFindBlueToothReceiver != null) {
            unregisterReceiver(mFindBlueToothReceiver);
        }
    }

    public void showBluetooth(final String address) {
//        ProgressUtil.showProgressDialog(this, getString(R.string.logining));
        bluetoothType = 1;
//        new Thread() {
//            @Override
//            public void run() {
        Intent intent = new Intent();
        intent.putExtra("address", address);
        setResult(Activity.RESULT_OK, intent);
        finish();
//            }
//        }.start();
//                Intent intent = new Intent();
//                intent.putExtra("address", address);
////        startActivityForResult(RESULT_OK,
////                REQUEST_ENABLE_START);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
    }
}
