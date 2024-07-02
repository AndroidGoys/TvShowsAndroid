package good.damn.tvlist.views.animatable

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt

abstract class AnimatableVector(
    open var x: Int = 0,
    open var y: Int = 0,
    open var width: Int,
    open var height: Int
) {

    @ColorInt
    open var color: Int = 0
        set(v) {
            field = v
            mPaint.color = v
        }

    protected val mPaint = Paint()

    abstract fun draw(
        canvas: Canvas
    )

}