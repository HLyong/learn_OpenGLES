package com.example.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.example.airhockeytextured.R;

public class ColorShaderProgram extends ShaderProgram {

    //Uniform Location
    private int uMatrixLocation;

    //Attribute Location
    private int aPositionLocation;
    private int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
    }

    public void setUniform(float[] matrix) {
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,matrix,0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getColorAttributeLocation() {
        return aColorLocation;
    }
}
