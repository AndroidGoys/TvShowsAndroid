package good.damn.tvlist.views.animatable.vectors

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import good.damn.tvlist.views.animatable.AnimatableVector
import good.damn.tvlist.views.animatable.BoundVector

class PlayVector(
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

    private val mPaint = Paint()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.color = 0xffff0000.toInt()
        mPaint.strokeWidth = height * 0.1f
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun draw(
        canvas: Canvas
    ) {

        // Right
        canvas.drawArc(
            mRectVector,
            -60f,
            120f,
            false,
            mPaint
        )

        // Left bottom
        canvas.drawArc(
            mRectVector,
            94.7f,
            28f,
            false,
            mPaint
        )


        canvas.drawArc(
            mRectVector,
            167.3f,
            28f,
            false,
            mPaint
        )


        canvas.drawArc(
            mRectVector,
            237.3f,
            28f,
            false,
            mPaint
        )

    }
}