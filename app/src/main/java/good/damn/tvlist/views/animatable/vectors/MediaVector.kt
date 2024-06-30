package good.damn.tvlist.views.animatable.vectors

import android.graphics.Canvas
import android.graphics.Paint
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

    private val mPaint = Paint()
    init {
        mPaint.style = Paint.Style.FILL
        mPaint.color = 0xffaaaaaa.toInt()
    }

    override fun draw(
        canvas: Canvas
    ) {
        canvas.drawRect(
            mRectVector,
            mPaint
        )
    }
}