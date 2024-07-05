package good.damn.tvlist.cache

import android.util.Log
import good.damn.tvlist.extensions.readBytes
import good.damn.tvlist.extensions.writeBytes
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

class CacheJSON {
    companion object {
        private const val TAG = "CacheJSON"
        fun load(
            name: String,
            cacheDirApp: File
        ): JSONObject? {

            val cacheFile = cacheJsonFile(
                name,
                cacheDirApp
            )

            if (!cacheFile.exists()) {
                return null
            }

            FileInputStream(
                cacheFile
            ).readBytes(
                4096
            ).let {
                val json = String(
                    it,
                    StandardCharsets.UTF_8
                )

                return try {
                    JSONObject(
                        json
                    )
                } catch (e: Exception) {
                    Log.d(TAG, "load: ERROR: ${e.message}")
                    null
                }

            }
        }

        fun cache(
            data: String,
            name: String,
            cacheDirApp: File
        ) {
            val cacheFile = cacheJsonFile(
                name,
                cacheDirApp
            )
            
            if (!cacheFile.exists() && cacheFile.createNewFile()) {
                Log.d(TAG, "cache: cache file ${cacheFile.name} created")
            }

            FileOutputStream(
                cacheFile
            ).writeBytes(
                data.toByteArray(
                    StandardCharsets.UTF_8
                )
            )
        }
        
        fun cacheJsonFile(
            name: String,
            cacheDirApp: File
        ) = CacheFile.cacheFile(
            cacheDirApp,
            "jsons",
            name
        )
    }
}