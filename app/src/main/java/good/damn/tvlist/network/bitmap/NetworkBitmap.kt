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
                URL(
                    url
                ).openStream()
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

                //if (bitmapWidth == bitmapHeight) {
                    correctedBitmap = Bitmap.createScaledBitmap(
                        it,
                        viewWidth,
                        viewHeight,
                        false
                    )
                /*} else {
                    val dh = viewHeight - bitmapHeight
                    val dw = viewWidth - bitmapWidth

                    val dstWidth: Int
                    val dstHeight: Int

                    if (dh > dw) {
                        dstWidth = bitmapWidth + dh
                        dstHeight = viewHeight
                    } else {
                        dstWidth = viewWidth
                        dstHeight = bitmapHeight + dw
                    }

                    val scaledUpBitmap = Bitmap.createScaledBitmap(
                        it,
                        dstWidth,
                        dstHeight,
                        false
                    )

                    val x: Int
                    val y: Int

                    if (dstWidth > dstHeight) {
                        x = (dstWidth - bitmapWidth) / 2
                        y = 0
                    } else {
                        x = 0
                        y = (dstHeight - bitmapHeight) / 2
                    }

                    Log.d(TAG, "loadFromNetwork: ____________")
                    Log.d(TAG, "loadFromNetwork: BITMAP: $bitmapWidth $bitmapHeight")
                    Log.d(TAG, "loadFromNetwork: DST: $dstWidth $dstHeight")
                    Log.d(TAG, "loadFromNetwork: VIEW: $viewWidth $viewHeight")
                    Log.d(TAG, "loadFromNetwork: XY: $x $y")

                    correctedBitmap = Bitmap.createBitmap(
                        scaledUpBitmap,
                        abs(x),
                        abs(y),
                        viewWidth,
                        viewHeight
                    )
                }*/



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