package com.plzgpt.lenzhub.ui.screen.lenz

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File
import java.io.FileOutputStream

fun composableToBitmap(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val background = view.background
    if (background != null) {
        background.draw(canvas)
    } else {
        canvas.drawColor(Color.WHITE)
    }
    view.draw(canvas)
    return bitmap
}

//@Composable
//fun CaptureAndSaveComposable() {
//    val context = LocalContext.current
//    val composableView = rememberViewWithSize()
//
//    AndroidView(
//        factory = { composableView },
//        modifier = Modifier.fillMaxSize()
//    ) {
//        // Place your specific composable inside the AndroidView
//        MyComposable()
//    }
//
//    Button(
//        onClick = {
//            val bitmap = composableToBitmap(composableView)
//            val filePath = "path/to/save/bitmap.png"
//            val outputStream = FileOutputStream(File(filePath))
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//            outputStream.close()
//            Toast.makeText(context, "Bitmap saved!", Toast.LENGTH_SHORT).show()
//        },
//        modifier = Modifier.padding(16.dp)
//    ) {
//        Text("Capture and Save")
//    }
//}
//
//@Composable
//fun MyComposable() {
//    TODO("Not yet implemented")
//}
