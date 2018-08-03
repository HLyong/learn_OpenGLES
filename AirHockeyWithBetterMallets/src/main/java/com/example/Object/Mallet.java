package com.example.Object;

import com.example.data.VertexArray;
import com.example.programs.ColorShaderProgram;
import com.example.util.Geometry;

import java.util.List;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

public class Mallet {
    public static final int POSITION_COMPONET_COUNT = 3;

    public float radius, height;

    private VertexArray vertexData;

    private List<ObjectBuilder.DrawCommand> drawCommandList;

    public Mallet(float radius, float height, int numsPointAroundMallet) {
        ObjectBuilder.GeneratedData generatedData =  ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0f,0f,0f)
                        ,radius,height), numsPointAroundMallet);

        this.radius = radius;
        this.height = height;
        vertexData = new VertexArray(generatedData.vertexData);
        drawCommandList = generatedData.drawCommandList;
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexData.setVertexArrtibPointer(0, colorShaderProgram.getPositionAttributeLocation(), POSITION_COMPONET_COUNT, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand d : drawCommandList) {
            d.draw();
        }
    }
}
