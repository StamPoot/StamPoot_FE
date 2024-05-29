package project.android.footstamp.data.util

import android.graphics.Bitmap
import kotlin.math.sqrt

object BitmapManager {
    private const val SERVER_IMAGE_SIZE = 200000

    fun bitmapResize1MB(bitmap: Bitmap): Bitmap {
        if (bitmap.byteCount <= SERVER_IMAGE_SIZE) return bitmap

        val width = bitmap.width
        val height = bitmap.height
        val ratio = width.toFloat() / height.toFloat()

        val newWidth = sqrt(SERVER_IMAGE_SIZE.toFloat() * ratio).toInt()
        val newHeight = (newWidth / ratio).toInt()


        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }
}