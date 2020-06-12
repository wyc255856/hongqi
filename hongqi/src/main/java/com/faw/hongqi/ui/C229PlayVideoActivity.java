package com.faw.hongqi.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.model.InteractiveVideoModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.FileUtil;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.view.SpreadView;
import com.faw.hongqi.widget.BigPointView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class C229PlayVideoActivity extends BaseActivity implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener, View.OnClickListener,
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnVideoSizeChangedListener,
        SeekBar.OnSeekBarChangeListener {

    private ImageView playOrPauseIv;
    private SurfaceView videoSuf;
    private MediaPlayer mPlayer;
    private SeekBar mSeekBar;
    private String path;
    private RelativeLayout rootViewRl;
    private LinearLayout controlLl;
    private TextView startTime, endTime;
    private boolean isShow = false;
    FrameLayout interactive_layout;
    BigPointView spreadView;
    public static final int UPDATE_TIME = 0x0001;
    public static final int HIDE_CONTROL = 0x0002;

    NewsModel newsModel;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIME:
                    updateTime();
                    mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
                    break;

                case HIDE_CONTROL:
//                    hideControl();
                    break;
            }
        }
    };


    private void initEvent() {
        playOrPauseIv.setOnClickListener(this);
        rootViewRl.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setClickable(false);
        mSeekBar.setEnabled(false);
        mSeekBar.setSelected(false);
        mSeekBar.setFocusable(false);
        findViewById(R.id.main_back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                stop();
            }
        });
        spreadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactive_layout.setVisibility(View.GONE);
                play();
            }
        });
    }

    private void initSurfaceView() {
        videoSuf = (SurfaceView) findViewById(R.id.surfaceView);
        videoSuf.setZOrderOnTop(false);
        videoSuf.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        videoSuf.getHolder().addCallback(this);
    }

    private void initPlayer() {
        mPlayer = new MediaPlayer();
        mAm = (AudioManager) getSystemService(AUDIO_SERVICE);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnInfoListener(this);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnSeekCompleteListener(this);
        mPlayer.setOnVideoSizeChangedListener(this);
        try {
            //使用手机本地视频

//            if (Constant.TEST) {
//                AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.vd);
//                try {
//                    mPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
//                            file.getLength());
//                    mPlayer.prepare();
//                    file.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {

//            AssetFileDescriptor afd = getResources().getAssets().openFd("5ec5dd3a24d33.mp4");
//
//            mPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());


            mPlayer.setDataSource(path);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        initInteractive();
        newsModel = (NewsModel) getIntent().getSerializableExtra("data");
        path = (FileUtil.getResPath() + newsModel.getVideo1()).replace("/HONGQIH9/standard", "");
        LogUtil.logError("playPath = " + path);
        setContentView(R.layout.activity_c229_play_video);
        initViews();
        initSurfaceView();
        initPlayer();
        initEvent();
    }

    @Override
    protected void initViews() {
        playOrPauseIv = (ImageView) findViewById(R.id.playOrPause);
        startTime = (TextView) findViewById(R.id.tv_start_time);
        endTime = (TextView) findViewById(R.id.tv_end_time);
        mSeekBar = (SeekBar) findViewById(R.id.tv_progess);
        rootViewRl = (RelativeLayout) findViewById(R.id.root_rl);
        controlLl = (LinearLayout) findViewById(R.id.control_ll);
        interactive_layout = findViewById(R.id.interactive_layout);
        spreadView = findViewById(R.id.spreadView);
        playOrPauseIv.setVisibility(View.GONE);
    }

    @Override
    protected void initWidgetActions() {
        findViewById(R.id.replay_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(initInteractiveIndex).isShow = false;
                if (initInteractiveIndex == 0) {
                    interactive_layout.setVisibility(View.GONE);
                    mPlayer.seekTo(0);
                    mSeekBar.setProgress(0);
                    play();
                } else {
                    interactive_layout.setVisibility(View.GONE);
                    mPlayer.seekTo((int) list.get(initInteractiveIndex - 1).time);
                    mSeekBar.setProgress((int) list.get(initInteractiveIndex - 1).time);
                    play();
                }
            }
        });
    }

    @Override
    boolean isHasTitle() {
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPlayer.setDisplay(holder);
        mPlayer.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        startTime.setText(formatLongToTimeStr(mp.getCurrentPosition()));
        endTime.setText(formatLongToTimeStr(mp.getDuration()));
        mSeekBar.setMax(mp.getDuration());
        mSeekBar.setProgress(mp.getCurrentPosition());
        play();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    private void play() {
        if (requestFocus()) {
            if (mPlayer == null) {
                return;
            }
            Log.i("playPath", path);
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                mHandler.removeMessages(UPDATE_TIME);
//            mHandler.removeMessages(HIDE_CONTROL);
                playOrPauseIv.setVisibility(View.GONE);
//                playOrPauseIv.setImageResource(android.R.drawable.ic_media_play);
            } else {
                mPlayer.start();
                mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
//            mHandler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
                playOrPauseIv.setVisibility(View.INVISIBLE);
//                playOrPauseIv.setImageResource(android.R.drawable.ic_media_pause);
            }
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        //TODO
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.tv_backward) {
//            backWard();
//        } else if (id == R.id.tv_forward) {
//            forWard();
//        } else
        if (id == R.id.playOrPause) {
            play();
        } else if (id == R.id.root_rl) {
            pause();

        }
    }

    private void pause() {
        mPlayer.pause();
        mHandler.removeMessages(UPDATE_TIME);
//            mHandler.removeMessages(HIDE_CONTROL);
        playOrPauseIv.setVisibility(View.VISIBLE);
//        playOrPauseIv.setImageResource(android.R.drawable.ic_media_play);
        isShow = true;
//        mHandler.removeMessages(HIDE_CONTROL);
        mHandler.sendEmptyMessage(UPDATE_TIME);
//            showControl();
    }

    /**
     * 更新播放时间
     */
    private void updateTime() {

        startTime.setText(formatLongToTimeStr(
                mPlayer.getCurrentPosition()));
        mSeekBar.setProgress(mPlayer.getCurrentPosition());
        showinitInteractive(mPlayer.getCurrentPosition());
    }

    /**
     * 隐藏进度条
     */
    private void hideControl() {
        isShow = false;
        mHandler.removeMessages(UPDATE_TIME);
        controlLl.animate().setDuration(300).translationY(controlLl.getHeight());
    }

    /**
     * 显示进度条
     */
    private void showControl() {
        if (isShow) {
            play();
        }
        isShow = true;
//        mHandler.removeMessages(HIDE_CONTROL);
        mHandler.sendEmptyMessage(UPDATE_TIME);
//        mHandler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
        controlLl.animate().setDuration(300).translationY(0);
    }

    /**
     * 设置快进10秒方法
     */
    private void forWard() {
        if (mPlayer != null) {
            int position = mPlayer.getCurrentPosition();
            mPlayer.seekTo(position + 10000);
        }
    }

    /**
     * 设置快退10秒的方法
     */
    public void backWard() {
        if (mPlayer != null) {
            int position = mPlayer.getCurrentPosition();
            if (position > 10000) {
                position -= 10000;
            } else {
                position = 0;
            }
            mPlayer.seekTo(position);
        }
    }

    //OnSeekBarChangeListener
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (mPlayer != null && b) {
            mPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private String formatLongToTimeStr(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//这里想要只保留分秒可以写成"mm:ss"
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(time);
//        long hours = time / (1000 * 60 * 60);
//        long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
//        String diffTime = "";
//        if (minutes < 10) {
//            diffTime = hours + ":0" + minutes;
//        } else {
//            diffTime = hours + ":" + minutes;
//        }
//        return diffTime;
        return hms;
    }

    List<InteractiveVideoModel> list = new ArrayList<>();

    public void initInteractive() {
        InteractiveVideoModel interactiveVideoModel1 = new InteractiveVideoModel();
        interactiveVideoModel1.isShow = false;
        interactiveVideoModel1.resId = R.mipmap.test_video;
        interactiveVideoModel1.time = 10000;
        list.add(interactiveVideoModel1);


        InteractiveVideoModel interactiveVideoModel2 = new InteractiveVideoModel();
        interactiveVideoModel2.isShow = false;
        interactiveVideoModel2.resId = R.mipmap.test_video;
        interactiveVideoModel2.time = 20000;
        list.add(interactiveVideoModel2);


    }

    int initInteractiveIndex = 0;

    public void showinitInteractive(long position) {
        if (position == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            final InteractiveVideoModel interactiveVideoModel = list.get(i);
            if (position > interactiveVideoModel.time && !interactiveVideoModel.isShow) {
                initInteractiveIndex = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        interactiveVideoModel.isShow = true;
                        interactive_layout.setVisibility(View.VISIBLE);
                        interactive_layout.setBackgroundResource(interactiveVideoModel.resId);
                        mPlayer.pause();
                        mHandler.removeMessages(UPDATE_TIME);
                    }
                });

            }
        }

    }

    public static void goVideoActivity(Context context, NewsModel newsModel) {
//        if (Constant.TEST) {
//            newsModel = DBUtil.getTestModel(context);
//        }
        Intent intent = new Intent(context, C229PlayVideoActivity.class);
        intent.putExtra("data", newsModel);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.in,
                R.anim.out);

    }



    private AudioManager mAm;

    /**
     * 请求语音焦点
     * @return
     */
    private boolean requestFocus() {
        int result = mAm.requestAudioFocus(afChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

    }


    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {

                pause();


            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                play();

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mAm.abandonAudioFocus(afChangeListener);
                stop();
            }

        }

    };

    private void stop() {

        if (mPlayer != null) {

            mPlayer.stop();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
