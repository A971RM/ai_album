package com.anime.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.ConditionVariable;
import android.util.AttributeSet;

import com.faddensoft.breakout.GameRecorder;
import com.fun.anime.Anime;

import java.io.File;

public class AnimeSurfaceView extends GLSurfaceView {
    public AnimeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnimeSurfaceView(Context context) {
        super(context);
        init(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.d(TAG, "asking renderer to pause");
        syncObj.close();
        queueEvent(new Runnable() {
            @Override public void run() {
                mAnimeSurfaceRender.onViewPause(syncObj);
            }});
        syncObj.block();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init(Context context) {
        mContext = context;
        setEGLContextClientVersion ( CONTEXT_CLIENT_VERSION );
        mAnimeSurfaceRender = new AnimeSurfaceRender(context);
        setRenderer(mAnimeSurfaceRender);
        GameRecorder.getInstance().prepareEncoder(mContext, "");
    }

    public void setAnime(Anime anime) {
        mAnime = anime;
    }

    private final int CONTEXT_CLIENT_VERSION = 2;
    private AnimeSurfaceRender mAnimeSurfaceRender;
    private Context mContext;
    private Anime mAnime = null;
    private final ConditionVariable syncObj = new ConditionVariable();
}
