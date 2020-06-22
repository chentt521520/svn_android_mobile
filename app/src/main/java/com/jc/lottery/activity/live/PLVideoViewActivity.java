package com.jc.lottery.activity.live;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.live.utils.Config;
import com.jc.lottery.activity.live.utils.Utils;
import com.jc.lottery.activity.live.widget.MediaController;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.LiveBean;
import com.jc.lottery.bean.req.pos_GetElectronicLiveVideo;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.AppLanguageUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnAudioFrameListener;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoFrameListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * This is a demo activity of PLVideoView
 */
public class PLVideoViewActivity extends Activity {

    private static final String TAG = PLVideoViewActivity.class.getSimpleName();

    private PLVideoView mVideoView;
    private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
    private TextView mStatInfoTextView;
    private TextView tvState;
    private MediaController mMediaController;
    private LinearLayout llyBack;
    private RelativeLayout relTop;
    private RelativeLayout relBg;
    private TextView tvTitle;

    private boolean mIsLiveStreaming;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_pl_video_view);
        initView();
    }

//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_pl_video_view;
//    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void initView() {
        String language = SPUtils.look(this, SPkey.Language);
        if (TextUtils.equals(language, "Chinese")) {
            AppLanguageUtils.changeAppLanguage(this, Locale.CHINA);
        } else {
            AppLanguageUtils.changeAppLanguage(this, Locale.ENGLISH);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        tvState = findViewById(R.id.tv_state);
        LiveBean liveBean = (LiveBean) getIntent().getSerializableExtra("liveBean");
//        getHttpLiveInfo(liveBean);
//        mIsLiveStreaming = getIntent().getIntExtra("liveStreaming", 1) == 1;
        mIsLiveStreaming = true;

        mVideoView = findViewById(R.id.VideoView);
        relBg = findViewById(R.id.rel_bg);
        relTop = findViewById(R.id.rel_live_top);
        llyBack = findViewById(R.id.lly_live_back);
        tvTitle = findViewById(R.id.tv_live_title);

        View loadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(loadingView);

        View mCoverView = findViewById(R.id.CoverView);
        mVideoView.setCoverView(mCoverView);

        mStatInfoTextView = findViewById(R.id.StatInfoTextView);

        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codec);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming ? 1 : 0);
        boolean disableLog = getIntent().getBooleanExtra("disable-log", false);
        // options.setString(AVOptions.KEY_DNS_SERVER, "127.0.0.1");
        options.setInteger(AVOptions.KEY_LOG_LEVEL, disableLog ? 5 : 0);
        boolean cache = getIntent().getBooleanExtra("cache", false);
        if (!mIsLiveStreaming && cache) {
            options.setString(AVOptions.KEY_CACHE_DIR, Config.DEFAULT_CACHE_DIR);
        }
        boolean vcallback = getIntent().getBooleanExtra("video-data-callback", false);
        if (vcallback) {
            options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
        }
        boolean acallback = getIntent().getBooleanExtra("audio-data-callback", false);
        if (acallback) {
            options.setInteger(AVOptions.KEY_AUDIO_DATA_CALLBACK, 1);
        }
        if (!mIsLiveStreaming) {
            int startPos = getIntent().getIntExtra("start-pos", 0);
            options.setInteger(AVOptions.KEY_START_POSITION, startPos * 1000);
        }
        // options.setString(AVOptions.KEY_COMP_DRM_KEY,"cWoosgRk");
        mVideoView.setAVOptions(options);

        // Set some listeners
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnVideoFrameListener(mOnVideoFrameListener);
        mVideoView.setOnAudioFrameListener(mOnAudioFrameListener);

//        mVideoView.setVideoPath(videoPath);
        mVideoView.setLooping(getIntent().getBooleanExtra("loop", false));

        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(this, !mIsLiveStreaming, mIsLiveStreaming);
        mMediaController.setOnClickSpeedAdjustListener(mOnClickSpeedAdjustListener);
        mMediaController.setOnHiddenListener(new MediaController.OnHiddenListener() {
            @Override
            public void onHidden() {
//                ToastUtils.showShort("4");
                relTop.setVisibility(View.GONE);
            }
        });
        mMediaController.setOnShownListener(new MediaController.OnShownListener() {
            @Override
            public void onShown() {
//                ToastUtils.showShort("5");
                relTop.setVisibility(View.VISIBLE);
            }
        });
        mVideoView.setMediaController(mMediaController);
        if (liveBean.getLiveStatus().equals("01")) {
//            mVideoView.setVideoPath("rtmp://pili-live-rtmp.baoxianglou.cn/bxllive/tests?sign=76d437e96f31670082eb52e97de015e2&t=5e980870");
            getHttpLiveInfo(liveBean);
        }else if (liveBean.getLiveStatus().equals("00")){
            relTop.setBackground(null);
            relBg.setBackgroundColor(Color.rgb(123, 123, 123));
            tvState.setText(getString(R.string.no_live_broadcast) + "\n" + getString(R.string.please_come_back_later));
            relTop.setVisibility(View.VISIBLE);
            mVideoView.setVisibility(View.GONE);
//            ToastUtils.showShort(getString(R.string.no_live_broadcast));
//            finish();
        }else {
            relTop.setBackground(null);
            relBg.setBackgroundColor(Color.rgb(123, 123, 123));
            tvState.setText(getString(R.string.live_broadcast_ended) + "\n" + getString(R.string.please_come_back_later));
            relTop.setVisibility(View.VISIBLE);
            mVideoView.setVisibility(View.GONE);
//            ToastUtils.showShort(getString(R.string.live_broadcast_ended));
//            finish();
        }
        llyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(liveBean.getDrawNumber() + " " + liveBean.getGameName() + getString(R.string.live_broadcast_lottery));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaController.getWindow().dismiss();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    public void onClickSwitchScreen(View v) {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoView.getDisplayAspectRatio()) {
            case PLVideoView.ASPECT_RATIO_ORIGIN:
                Utils.showToastTips(this, "Origin mode");
                break;
            case PLVideoView.ASPECT_RATIO_FIT_PARENT:
                Utils.showToastTips(this, "Fit parent !");
                break;
            case PLVideoView.ASPECT_RATIO_PAVED_PARENT:
//                Utils.showToastTips(this, "Paved parent !");
                break;
            case PLVideoView.ASPECT_RATIO_16_9:
//                Utils.showToastTips(this, "16 : 9 !");
                break;
            case PLVideoView.ASPECT_RATIO_4_3:
//                Utils.showToastTips(this, "4 : 3 !");
                break;
            default:
                break;
        }
    }

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
                    ProgressUtil.dismissProgressDialog();
//                    Utils.showToastTips(PLVideoViewActivity.this, "first video render time: " + extra + "ms");
                    Log.i(TAG, "Response: " + mVideoView.getResponseInfo());
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    Log.i(TAG, "video frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    Log.i(TAG, "audio frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.i(TAG, "Gop Time: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                case PLOnInfoListener.MEDIA_INFO_METADATA:
                    Log.i(TAG, mVideoView.getMetadata().toString());
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                    updateStatInfo();
                    break;
                case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                    Log.i(TAG, "Connected !");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    Log.i(TAG, "Rotation changed: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_LOOP_DONE:
                    Log.i(TAG, "Loop done");
                    break;
                case PLOnInfoListener.MEDIA_INFO_CACHE_DOWN:
                    Log.i(TAG, "Cache done");
                    break;
                case PLOnInfoListener.MEDIA_INFO_STATE_CHANGED_PAUSED:
                    Log.i(TAG, "State paused");
                    break;
                case PLOnInfoListener.MEDIA_INFO_STATE_CHANGED_RELEASED:
                    Log.i(TAG, "State released");
                    break;
                default:
                    break;
            }
        }
    };

    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    Log.e(TAG, "IO Error!");
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
//                    Utils.showToastTips(PLVideoViewActivity.this, "failed to open player !");
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
//                    Utils.showToastTips(PLVideoViewActivity.this, "failed to seek !");
                    return true;
                case PLOnErrorListener.ERROR_CODE_CACHE_FAILED:
//                    Utils.showToastTips(PLVideoViewActivity.this, "failed to cache url !");
                    break;
                default:
//                    Utils.showToastTips(PLVideoViewActivity.this, "unknown error !");
                    break;
            }
            finish();
            return true;
        }
    };

    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            Log.i(TAG, "Play Completed !");
//            Utils.showToastTips(PLVideoViewActivity.this, "Play Completed !");
            if (!mIsLiveStreaming) {
                mMediaController.refreshProgress();
            }
            //finish();
        }
    };

    private PLOnBufferingUpdateListener mOnBufferingUpdateListener = new PLOnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(int precent) {
            Log.i(TAG, "onBufferingUpdate: " + precent);
        }
    };

    private PLOnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLOnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(int width, int height) {
            Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
        }
    };

    private PLOnVideoFrameListener mOnVideoFrameListener = new PLOnVideoFrameListener() {
        @Override
        public void onVideoFrameAvailable(byte[] data, int size, int width, int height, int format, long ts) {
            Log.i(TAG, "onVideoFrameAvailable: " + size + ", " + width + " x " + height + ", " + format + ", " + ts);
            if (format == PLOnVideoFrameListener.VIDEO_FORMAT_SEI && bytesToHex(Arrays.copyOfRange(data, 19, 23)).equals("74733634")) {
                // If the RTMP stream is from Qiniu
                // Add &addtssei=true to the end of URL to enable SEI timestamp.
                // Format of the byte array:
                // 0:       SEI TYPE                    This is part of h.264 standard.
                // 1:       unregistered user data      This is part of h.264 standard.
                // 2:       payload length              This is part of h.264 standard.
                // 3-18:    uuid                        This is part of h.264 standard.
                // 19-22:   ts64                        Magic string to mark this stream is from Qiniu
                // 23-30:   timestamp                   The timestamp
                // 31:      0x80                        Magic hex in ffmpeg
                Log.i(TAG, " timestamp: " + Long.valueOf(bytesToHex(Arrays.copyOfRange(data, 23, 31)), 16));
            }
        }
    };

    private PLOnAudioFrameListener mOnAudioFrameListener = new PLOnAudioFrameListener() {
        @Override
        public void onAudioFrameAvailable(byte[] data, int size, int samplerate, int channels, int datawidth, long ts) {
            Log.i(TAG, "onAudioFrameAvailable: " + size + ", " + samplerate + ", " + channels + ", " + datawidth + ", " + ts);
        }
    };

    private MediaController.OnClickSpeedAdjustListener mOnClickSpeedAdjustListener = new MediaController.OnClickSpeedAdjustListener() {
        @Override
        public void onClickNormal() {
            // 0x0001/0x0001 = 2
            mVideoView.setPlaySpeed(0X00010001);
//            ToastUtils.showShort("1");
        }

        @Override
        public void onClickFaster() {
            // 0x0002/0x0001 = 2
            mVideoView.setPlaySpeed(0X00020001);
//            ToastUtils.showShort("2");
        }

        @Override
        public void onClickSlower() {
            // 0x0001/0x0002 = 0.5
            mVideoView.setPlaySpeed(0X00010002);
//            ToastUtils.showShort("3");
        }
    };

    private String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private void updateStatInfo() {
        long bitrate = mVideoView.getVideoBitrate() / 1024;
        final String stat = bitrate + "kbps, " + mVideoView.getVideoFps() + "fps";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatInfoTextView.setText(stat);
            }
        });
    }

    private void getHttpLiveInfo(LiveBean liveBean) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String accountName = SPUtils.look(this, SPkey.username);
        long timeStampSec = TimeUtils.get13ServiceTimeStamp() / 1000;
        pos_GetElectronicLiveVideo.DrawInfo drawInfo = new pos_GetElectronicLiveVideo.DrawInfo(liveBean.getDrawNumber(),liveBean.getGameAlias(),timeStampSec);
        pos_GetElectronicLiveVideo pos_gameQueryInfo = new pos_GetElectronicLiveVideo(accountName,drawInfo);
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetElectronicLiveVideo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                mVideoView.setVideoPath(jsonObject.getString("url"));
                            }else {
                                ProgressUtil.dismissProgressDialog();
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

    private void getHttpLiveInfoTest() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String accountId = SPUtils.look(this, SPkey.accountId);
//        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetElectronicLiveVideoTest)
                .upJson("")
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String url = jsonObject.getString("url");
                            mVideoView.setVideoPath(url);
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
}
