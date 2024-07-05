package good.damn.tvlist.cache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class CacheBitmap {
    companion object {
        private const val TAG = "CacheBitmap"
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
            cacheDirApp: File
        ) {
            val cacheFile = cacheBitmapFile(
                name,
                cacheDirApp
            )

            Log.d(TAG, "cache: CACHE_FILE: $cacheFile")
            
            if (!cacheFile.exists() && cacheFile.createNewFile()) {
                Log.d(TAG, "cache: cache file created")
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
        ) = CacheFile.cacheFile(
            cacheDirApp,
            "bitmaps",
            name.hashCode().toString()
        )
    }
}