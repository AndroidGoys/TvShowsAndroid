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
import kotlin.math.abs

class NetworkBitmap {
    companion object {
        private const val TAG = "NetworkBitmap"
        
        fun loadFromNetwork(
            url: String,
            cacheDirApp: File,
            dirName: String,
            viewWidth: Int,
            viewHeight: Int,
            completion: (Bitmap) -> Unit
        ) = CoroutineScope(
            Dispatchers.IO
        ).launch {

            val cachedBitmap = CacheBitmap.loadFromCache(
                url,
                dirName,
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
                val connection = URL(
                    url
                ).openConnection()

                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.connect()

                connection.getInputStream()
            } catch (e: Exception) {
                Log.d(TAG, "loadFromNetwork: ERROR: ${e.message}")
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

                val bitmapWidth = it.width
                val bitmapHeight = it.height

                val correctedBitmap: Bitmap

                if (bitmapWidth == bitmapHeight
                    && viewWidth == viewHeight
                ) {
                    correctedBitmap = Bitmap.createScaledBitmap(
                        it,
                        viewWidth,
                        viewHeight,
                        false
                    )
                } else { // !(dh < 0 & dw < 0)
                    val dh = viewHeight - bitmapHeight
                    val dw = viewWidth - bitmapWidth

                    val kw = bitmapWidth.toFloat() / bitmapHeight
                    val kh = bitmapHeight.toFloat() / bitmapWidth

                    val dstWidth: Int
                    val dstHeight: Int

                    if (dh > dw) {
                        dstWidth = (bitmapWidth + dh * kw).toInt()
                        dstHeight = viewHeight
                    } else {
                        dstWidth = viewWidth
                        dstHeight = (bitmapHeight + dw * kh).toInt()
                    }

                    val scaledUpBitmap = Bitmap.createScaledBitmap(
                        it,
                        dstWidth,
                        dstHeight,
                        false
                    )

                    val x = (dstWidth - viewWidth) / 2
                    val y = (dstHeight - viewHeight) / 2

                    correctedBitmap = Bitmap.createBitmap(
                        scaledUpBitmap,
                        x,
                        y,
                        viewWidth,
                        viewHeight
                    )
                }



                CacheBitmap.cache(
                    correctedBitmap,
                    url,
                    dirName,
                    cacheDirApp
                )

                if (cachedBitmap != null) {
                    return@launch
                }
                App.ui {
                    completion(correctedBitmap)
                }

            }
        }
    }
}