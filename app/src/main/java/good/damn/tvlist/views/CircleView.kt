package good.damn.tvlist.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.ColorInt

class CircleView(
    context: Context
): AnimatableView(
    context
) {

    var circleX = 0.5f
        set(v) {
            field = v
            if (isLaidOut) {
                mCircleX = width * v
            }
        }

    var circleY = 0.5f
        set(v) {
            field = v
            if (isLaidOut) {
                mCircleY = height * v
            }
        }

    @ColorInt
    var circleColor: Int = 0xffff0000.toInt()
        set(v) {
            field = v
            mPaint.setColor(
                circleColor
            )
        }

    var circleRadius = 15f

    private var mPaint = Paint()

    private var mCircleX = 0f
    private var mCircleY = 0f

    init {
        mPaint.style = Paint.Style.FILL
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mCircleX = width * circleX
        mCircleY = height * circleY
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)
        canvas.drawCircle(
            mCircleX,
            mCircleY,
            circleRadius,
            mPaint
        )
    }
}