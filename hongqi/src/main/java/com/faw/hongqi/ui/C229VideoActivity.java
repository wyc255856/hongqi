package com.faw.hongqi.ui;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.faw.hongqi.R;
import com.faw.hongqi.model.InteractiveVideoModel;
import com.faw.hongqi.util.FileUtil;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.view.SpreadView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class C229VideoActivity extends BaseActivity {
    VideoView video_view;
    FrameLayout interactive_layout;
    Button play_btn;
    SpreadView spreadView;

    @Override
    protected void initData() {
        initInteractive();
        setContentView(R.layout.activity_c229_video);

    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initViews() {
        video_view = findViewById(R.id.video_view);
        interactive_layout = findViewById(R.id.interactive_layout);
        play_btn = findViewById(R.id.play_btn);
        spreadView = findViewById(R.id.spreadView);

    }

    @Override
    protected void initWidgetActions() {
        initLocalVideo();
        findViewById(R.id.main_back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        video_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPause();
            }
        });
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoStart();
            }
        });
        spreadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactive_layout.setVisibility(View.GONE);
                videoStart();
            }
        });
    }

    public void videoPause() {
        video_view.pause();
        play_btn.setVisibility(View.VISIBLE);
    }

    public void videoStart() {
        video_view.start();
        play_btn.setVisibility(View.GONE);
    }

    @Override
    boolean isHasTitle() {
        return true;
    }

    //播放本地视频
    private void initLocalVideo() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    while (video_view.isPlaying()) {
//                        final long currentPosition = getCurrentPosition();
//                        LogUtil.logError( "currentPosition==" + currentPosition );
//                        showinitInteractive(currentPosition);
//                        Thread.sleep(500);
//                        continue;
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        video_view.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                LogUtil.logError("currentPosition==" + mp.getCurrentPosition());
                return false;
            }
        });
        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {


                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        LogUtil.logError("currentPosition==" + percent);

                    }
                });
            }
        });
        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaybackVideo();
            }
        });
        video_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                stopPlaybackVideo();
                return true;
            }
        });
        //设置有进度条可以拖动快进
        MediaController localMediaController = new MediaController(this);
        video_view.setMediaController(localMediaController);
        String path = FileUtil.getResPath() + "images/2019-03-12/5c870f97c6e23.mp4";
        LogUtil.logError("path = " + path);
        File file = new File(path);
        LogUtil.logError("file = " + file.exists());
        Uri uri = Uri.parse(path);

//        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.test);
        video_view.setVideoURI(uri);
//        video_view.setVideoURI(uri);
//        video_view.start();
        video_view.requestFocus();// 让VideoView获取焦点
    }

    boolean is_play = false;

    @Override
    protected void onPause() {
        super.onPause();
        if (video_view.canPause()) {
            video_view.pause();
            is_play = true;
        } else {
            is_play = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (is_play) {
            video_view.start();
        }
    }

    private void stopPlaybackVideo() {
        try {
            video_view.stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getCurrentPosition() {
        if (video_view != null) {
            return video_view.getCurrentPosition();
        }
        return 0;
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

    public void showinitInteractive(long position) {
        if (position == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            final InteractiveVideoModel interactiveVideoModel = list.get(i);
            if (position > interactiveVideoModel.time && !interactiveVideoModel.isShow) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        interactiveVideoModel.isShow = true;
                        interactive_layout.setVisibility(View.VISIBLE);
                        interactive_layout.setBackgroundResource(interactiveVideoModel.resId);
                        video_view.pause();
                    }
                });

            }
        }

    }
}
