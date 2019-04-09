package com.fun.anime;


/**
 * 动画播放监听器
 */
public interface OnPlayListener {

    void onStart();

    /**
     * 播放进度
     * @param position  当前播放位置(毫秒)
     * @param duration  总时长(毫秒)
     */
    void onProgress(int position, int duration);

    void onStop();

    void onError(int code);

    void onFinish();
}