package com.fun.anime;

import android.content.Context;
import android.view.SurfaceView;

/**
 * 动画管理类
 */
public class AnimeDirector {

    private Context mContext;
    private Anime mAnime;                       // 动画资源对象
    private SurfaceView mSurfaceView;           // 动画播放器控件
    private OnPlayListener mPlayListener;       // 播放监听器
    private OnCombineListener mCombineListener; // 合成监听器

    public AnimeDirector(Context context, SurfaceView surfaceView){
        this.mContext = context;
        this.mSurfaceView = surfaceView;
    }

    public void setAnime(Anime anime) {
        this.mAnime = anime;
    }

    public void setPlayListener(OnPlayListener playListener) {
        this.mPlayListener = playListener;
    }

    public void setCombineListener(OnCombineListener combineListener) {
        this.mCombineListener = combineListener;
    }

    // 开始播放
    public void start(){

    }

    // 暂停播放
    public void stop(){

    }

    // 继续播放
    public void resume(){

    }

    // 定点播放（毫秒）
    public void seekTo(int mills) {

    }

    // 释放资源
    public void release(){

    }

    // 合成视频
    public void combine(){

    }


}
