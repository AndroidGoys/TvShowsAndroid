package good.damn.tvlist.utils

import android.graphics.Bitmap

class BitmapUtils {
    companion object {
        fun aspectedBitmap(
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