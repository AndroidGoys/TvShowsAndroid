package good.damn.tvlist.views.animatable

import android.graphics.RectF

abstract class BoundVector(
    x: Int,
    y: Int,
    width: Int,
    height: Int
): AnimatableVector(
    x,
    y,
    width,
    height
) {
    protected val mRectVector = RectF()

    init {
        mRectVector.left = x.toFloat()
        mRectVector.top = y.toFloat()

        mRectVector.right = (x + width).toFloat()
        mRectVector.bottom = (y + height).toFloat()
    }

    override var x: Int
        get() = super.x
        set(value) {
            super.x = value
            mRectVector.left = value.toFloat()
            width = width
        }

    override var y: Int
        get() = super.y
        set(value) {
            super.y = value
            mRectVector.top = value.toFloat()
            height = height
        }

    override var width: Int
        get() = super.width
        set(value) {
            super.width = value
            mRectVector.right = (x + value).toFloat()
        }

    override var height: Int
        get() = super.height
        set(value) {
            super.height = value
            mRectVector.bottom = (y + value).toFloat()
        }

}