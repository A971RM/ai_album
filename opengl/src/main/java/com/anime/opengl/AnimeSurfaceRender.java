package com.anime.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.ConditionVariable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.faddensoft.breakout.GameRecorder;
import com.fun.anime.Anime;
import com.fun.anime.Frame;
import com.openglesbook.common.ESShader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AnimeSurfaceRender implements GLSurfaceView.Renderer {
    private static final String TAG = "AnimeSurfaceRender";

    public AnimeSurfaceRender(Context context) {
        super();
        mContext = context;
        mVertices = ByteBuffer.allocateDirect ( mVerticesData.length * 4 )
                .order ( ByteOrder.nativeOrder() ).asFloatBuffer();
        mVertices.put ( mVerticesData ).position ( 0 );
        mIndices = ByteBuffer.allocateDirect ( mIndicesData.length * 2 )
                .order ( ByteOrder.nativeOrder() ).asShortBuffer();
        mIndices.put ( mIndicesData ).position ( 0 );
    }

    ///
    //  Load texture from bitmap
    //
    private int loadTexture (Bitmap bitmap)
    {
        int[] textureId = new int[1];
        GLES20.glGenTextures ( 1, textureId, 0 );
        GLES20.glBindTexture ( GLES20.GL_TEXTURE_2D, textureId[0] );
        GLUtils.texImage2D( GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR );
        GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR );
        GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE );
        GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE );
        return textureId[0];
    }

    private int loadTexture (String imagePath)
    {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDensity = dm.densityDpi;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);
        int retId = -1;
        if (bitmap != null) {
            retId = loadTexture(bitmap);
            bitmap.recycle();
        }
        return retId;
    }

    ///
    //  Load texture from asset
    //
    private int loadTextureFromAsset ( String fileName )
    {
        int retId = -1;
        Bitmap bitmap;
        InputStream is;
        try {
            is = mContext.getAssets().open ( fileName );
        } catch ( IOException ioe ) {
            is = null;
        }

        if ( is != null ) {
            bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                retId = loadTexture(bitmap);
                bitmap.recycle();
            }
        }

        return retId;
    }

    private int makeTextTexture( String textString) {
        int retId = -1;
        // Create an empty, mutable bitmap
        Bitmap bitmap = Bitmap.createBitmap(mViewportWidth, mViewportHeight, Bitmap.Config.ARGB_8888);
        // get a canvas to paint over the bitmap
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(0);

        // Draw the text
        Paint textPaint = new Paint();
        textPaint.setTextSize(64);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(0xff, 0xff, 0xff, 0xff);
        // draw the text centered
        canvas.drawText(textString, 16,112, textPaint);
        retId = loadTexture(bitmap);
        return retId;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Load shaders from 'assets' and get a linked program object
        mProgramObject = ESShader.loadProgramFromAsset ( mContext,
                "shaders/vertexShader.vert",
                "shaders/fragmentShader.frag" );

        // Get the attribute locations
        mPositionLoc = GLES20.glGetAttribLocation(mProgramObject, "a_position");
        mTexCoordLoc = GLES20.glGetAttribLocation(mProgramObject, "a_texCoord" );

        // Get the sampler locations
        mTexture0Loc = GLES20.glGetUniformLocation ( mProgramObject, "s_texture0" );
        mTexture1Loc = GLES20.glGetUniformLocation ( mProgramObject, "s_texture1" );
        mTextureTextLoc = GLES20.glGetUniformLocation ( mProgramObject, "s_texturetext" );

        // Load the texture
        mTexture0TexId = loadTextureFromAsset ( "textures/basemap.png" );
        mTexture1TexId = loadTextureFromAsset ( "textures/lightmap.png" );
        mTextureTextTexId = makeTextTexture("Hello world");

        glSetup();
        // now repeat it for the game recorder
        GameRecorder recorder = GameRecorder.getInstance();
        Log.d(TAG, "configuring GL for recorder");
        saveRenderState();
        recorder.firstTimeSetup();
        recorder.makeCurrent();
        glSetup();
        restoreRenderState();
        mFrameCount = 0;
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


        GameRecorder recorder = GameRecorder.getInstance();
        if (recorder.isRecording() && recordThisFrame()) {
            saveRenderState();

            // switch to recorder state
            recorder.makeCurrent();
            recorder.setViewport();

            // render everything again
            drawFrame();
            recorder.swapBuffers();

            restoreRenderState();
        }
    }

    public void drawFrame () {
        // Clear the color buffer
        GLES20.glClear ( GLES30.GL_COLOR_BUFFER_BIT );
        // Use the program object
        GLES20.glUseProgram ( mProgramObject );

        // Load the vertex position
        mVertices.position(0);
        GLES20.glVertexAttribPointer ( mPositionLoc, 3, GLES20.GL_FLOAT,
                false,
                5 * 4, mVertices );
        // Load the texture coordinate
        mVertices.position(3);
        GLES20.glVertexAttribPointer ( mTexCoordLoc, 2, GLES20.GL_FLOAT,
                false,
                5 * 4,
                mVertices );

        GLES20.glEnableVertexAttribArray ( mPositionLoc );
        GLES20.glEnableVertexAttribArray ( mTexCoordLoc );

        // Bind the texture0 map
        GLES20.glActiveTexture ( GLES20.GL_TEXTURE0 );
        GLES20.glBindTexture ( GLES20.GL_TEXTURE_2D, mTexture0TexId );
        GLES20.glUniform1i ( mTexture0Loc, 0 );

        // Bind the texture1 map
        GLES20.glActiveTexture ( GLES20.GL_TEXTURE1 );
        GLES20.glBindTexture ( GLES20.GL_TEXTURE_2D, mTexture1TexId );
        GLES20.glUniform1i ( mTexture1Loc, 1 );

        // Bind the texturetext map
        GLES20.glActiveTexture ( GLES20.GL_TEXTURE2 );
        GLES20.glBindTexture ( GLES20.GL_TEXTURE_2D, mTextureTextTexId );
        GLES20.glUniform1i ( mTextureTextLoc, 2 );

        GLES20.glDrawElements ( GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, mIndices );
    }

    /**
     * Performs one-time GL setup (creating programs, disabling optional features).
     */
    private void glSetup() {
        GLES30.glClearColor ( 1.0f, 1.0f, 1.0f, 1.0f );
    }

    /**
     * Saves the current projection matrix and EGL state.
     */
    private void saveRenderState() {
        mSavedEglDisplay = EGL14.eglGetCurrentDisplay();
        mSavedEglDrawSurface = EGL14.eglGetCurrentSurface(EGL14.EGL_DRAW);
        mSavedEglReadSurface = EGL14.eglGetCurrentSurface(EGL14.EGL_READ);
        mSavedEglContext = EGL14.eglGetCurrentContext();
    }

    /**
     * Saves the current projection matrix and EGL state.
     */
    private void restoreRenderState() {
        // switch back to previous state
        if (!EGL14.eglMakeCurrent(mSavedEglDisplay, mSavedEglDrawSurface, mSavedEglReadSurface,
                mSavedEglContext)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    private boolean recordThisFrame() {
        final int TARGET_FPS = 30;

        mFrameCount++;
        switch (TARGET_FPS) {
            case 60:
                return true;
            case 30:
                return (mFrameCount & 0x01) == 0;
            case 24:
                // want 2 out of every 5 frames
                int mod = mFrameCount % 5;
                return mod == 0 || mod == 2;
            default:
                return true;
        }
    }

    /**
     * Handles pausing of the game Activity.  This is called by the View (via queueEvent) at
     * pause time.  It tells GameState to save its state.
     *
     * @param syncObj Object to notify when we have finished saving state.
     */
    public void onViewPause(ConditionVariable syncObj) {
        /*
         * We don't explicitly pause the game action, because the main game loop is being driven
         * by the framework's calls to our onDrawFrame() callback.  If we were driving the updates
         * ourselves we'd need to do something more.
         */

        GameRecorder.getInstance().gamePaused();

        syncObj.open();
    }

    public void onViewRecordStart() {
        GameRecorder.getInstance().startRecording();
    }

    public void updateAnime(Anime anime) {
        List<Frame> frames = anime.getFrames();
        if (frames.size() == 0) {
            return;
        }
        for (int i = 0; i < 2 && i < frames.size(); i ++) {
            Frame frame = frames.get(i);
            if (i == 0) {
                mTexture0TexId = loadTexture(frame.getImagePath());
            } else {
                mTexture1TexId = loadTexture(frame.getImagePath());
            }
            mTextureTextTexId = makeTextTexture(frame.getFrameText().getText());
        }
    }

    private EGLDisplay mSavedEglDisplay;
    private EGLSurface mSavedEglDrawSurface;
    private EGLSurface mSavedEglReadSurface;
    private EGLContext mSavedEglContext;
    // Size and position of the GL viewport, in screen coordinates.  If the viewport covers the
    // entire screen, the offsets will be zero and the width/height values will match the
    // size of the display.  (This is one of the few places where we deal in actual pixels.)
    private int mViewportWidth = 1080, mViewportHeight = 1920;
    private int mViewportXoff = 0, mViewportYoff = 0;
    // Frame counter, used for reducing recorder frame rate.
    private int mFrameCount;
    private Context mContext;

    // GLSL
    private final float[] mVerticesData =
    {
        -1.0f,  1.0f, 0.0f, // Position 0
        0.0f,  0.0f,       // TexCoord 0
        -1.0f, -1.0f, 0.0f, // Position 1
        0.0f,  1.0f,       // TexCoord 1
        1.0f, -1.0f, 0.0f, // Position 2
        1.0f,  1.0f,       // TexCoord 2
        1.0f,  1.0f, 0.0f, // Position 3
        1.0f,  0.0f        // TexCoord 3
    };

    private final short[] mIndicesData =
    {
        0, 1, 2, 0, 2, 3
    };

    private FloatBuffer mVertices;
    private ShortBuffer mIndices;

    // Handle to a program object
    private int mProgramObject;
    // Attribute locations
    private int mPositionLoc;
    private int mTexCoordLoc;

    // Texture handle
    private int mTexture0Loc;
    private int mTexture1Loc;
    private int mTexture0TexId;
    private int mTexture1TexId;
    private int mTextureTextLoc;
    private int mTextureTextTexId;
}
