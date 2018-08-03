package com.example.airhockeyortho;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.util.ShaderHelper;
import com.example.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    public static final int BYTE_PRE_FLOAT = 4;

    public static final int POSITION_COUNT = 2;
    public static final int COLOR_COUNT = 3;
    public static final int STRIDE = (POSITION_COUNT + COLOR_COUNT) * BYTE_PRE_FLOAT;

    public static final String A_COLOR = "a_Color";
    public static final String A_POSITION = "a_Position";
    public int mPositionLocation;
    private int aColorLocation;

    private FloatBuffer vertexData;
    private Context mContext;
    private int mProgram;

    public AirHockeyRenderer(Context context) {
        this.mContext = context;
        float[] tableVerticesWithTriangles = {

                // Triangles fan
                 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f,  -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f,  0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                // Line1
                -0.5f,  0.0f, 1.0f, 0f, 0f,
                0.5f,  0.0f, 1.0f, 0f, 0f,
                // Mallets
                0.0f, -0.25f, 0f, 0f, 1f,
                0.0f,  0.25f, 1.0f, 0f, 0f,
        };

        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTE_PRE_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f,0.0f,0.0f);

        String vertexShaderSource = TextResourceReader.readTextFileFormResource(mContext, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFormResource(mContext, R.raw.simple_fragment_shader);

        int vertexShaderId = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        mProgram = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId);
        ShaderHelper.isVaildProgram(mProgram);
        glUseProgram(mProgram);
        aColorLocation = glGetAttribLocation(mProgram, A_COLOR);
        mPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
        vertexData.position(0);
        glVertexAttribPointer(mPositionLocation, POSITION_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(mPositionLocation);

        vertexData.position(POSITION_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //画三角形，白色背景
//        glUniform4f(mColorLocation,1.0f,10.f,1.0f,1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        //画分割线
//        glUniform4f(mColorLocation,1.0f,0.0f,0.0f,1.0f);
        glDrawArrays(GL_LINES, 6, 2);

        //画点
//        glUniform4f(mColorLocation, 0.0f,0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);

//        glUniform4f(mColorLocation, 1.0f,0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
