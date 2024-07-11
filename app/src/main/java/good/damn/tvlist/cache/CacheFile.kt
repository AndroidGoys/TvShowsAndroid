package good.damn.tvlist.cache

import android.util.Log
import java.io.File

class CacheFile {

    companion object {
        private const val TAG = "FileCache"

        fun cacheFile(
            cacheDirApp: File,
            dirName: String,
            fileName: String
        ) = File(
            "${cacheDir(cacheDirApp, dirName)}/$fileName"
        )

        fun cacheDir(
            cacheDirApp: File,
            dirName: String,
        ): File {
            val cacheDir = File(
                "${cacheDirApp}/$dirName"
            )

            if (!cacheDir.exists() && cacheDir.mkdirs()) {
                Log.d(TAG, "cacheDir: bitmap cache dir is created")
            }

            return cacheDir
        }
    }

}