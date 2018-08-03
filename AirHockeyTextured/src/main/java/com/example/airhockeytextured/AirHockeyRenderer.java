package com.example.airhockeytextured;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.Object.Mallet;
import com.example.Object.Tables;
import com.example.programs.ColorShaderProgram;
import com.example.programs.TextureShaderProgram;
import com.example.util.MatrixHelper;
import com.example.util.ShaderHelper;
import com.example.util.TextResourceReader;
import com.example.util.TextureHelper;

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
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    public  final float[] projectMatrix = new float[16];
    public  final float[] modelMatrix = new float[16];

    private Tables mTable;
    private Mallet mMallet;

    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;

    private int texture;

    public AirHockeyRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f,0.0f,0.0f);

        mTable = new Tables();
        mMallet = new Mallet();

        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        glViewport(0,0,width,height);

        MatrixHelper.perspectiveM(projectMatrix, 45, (float)width/(float)height, 1f, 10f);
        Matrix.setIdentityM(modelMatrix,0);
        Matrix.translateM(modelMatrix,0,0f,0,-3f);
        Matrix.rotateM(modelMatrix,0,-60f, 1f, 0f,0f);

        float[] temp = new float[16];
        Matrix.multiplyMM(temp,0,projectMatrix,0,modelMatrix,0);
        System.arraycopy(temp,0,projectMatrix,0,temp.length);
        //适配横竖屏
//        float aspectRatio = width > height ? (float)width/height : (float)height/width;
//        if (width > height) {
//            Matrix.orthoM(projectMatrix, 0, -aspectRatio, aspectRatio, -1, 1, -1, 1);
//        } else {
//            Matrix.orthoM(projectMatrix, 0, -1, 1, -aspectRatio, aspectRatio, -1, 1);
//        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        textureShaderProgram.useProgram();
        textureShaderProgram.setUniform(projectMatrix, texture);
        mTable.bindData(textureShaderProgram);
        mTable.draw();

        colorShaderProgram.useProgram();
        colorShaderProgram.setUniform(projectMatrix);
        mMallet.bindData(colorShaderProgram);
        mMallet.draw();
    }
}
