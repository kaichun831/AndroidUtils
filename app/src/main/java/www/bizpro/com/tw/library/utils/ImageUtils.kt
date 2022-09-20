package www.bizpro.com.tw.library.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.*
import java.text.DecimalFormat
import kotlin.math.roundToInt

object ImageUtils {
    /**
     *  檔案轉Bitmap
     */
    fun fileToBitmap(file: File): Bitmap? {
        return try {
            val newOpts = BitmapFactory.Options()
            BitmapFactory.decodeFile(file.absolutePath, newOpts)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * 壓縮圖片流程[1]調整壓縮尺寸接近1920*1080
     */
    fun resizeAndCompassToNewFile(file: File,quality:Int): File? {
        val picWidth = 1920
        val picHeight = 1080
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.path, options)
        val iHeight = options.outHeight
        val iWidth = options.outWidth
        if (iHeight > 0 && iWidth > 0) {
            val reqWidth: Int
            val reqHeight: Int
            var inSampleSize = 1
            //判斷是否為長型、寬型
            if (iWidth > iHeight) {
                reqWidth = picWidth
                reqHeight = picHeight
            } else {
                reqWidth = picHeight
                reqHeight = picWidth
            }
            if (iHeight > reqHeight || iWidth > reqWidth) {
                val heightRatio = (iHeight.toFloat() / reqHeight.toFloat()).roundToInt()
                val widthRatio = (iWidth.toFloat() / reqHeight.toFloat()).roundToInt()
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            options.inSampleSize = inSampleSize
            options.inJustDecodeBounds = false
            val bPic = BitmapFactory.decodeFile(file.path, options)
            return bitMapCompressToNewFile(bPic, file,quality)!!
        }
        return null
    }

    /**
     * 壓縮圖片流程[2]調整壓縮品質
     * @param quality 照片壓縮品質 0-100
     */
    private fun bitMapCompressToNewFile(bitmap: Bitmap, file: File,quality:Int): File? {
        val fileInputStream: FileInputStream?
        try {
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)
            Log.d("MobileEnv", "壓縮後圖片寬 : " + bitmap.width)
            Log.d("MobileEnv", "壓縮後圖片高 : " + bitmap.height)
            val bitmapData: ByteArray = bos.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            fileInputStream = FileInputStream(file)
            Log.d("MobileEnv", "壓縮後圖片尺寸 : " + formatFileSize(fileInputStream.available()))
            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    /**
     * 換算檔案大小
     */
    private  fun formatFileSize(size: Int): String {
        val df = DecimalFormat("#.00")
        val wrongSize = "0B"
        return when {
            size <= 0 -> wrongSize
            size < 1024 -> df.format(size.toDouble()).toString() + "B"
            size < 1048576 -> df.format(size.toDouble() / 1024).toString() + "KB"
            size < 1073741824 -> df.format(size.toDouble() / 1048576).toString() + "MB"
            else -> df.format(size.toDouble() / 1073741824).toString() + "GB"
        }
    }
}