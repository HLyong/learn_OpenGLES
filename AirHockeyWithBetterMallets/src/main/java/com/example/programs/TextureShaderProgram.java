package com.example.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.example.airhockeywithbettermallets.R;
import com.example.util.TextResourceReader;

public class TextureShaderProgram extends ShaderProgram {
    //Uniform Location
    private int uMatrixLocation;
    private int uTextureUnitLocation;

    private int a_TextureCoordinatesLocation;
    private int a_PositionLocation;

    public TextureShaderProgram(Context context) {

        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);
        a_PositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        a_TextureCoordinatesLocation = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    //传入矩阵和纹理给Uniform
    public void setUniform(float[] matrix, int textureId) {

        //传入矩阵
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix,0);

        //把活动的纹理单元设置为纹理单元0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        //绑定这个纹理单元
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        //把被选定的纹理单元传递给片段着色器中的u_TextureUnit
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getTextureCoordinatesAttributeLocation() {
        return a_TextureCoordinatesLocation;
    }

    public int getPositionAttributeLocation() {
        return a_PositionLocation;
    }
}
