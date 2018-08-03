package com.example.airhockeytextured;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.util.MatrixHelper;
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
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
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

    //add for matrix
    public static final String U_MATRIX = "u_Matrix";
    public int uMatrixLocation;
    public float[] projectMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    //add end

    private FloatBuffer vertexData;
    private Context mContext;
    private int mProgram;

    public AirHockeyRenderer(Context context) {
        this.mContext = context;
        float[] tableVerticesWithTriangles = {
        //x,y,z,w,r,g,b,
                // Triangles fan
                 0.0f,  0.0f, 1.0f, 1.0f, 1.0f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f,  -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f,   0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f,  0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                // Line1
                -0.5f,  0.0f, 1.0f, 0f, 0f,
                0.5f,   0.0f, 1.0f, 0f, 0f,
                // Mallets
                0.0f,  -0.4f, 0f, 0f, 1f,
                0.0f,   0.4f, 1.0f, 0f, 0f,
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
        uMatrixLocation = glGetUniformLocation(mProgram, U_MATRIX);

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
        glUniformMatrix4fv(uMatrixLocation,1,false,projectMatrix,0);

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
