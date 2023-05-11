package com.plzgpt.lenzhub.opengl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.effect.Effect
import android.media.effect.EffectContext
import android.media.effect.EffectFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.util.Log
import com.plzgpt.lenzhub.R
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class PhotoFilter(context: Context, photo: Bitmap): GLSurfaceView.Renderer {

    private var photoWidth = context.resources.displayMetrics.widthPixels
    private var photoHeight = context.resources.displayMetrics.widthPixels

    private var resizedPhoto = Bitmap.createScaledBitmap(photo, photoWidth, photoHeight, false)

    private val textures = IntArray(10)
    private lateinit var square: Square

    private lateinit var effectFactory: EffectFactory
    private lateinit var brightnessEffect: Effect
    private lateinit var contrastEffect: Effect

    private var filterValueMap = mutableMapOf<String, Float>()

    private fun generateSquare() {
        GLES20.glGenTextures(10, textures, 0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_S,
            GLES20.GL_CLAMP_TO_EDGE
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_T,
            GLES20.GL_CLAMP_TO_EDGE
        )

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, resizedPhoto, 0)
        square = Square()
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        effectFactory = EffectContext.createWithCurrentGlContext().factory
        brightnessEffect = effectFactory.createEffect(EffectFactory.EFFECT_BRIGHTNESS)
        contrastEffect = effectFactory.createEffect(EffectFactory.EFFECT_CONTRAST)

        filterValueMap["Brightness"] = 1f
        filterValueMap["Contrast"] = 1f
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0,0,photoWidth, photoHeight)
        GLES20.glClearColor(0f,0f,0f,1f)
        generateSquare()
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d("PhotoFilter", "onDrawFrame")

        brightnessEffect.setParameter("brightness", filterValueMap["Brightness"]!!)
        brightnessEffect.apply(textures[0], photoWidth, photoHeight, textures[1])

        contrastEffect.setParameter("contrast", filterValueMap["Contrast"]!!)
        contrastEffect.apply(textures[1], photoWidth, photoHeight, textures[2])

        square.draw(textures[2])
    }

    fun setFilterValue(filterType: String, value: Float) {
        filterValueMap[filterType] = value
    }
}