package com.example.util;


import android.util.Log;

import com.example.airhockeytextured.AirHockeyTextured;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

public class ShaderHelper {

    public static final String TAG = AirHockeyTextured.TAG_TITLE + ShaderHelper.class.getSimpleName();

    public static int compileVertexShader(String source) {
        return compileShader(GL_VERTEX_SHADER, source);
    }

    public static int compileFragmentShader(String source) {
        return compileShader(GL_FRAGMENT_SHADER, source);
    }

    public static int compileShader(int type, String source) {

        int id = glCreateShader(type);
        if (id == 0) {
            Log.e(TAG, "compileShader: create shader type 0 fail");
        }

        glShaderSource(id, source);
        glCompileShader(id);
        int[] status = new int[1];
        glGetShaderiv(id, GL_COMPILE_STATUS, status, 0);
        Log.e(TAG, "compileShader: state " + glGetShaderInfoLog(id));
        if (status[0] == 0) {
            glDeleteShader(id);
        }

        return id;
    }

    public static int linkProgram(int verdexShaderId, int fragmentShaderId) {

        int programId = glCreateProgram();
        if (programId == 0) {
            Log.e(TAG, "linkProgram: create fail id == 0" );
        }

        glAttachShader(programId, verdexShaderId);
        glAttachShader(programId, fragmentShaderId);

        glLinkProgram(programId);
        Log.i(TAG, "linkProgram: link program id " + programId);

        int[] state = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, state, 0);
        if (state[0] == 0) {
            Log.e(TAG, "linkProgram: fail ");
            glDeleteProgram(programId);
        }

        return programId;
    }

    public static boolean isVaildProgram(int programId) {
        glValidateProgram(programId);

        int[] status = new int[1];
        glGetProgramiv(programId, GL_VALIDATE_STATUS, status, 0);
        Log.i(TAG, "isVaildProgram: result id " + status[0] + " info  " + glGetProgramInfoLog(programId));

        return status[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int program;

        int vertex = compileVertexShader(vertexShaderSource);
        int fragment = compileFragmentShader(fragmentShaderSource);
        program = linkProgram(vertex, fragment);

        return program;
    }
}
