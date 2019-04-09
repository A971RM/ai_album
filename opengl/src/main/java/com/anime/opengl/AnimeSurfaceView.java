package com.anime.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

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
    }

    private final int CONTEXT_CLIENT_VERSION = 2;
    private AnimeSurfaceRender mAnimeSurfaceRender;
    private Context mContext;
}
