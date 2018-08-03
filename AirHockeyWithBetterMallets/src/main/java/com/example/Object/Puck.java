package com.example.Object;

import com.example.data.VertexArray;
import com.example.programs.ColorShaderProgram;
import com.example.util.Geometry;

import java.util.List;

public class Puck {
    public static final int POSITION_COMPONET_COUNT = 3;

    public float radius, height;

    private VertexArray vertexData;

    private List<ObjectBuilder.DrawCommand> drawCommandList;

    public Puck(float radius, float height, int numsPointAroundPuck) {
        ObjectBuilder.GeneratedData generatedData =  ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0f,0f,0f)
                ,radius,height), numsPointAroundPuck);

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
