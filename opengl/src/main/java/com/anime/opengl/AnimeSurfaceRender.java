package com.anime.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.openglesbook.common.ESShader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

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

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Load shaders from 'assets' and get a linked program object
        mProgramObject = ESShader.loadProgramFromAsset ( mContext,
                "shaders/vertexShader.vert",
                "shaders/fragmentShader.frag" );

        // Get the attribute locations
        mPositionLoc = GLES20.glGetAttribLocation(mProgramObject, "a_position");
        mTexCoordLoc = GLES20.glGetAttribLocation(mProgramObject, "a_texCoord" );

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
        GLES20.glDrawElements ( GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, mIndices );
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
}
