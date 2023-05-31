package com.plzgpt.lenzhub.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


object FileUtil {
    // 임시 파일 생성
    fun createTempFile(context: Context, fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    // 파일 내용 스트림 복사
    fun copyToFile(context: Context, uri: Uri, file: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(4 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }

        outputStream.flush()
    }
}

//URI를 파일로 변환시켜주는 함수를 담는 object
object UriUtil {
    // URI -> File
    fun toFile(context: Context, uri: Uri): File {
        val fileName = getFileName(context, uri)

        val file = FileUtil.createTempFile(context, fileName)
        FileUtil.copyToFile(context, uri, file)

//        val bmp = BitmapFactory.decodeFile(file.absolutePath)
//        //압축하는 함수
//        bitmapToFile(bmp, file)

        return File(file.absolutePath)
    }

    // get file name & extension
    fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)!!.split("/").last()

        return "$name.$ext"
    }

    fun bitmapToFile(bitmap: Bitmap, file: File): File {
        var out: OutputStream? = null

        Log.d("upload-result", "용량 : " + file.length())
        try{
            file.createNewFile()
            out = FileOutputStream(file)

            //압축 비율(quality조절)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 63, out)
        }finally{
            out?.close()
        }
        return file
    }
}