package com.example.Object;

import com.example.data.VertexArray;
import com.example.programs.ColorShaderProgram;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

public class Mallet {
    public static final int POSITION_COMPONENT_CONUT = 2;
    public static final int COLOR_COMPONENT_CONUT = 3;
    public static final int STRIDE = (POSITION_COMPONENT_CONUT + COLOR_COMPONENT_CONUT) * VertexArray.BYTE_LENGTH;

    public static final float[] VERTEX_DATA = {
            //order of coordinates : X,Y,R,G,B

            //TriangleFan
            0.0f,  -0.4f, 0f, 0f, 1f,
            0.0f,   0.4f, 1.0f, 0f, 0f,
    };

    private final VertexArray vertexArray;

    public Mallet() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexArrtibPointer(0
                ,colorShaderProgram.getPositionAttributeLocation()
                ,POSITION_COMPONENT_CONUT
                ,STRIDE);

        vertexArray.setVertexArrtibPointer(POSITION_COMPONENT_CONUT
                ,colorShaderProgram.getColorAttributeLocation()
                ,COLOR_COMPONENT_CONUT
                ,STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0 ,2);
    }
}
