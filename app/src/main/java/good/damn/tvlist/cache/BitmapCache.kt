package good.damn.tvlist.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class BitmapCache {
    companion object {
        private const val TAG = "BitmapCache"
        fun loadFromCache(
            name: String,
            cacheDirApp: File
        ): Bitmap? {
            val cacheFile = cacheBitmapFile(
                name,
                cacheDirApp
            )

            if (!cacheFile.exists()) {
                return null
            }

            FileInputStream(
                cacheFile
            ).let {
                val bitmap = BitmapFactory.decodeStream(
                    it
                )
                it.close()

                return bitmap
            }
        }

        fun cache(
            bitmap: Bitmap,
            name: String,
            cacheDir: File
        ) {
            val cacheFile = cacheBitmapFile(
                name,
                cacheDir
            )

            if (!cacheFile.exists() && cacheFile.createNewFile()) {
                Log.d(TAG, "loadFromCache: cache file created")
            }

            val fos = FileOutputStream(
                cacheFile
            )
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                fos
            )
            fos.close()
        }


        private fun cacheBitmapFile(
            name: String,
            cacheDirApp: File
        ) = File(
            "${cacheBitmapDir(cacheDirApp)}/${name.hashCode()}"
        )

        private fun cacheBitmapDir(
            cacheDirApp: File
        ): File {
            val cacheDir = File(
                "${cacheDirApp}/bitmaps"
            )

            if (!cacheDir.exists() && cacheDir.mkdir()) {
                Log.d(TAG, "loadFromCache: bitmap cache dir is created")
            }

            return cacheDir
        }
    }
}