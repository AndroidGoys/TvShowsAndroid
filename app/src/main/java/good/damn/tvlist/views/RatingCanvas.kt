package good.damn.tvlist.views

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import androidx.annotation.ColorInt

class RatingCanvas {

    var cornerRadius = 5f
    var rating = 4.7f
        set(v) {
            field = (v * 10).toInt() / 10f
            mRatingStr = field.toString()
        }

    @ColorInt
    var textColor: Int = 0
        set(v) {
            field = v
            mPaintText.color = v
        }

    var textSize = 4f
        set(v) {
            field = v
            mPaintText.textSize = v
        }

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintText.typeface = v
        }

    private val mPaint = Paint()
    private val mPaintText = Paint()

    private val mClipPath = Path()
    private val mRect = RectF()

    private var mTextX = 0f
    private var mTextY = 0f

    private var mRatingStr = "0.0"

    init {
        mPaint.isDither = true
    }

    fun layout(
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        mRect.top = x
        mRect.left = y
        mRect.right = width + x
        mRect.bottom = height + y
        val shader = LinearGradient(
            mRect.top,
            mRect.left,
            mRect.right,
            mRect.bottom,
            intArrayOf(
                detectColor(
                    rate = rating.toInt()
                ),
                detectColor(
                    rate = ((rating - rating.toInt()) * 10f).toInt(),
                    times = 2
                )
            ),
            floatArrayOf(
                0.0f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )

        mTextX = (
            width - mPaintText.measureText(mRatingStr)
        ) * 0.5f + mRect.left

        mTextY = (
            height + mPaintText.textSize
        ) * 0.4f + mRect.top

        mPaint.shader = shader
    }

    fun draw(
        canvas: Canvas
    ) {
        mClipPath.reset()
        mClipPath.addRoundRect(
            mRect,
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
        mClipPath.close()
        canvas.clipPath(
            mClipPath
        )
        canvas.drawPaint(
            mPaint
        )
        canvas.drawText(
            mRatingStr,
            mTextX,
            mTextY,
            mPaintText
        )
    }

    companion object {
        @ColorInt
        private fun detectColor(
            rate: Int,
            times: Int = 1
        ) = when(rate) {
            in 0..1*times -> 0xffFC1304.toInt()
            in 2*times..3*times -> 0xffE4B303.toInt()
            else -> 0xff36A502.toInt()
        }
    }

}