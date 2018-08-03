package com.example.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.example.util.ShaderHelper;
import com.example.util.TextResourceReader;
import com.example.util.TextureHelper;

public class ShaderProgram {
    //uniform constants
    public static final String U_MATRIX = "u_Matrix";
    public static final String U_TEXTURE_UNIT = "u_TextureUnit";

    //Attribute constants
    public static final String A_POSITION = "a_Position";
    public static final String A_COLOR = "a_Color";
    public static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    //Shader program
    protected final int program;
    protected ShaderProgram(Context context, int vertexResId, int fragmentResId) {
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFormResource(context, vertexResId),
                TextResourceReader.readTextFileFormResource(context, fragmentResId)
        );

    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}
