package com.anime.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AnimeSurfaceRender implements GLSurfaceView.Renderer {
    public AnimeSurfaceRender(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glSetup();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mViewportXoff = 0;
        mViewportYoff = 0;
        mViewportWidth = width;
        mViewportHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glViewport(mViewportXoff, mViewportYoff, mViewportWidth, mViewportHeight);
        drawFrame();
    }

    public void drawFrame () {
        // Clear the color buffer
        GLES30.glClear ( GLES30.GL_COLOR_BUFFER_BIT );
    }

    /**
     * Performs one-time GL setup (creating programs, disabling optional features).
     */
    private void glSetup() {
        GLES30.glClearColor ( 1.0f, 1.0f, 1.0f, 1.0f );
    }

    // Size and position of the GL viewport, in screen coordinates.  If the viewport covers the
    // entire screen, the offsets will be zero and the width/height values will match the
    // size of the display.  (This is one of the few places where we deal in actual pixels.)
    private int mViewportWidth, mViewportHeight;
    private int mViewportXoff, mViewportYoff;
    private Context mContext;
}
