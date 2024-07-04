package good.damn.tvlist.views

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader

class RatingCanvas {

    var cornerRadius = 5f

    private val mPaint = Paint()

    private val mClipPath = Path()
    private val mRect = RectF()

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
                0xff36A502.toInt(),
                0xffD4A019.toInt()
            ),
            floatArrayOf(
                0.0f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )

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
    }

}