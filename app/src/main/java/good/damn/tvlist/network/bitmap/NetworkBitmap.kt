package good.damn.tvlist.network.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.cache.CacheBitmap
import good.damn.tvlist.cache.CacheFile
import good.damn.tvlist.utils.BitmapUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL

class NetworkBitmap {
    companion object {
        private const val TAG = "NetworkBitmap"
        private const val DIR_ORIGINAL = "original/bitmap"

        fun contextCache(
            b: Bitmap,
            url: String,
            cacheDirApp: File,
            dirName: String,
            viewWidth: Int,
            viewHeight: Int,
        ): Bitmap {
            val aspectedBitmap = BitmapUtils.aspectedBitmap(
                b,
                viewWidth,
                viewHeight
            )

            CacheBitmap.cache(
                aspectedBitmap,
                url,
                dirName,
                cacheDirApp
            )

            return aspectedBitmap
        }

        fun cacheOriginal(
            bitmap: Bitmap,
            url: String,
            cacheDirApp: File
        ) {
            CacheBitmap.cache(
                bitmap,
                url,
                DIR_ORIGINAL,
                cacheDirApp
            )
        }

        fun loadFromNetwork(
            url: String,
            cacheDirApp: File,
            dirName: String,
            viewWidth: Int,
            viewHeight: Int,
            completion: (Bitmap?) -> Unit
        ) = App.IMAGE_SCOPE.launch {

            val cachedNativeBitmap = CacheBitmap.loadFromCache(
                url,
                dirName,
                cacheDirApp
            )

            if (cachedNativeBitmap != null) {
                App.ui {
                    completion(
                        cachedNativeBitmap
                    )
                }
                return@launch
            }

            val cachedOriginalBitmap = CacheBitmap.loadFromCache(
                url,
                DIR_ORIGINAL,
                cacheDirApp
            )

            if (cachedOriginalBitmap != null) {
                val aspectedBitmap = contextCache(
                    cachedOriginalBitmap,
                    url,
                    cacheDirApp,
                    dirName,
                    viewWidth,
                    viewHeight
                )

                App.ui {
                    completion(
                        aspectedBitmap
                    )
                }
                return@launch
            }

            // Check expiration period?

            val inp = try {
                val connection = URL(
                    url
                ).openConnection()

                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                connection.connect()

                connection.getInputStream()
            } catch (e: Exception) {
                Log.d(TAG, "loadFromNetwork: ERROR: ${e.message}")
                App.ui {
                    completion(null)
                }
                return@launch
            }


            val option = BitmapFactory.Options()
            option.inSampleSize = if (viewHeight > viewWidth)
                viewHeight / viewWidth
            else viewWidth / viewHeight

            option.outWidth = viewWidth
            option.outHeight = viewHeight

            BitmapFactory.decodeStream(
                inp,
                null,
                option
            )?.let {
                inp.close()

                cacheOriginal(
                    it,
                    url,
                    cacheDirApp
                )

                val aspectedBitmap = contextCache(
                    it,
                    url,
                    cacheDirApp,
                    dirName,
                    viewWidth,
                    viewHeight
                )

                App.ui {
                    completion(aspectedBitmap)
                }
            }
        }
    }
}