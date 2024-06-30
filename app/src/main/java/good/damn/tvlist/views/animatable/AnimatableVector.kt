package good.damn.tvlist.views.animatable

import android.graphics.Canvas

abstract class AnimatableVector(
    open var x: Int,
    open var y: Int,
    open var width: Int,
    open var height: Int
) {
    abstract fun draw(
        canvas: Canvas
    )

}