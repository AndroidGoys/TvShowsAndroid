package good.damn.tvlist.views.animatable.vectors

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import androidx.annotation.ColorInt
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

    @ColorInt
    var color: Int = 0
        set(v) {
            field = v
            mPaint.color = v
            mPaintFillPlay.color = v
        }

    private val mPaint = Paint()
    private val mPaintFillPlay = Paint()

    private val mPath = Path()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.color = 0xffff0000.toInt()
        mPaint.strokeWidth = height * 0.1f
        mPaint.strokeCap = Paint.Cap.ROUND

        mPaintFillPlay.style = Paint.Style.STROKE
        mPaintFillPlay.color = mPaint.color
        mPaintFillPlay.strokeCap = Paint.Cap.ROUND
        mPaintFillPlay.strokeWidth = height * 0.2f

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

        // Triangle
        mPath.reset()
        mPath.moveTo(
            x + width * 0.45f,
            y + height * 0.45f
        )
        mPath.lineTo(
            x + width * 0.53f,
            y + height * 0.5f,
        )
        mPath.lineTo(
            x + width * 0.45f,
            y + height * 0.55f
        )
        mPath.close()

        canvas.drawPath(
            mPath,
            mPaintFillPlay
        )

    }
}