package com.plzgpt.lenzhub.opengl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.effect.Effect
import android.media.effect.EffectContext
import android.media.effect.EffectFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class PhotoFilter(context: Context, photo: Bitmap): GLSurfaceView.Renderer {

    // create singleton
    companion object {
        private lateinit var instance: PhotoFilter
        fun getInstance(context: Context, photo: Bitmap): PhotoFilter {
            if (!::instance.isInitialized) {
                instance = PhotoFilter(context, photo)
            }
            return instance
        }
    }
    private var originalPhoto = photo
    private var photoWidth = context.resources.displayMetrics.widthPixels
    private var photoHeight = context.resources.displayMetrics.widthPixels

    private var resizedPhoto = Bitmap.createScaledBitmap(photo, photoWidth, photoHeight, false)

    private val textures = IntArray(10)
    private lateinit var square: Square
    private lateinit var effectFactory: EffectFactory

    data class Effects(
        val name: String,
        val effect: Effect,
        val key: String,
        var value: Float
    )

    private lateinit var effects: List<Effects>

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
        Log.d("PhotoFilter", "onSurfaceCreated")
        effectFactory = EffectContext.createWithCurrentGlContext().factory

        effects = listOf(
            Effects("Brightness", effectFactory.createEffect(EffectFactory.EFFECT_BRIGHTNESS), "brightness", 1f),
            Effects("Contrast", effectFactory.createEffect(EffectFactory.EFFECT_CONTRAST), "contrast", 1f),
            Effects("BackLight", effectFactory.createEffect(EffectFactory.EFFECT_FILLLIGHT), "strength", 0f),
            Effects("Distortion", effectFactory.createEffect(EffectFactory.EFFECT_FISHEYE), "scale", 0f),
            Effects("Grain", effectFactory.createEffect(EffectFactory.EFFECT_GRAIN), "strength", 0f),
            Effects("Saturate", effectFactory.createEffect(EffectFactory.EFFECT_SATURATE), "scale", 0f),
            Effects("Sharpen", effectFactory.createEffect(EffectFactory.EFFECT_SHARPEN), "scale", 0f),
            Effects("Temperature", effectFactory.createEffect(EffectFactory.EFFECT_TEMPERATURE), "scale", 0.5f),
        )
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d("PhotoFilter", "onSurfaceChanged")
        GLES20.glViewport(0,0,photoWidth, photoHeight)
        GLES20.glClearColor(0f,0f,0f,1f)
        generateSquare()
    }

    override fun onDrawFrame(gl: GL10?) {
        for(i in 0 .. 7) {
            effects[i].effect.setParameter(effects[i].key, effects[i].value)
            effects[i].effect.apply(textures[i], photoWidth, photoHeight, textures[i+1])
        }

        square.draw(textures[8])
    }

    fun setFilterValue(name: String, value: Float) {
        for(i in 0 .. 7) {
            if(effects[i].name == name) {
                effects[i].value = value
            }
        }
    }

    fun getModifiedPhoto(): Bitmap {
        var outputStream: FileOutputStream? = null
        val dir = File(Environment.getExternalStorageDirectory().absolutePath + "/lenzhub")
        val fileName = System.currentTimeMillis().toString() + ".jpeg"
        val file = File(dir, fileName)

        try {
            outputStream = FileOutputStream(file)
            resizedPhoto.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }

        return resizedPhoto
    }
}