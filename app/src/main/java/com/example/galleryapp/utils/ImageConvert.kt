package com.example.galleryapp.utils


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class ImageConverter {
    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            return toByteArray()
        }
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArray = convertBitmapToByteArray(bitmap)
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun uriToBase64(context: Context, uri: Uri): String? {
        val bitmap = uriToBitmap(context, uri)
        return bitmap?.let { bitmapToBase64(it) }
    }

    fun base64ToBitmap(base64String: String): Bitmap {
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        val inputStream = ByteArrayInputStream(imageBytes)
        return BitmapFactory.decodeStream(inputStream)
    }

    fun base64ToImageBitmap(base64String: String): ImageBitmap? {
        return try {
            val bitmap = base64ToBitmap(base64String)
            bitmap.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null // or a default ImageBitmap in case of failure
        }
    }
}