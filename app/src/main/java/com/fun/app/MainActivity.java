package com.fun.app;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;

import com.anime.opengl.AnimeSurfaceView;
import com.fun.anime.Anime;
import com.fun.anime.AnimeDirector;
import com.fun.anime.Frame;
import com.fun.anime.FrameText;
import com.fun.anime.OnCombineListener;
import com.fun.anime.OnPlayListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private AnimeDirector animeManager;
    private AnimeSurfaceView surfaceView;
    private Anime anime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        surfaceView = findViewById(R.id.surfaceView);

        findViewById(R.id.preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAnime(1);
            }
        });

        findViewById(R.id.combine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCombine();
            }
        });

        animeManager = new AnimeDirector(this, surfaceView);
    }

    private OnPlayListener mPlayListener = new OnPlayListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onProgress(int progress, int duration) {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onError(int code) {

        }

        @Override
        public void onFinish() {

        }
    };

    private void loadAnime(int templateId){
        anime = new Anime();
        anime.setId(System.currentTimeMillis());
        anime.setTemplateId(templateId);
        anime.setHeight(1280);
        anime.setWidth(720);
        anime.setVideoPath("/storage/emulated/0/ai-album/videos/anime.mp4");
        anime.setDuration(15);

        anime.setMusicName("");
        anime.setMusicAuthor("");
        anime.setMusicPath("");
        anime.setMusicOffset(10000);
        anime.setMusicDuration(167000);
        anime.setMusicVolume(0.6f);

        anime.setBackgroundAlpha(1f);
        anime.setBackgroundColor("#FF34B3");
        anime.setBackgroundPath("");
        anime.setBackgroundType(Anime.BACKGROUND_TYPE_COLOR);
        anime.setCoverPath("");
        anime.setLogoPath("");
        anime.setShowLogo(false);

        List<Frame> frames = new ArrayList<>();
        for(int i=0; i<10; i++) {
            FrameText frameText = new FrameText();
            frameText.setText(getTxt(i));
            frameText.setTextColor("#FFFFFF");
            frameText.setTextSize(32);
            frameText.setTextBold(false);
            frameText.setBorderColor("");
            frameText.setBorderWidth(0);
            frameText.setFontName("");
            frameText.setShowTemplateId(-1); // 无文字特效
            frameText.setRect(null);
            frameText.setTypeface("");
            frameText.setTypeFacePath("");

            Frame frame = new Frame();
            frame.setFrameText(frameText);
            frame.setPlayTime(2 * i + 0.2f);
            frame.setImagePath("");
            frame.setType(0);

            frames.add(frame);
        }
        anime.setFrames(frames);

        animeManager.setPlayListener(mPlayListener);
        animeManager.setAnime(anime);
        animeManager.start();
        surfaceView.startRecord();
    }

    private void doCombine(){
        surfaceView.stopRecord();
        animeManager.setCombineListener(new OnCombineListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(float progress) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onError(int code) {

            }
        });
        animeManager.combine();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //通过调用item.getItemId()来判断菜单项
        switch (item.getItemId()){
            case R.id.anime1:
                loadAnime(1);
                break;
            case R.id.anime2:
                loadAnime(2);
                break;
            case R.id.anime3:
                loadAnime(3);
                break;
            default:
        }
        return true;
    }


    static String[] texts = {
            "枝头",
            "那是怒放的花儿",
            "还是翩翩的蝴蝶",
            "为一个约定",
            "不辜负这繁华的季节",
            "水中",
            "那是你的倒影",
            "还是我昨夜的一场梦境",
            "心湖正悠悠地荡漾",
            "惊起一只翠绿的蜻蜓"
    };

    private static String getTxt(int index) {
        return texts[index % texts.length];
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
    }
}
