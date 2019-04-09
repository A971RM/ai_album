package com.fun.anime;

/**
 * 动画合成监听器
 */
public interface OnCombineListener {

    void onStart();

    /**
     * 合成进度
     * @param progress  百分比0-100
     */
    void onProgress(float progress);

    void onFinish();

    void onError(int code);
}