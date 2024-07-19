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
        private const val DIR_ORIGINAL = "original/bitmap"

        fun loadFromNetwork(
            url: String,
            cacheDirApp: File,
            dirName: String,
            viewWidth: Int,
            viewHeight: Int,
            completion: (Bitmap) -> Unit
        ) = App.IO.launch {
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
                val aspectedBitmap = aspectedBitmap(
                    cachedOriginalBitmap,
                    viewWidth,
                    viewHeight
                )

                CacheBitmap.cache(
                    aspectedBitmap,
                    url,
                    dirName,
                    cacheDirApp
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

                CacheBitmap.cache(
                    it,
                    url,
                    DIR_ORIGINAL,
                    cacheDirApp
                )

                val aspectedBitmap = aspectedBitmap(
                    it,
                    viewWidth,
                    viewHeight
                )

                CacheBitmap.cache(
                    aspectedBitmap,
                    url,
                    dirName,
                    cacheDirApp
                )

                App.ui {
                    completion(aspectedBitmap)
                }

            }
        }

        private fun aspectedBitmap(
            originBitmap: Bitmap,
            viewWidth: Int,
            viewHeight: Int
        ): Bitmap {
            val bitmapWidth = originBitmap.width
            val bitmapHeight = originBitmap.height

            if (bitmapWidth == bitmapHeight
                && viewWidth == viewHeight
            ) {
                return Bitmap.createScaledBitmap(
                    originBitmap,
                    viewWidth,
                    viewHeight,
                    false
                )
            }

            // !(dh < 0 & dw < 0)
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
                originBitmap,
                dstWidth,
                dstHeight,
                false
            )

            var x = (dstWidth - viewWidth) / 2
            var y = (dstHeight - viewHeight) / 2

            var finalWidth = viewWidth
            var finalHeight = viewHeight

            if (x < 0) {
                x = 0
            }

            if (y < 0) {
                y = 0
            }

            if (finalWidth > dstWidth) {
                finalWidth = dstWidth
            }

            if (finalHeight > dstHeight) {
                finalHeight = dstHeight
            }

            return Bitmap.createBitmap(
                scaledUpBitmap,
                x,
                y,
                finalWidth,
                finalHeight
            )
        }
    }
}