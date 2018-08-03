package com.example.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.example.airhockeywithbettermallets.R;


public class ColorShaderProgram extends ShaderProgram {

    //Uniform Location
    private int uMatrixLocation;

    //Attribute Location
    private int aPositionLocation;
    private int uColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
    }

    public void setUniform(float[] matrix, float r, float g, float b) {
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,matrix,0);
        GLES20.glUniform4f(uColorLocation, r,g,b,1f);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

}
