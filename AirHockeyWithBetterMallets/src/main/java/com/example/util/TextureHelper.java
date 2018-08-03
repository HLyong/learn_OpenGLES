package com.example.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.example.airhockeywithbettermallets.AirHockeyWithBetterMallets;

public class TextureHelper {

    public static final String TAG = AirHockeyWithBetterMallets.TAG_TITLE + TextureHelper.class.getSimpleName();
    public static int loadTexture(Context context, int resId) {
        int[] textureObjectIds = new int[1];

        GLES20.glGenTextures(1, textureObjectIds, 0);
        if (textureObjectIds[0] == 0) {
            Log.e(TAG, "loadTexture: load fail id == 0" );
            return 0;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        if (bitmap == null) {
            Log.i(TAG, "loadTexture: load fail bitmap is null");
            GLES20.glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }
         /*
        纹理过滤模式：
        GL_NEAREST 最近邻过滤
        GL_NEAREST_MIPMAP_NEAREST 使用MIP贴图的最近邻过滤
        GL_NEAREST_MIPMAP_LINEAR 使用MIP贴图级别之间插值的最近邻过滤
        GL_LINEAR 双线性过滤
        GL_LINEAR_MIPMAP_NEAREST 使用MIP贴图的双线性过滤
        GL_LINEAR_MIPMAP_LINEAR 三线性过滤(使用MIP贴图级别之间插值的双线性过滤)
        GL_NEAREST和GL_LINEAR可用于缩小放大两种情况，其它只能用于缩小
         */
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0]);
        //设置纹理过滤参数。
        //缩小（GL_TEXTURE_MIN_FILTER）时，使用三线性过滤（GL_LINEAR_MIPMAP_LINEAR）
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        //放大（GL_TEXTURE_MAG_FILTER）时，采用双线性过滤（GL_LINEAR）
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        bitmap = null;
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        return textureObjectIds[0];
    }


}
