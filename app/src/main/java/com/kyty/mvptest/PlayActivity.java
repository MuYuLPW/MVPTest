package com.kyty.mvptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.kyty.mvptest.utils.JZMediaIjk;
import com.kyty.mvptest.utils.JZMediaSystem;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class PlayActivity extends AppCompatActivity {
    private JzvdStd jzvdStd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        String url = getIntent().getStringExtra("url");
        jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
        jzvdStd.setUp(url, "", Jzvd.SCREEN_NORMAL);
        jzvdStd.startVideo();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.resetAllVideos();
    }
}
