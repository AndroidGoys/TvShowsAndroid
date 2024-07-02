package good.damn.tvlist.views.animatable.vectors

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Animatable
import good.damn.tvlist.views.animatable.AnimatableVector
import good.damn.tvlist.views.animatable.BoundVector

class MediaVector(
    x: Int,
    y: Int,
    width: Int,
    height: Int
): BoundVector(
    x,
    y,
    width,
    height
) {

    private val mPaintAccent = Paint()
    private var mRadius = 1f

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.color = 0xffaaaaaa.toInt()
        mRadius = height * 0.15f

        mPaintAccent.style = Paint.Style.STROKE
        mPaintAccent.strokeCap = Paint.Cap.ROUND
        mPaintAccent.color = 0xffffffff.toInt()
        mPaintAccent.strokeWidth = width * 0.1f
    }

    override fun draw(
        canvas: Canvas
    ) {
        canvas.drawRoundRect(
            mRectVector,
            mRadius,
            mRadius,
            mPaint
        )

        (y + height * 0.25f).let { lineEndY ->
            canvas.drawLine(
                x + width * 0.15f,
                y.toFloat(),
                x + width * 0.25f,
                lineEndY,
                mPaintAccent
            )

            canvas.drawLine(
                x + width * 0.35f,
                y.toFloat(),
                x + width * 0.45f,
                lineEndY,
                mPaintAccent
            )

            canvas.drawLine(
                x + width * 0.55f,
                y.toFloat(),
                x + width * 0.65f,
                lineEndY,
                mPaintAccent
            )
        }
    }
}