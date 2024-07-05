package good.damn.tvlist.network.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.cache.CacheBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL

class NetworkBitmap {
    companion object {
        private const val TAG = "NetworkBitmap"
        
        fun loadFromNetwork(
            url: String,
            cacheDirApp: File,
            widthBitmap: Int,
            heightBitmap: Int,
            completion: (Bitmap) -> Unit
        ) = CoroutineScope(
            Dispatchers.IO
        ).launch {
            val cachedBitmap = CacheBitmap.loadFromCache(
                url,
                cacheDirApp
            )


            Log.d(TAG, "loadFromNetwork: $cachedBitmap")

            if (cachedBitmap != null) {
                App.ui {
                    completion(
                        cachedBitmap
                    )
                }
                return@launch
            }
            
            // Check expiration period?

            val inp = try {
                URL(
                    url
                ).openStream()
            } catch (e: Exception) {
                Log.d(TAG, "loadFromNetwork: ERROR: ${e.message}")
                return@launch
            }


            val option = BitmapFactory.Options()
            option.inSampleSize = if (heightBitmap > widthBitmap)
                heightBitmap / widthBitmap
            else widthBitmap / heightBitmap

            option.outWidth = widthBitmap
            option.outHeight = heightBitmap

            BitmapFactory.decodeStream(
                inp,
                null,
                option
            )?.let {
                inp.close()

                val scaledBitmap = Bitmap.createScaledBitmap(
                    it,
                    widthBitmap,
                    heightBitmap,
                    false
                )

                CacheBitmap.cache(
                    scaledBitmap,
                    url,
                    cacheDirApp
                )

                if (cachedBitmap != null) {
                    return@launch
                }
                App.ui {
                    completion(scaledBitmap)
                }
            }
        }
    }
}