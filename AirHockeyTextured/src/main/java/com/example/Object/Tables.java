package com.example.Object;

import com.example.data.VertexArray;
import com.example.programs.TextureShaderProgram;

import static android.opengl.GLES20.*;

public class Tables {
    public static final int POSITION_COMPONENT_CONUT = 2;
    public static final int TEXTURE_COORDINATES_COMPONENT_CONUT = 2;
    public static final int STRIDE = (POSITION_COMPONENT_CONUT + TEXTURE_COORDINATES_COMPONENT_CONUT) * VertexArray.BYTE_LENGTH;

    public static final float[] VERTEX_DATA = {
            //order of coordinates : X,Y,S,T

            //TriangleFan
               0f,   0f, 0.5f, 0.5f,
            -0.5f,  -0.8f, 0f, 0.9f,
             0.5f,  -0.8f, 1f, 0.9f,
             0.5f,   0.8f, 1f, 0.1f,
            -0.5f,   0.8f, 0f, 0.1f,
            -0.5f,  -0.8f, 0f, 0.9f,
    };


    private final VertexArray vertexArray;

    public Tables() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureShaderProgram) {
        vertexArray.setVertexArrtibPointer(0
        ,textureShaderProgram.getPositionAttributeLocation()
        ,POSITION_COMPONENT_CONUT
        ,STRIDE);

        vertexArray.setVertexArrtibPointer(POSITION_COMPONENT_CONUT
                ,textureShaderProgram.getTextureCoordinatesAttributeLocation()
                ,TEXTURE_COORDINATES_COMPONENT_CONUT
                ,STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0 ,6);
    }

}
