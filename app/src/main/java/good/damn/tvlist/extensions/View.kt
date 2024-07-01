package good.damn.tvlist.extensions

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import good.damn.tvlist.App


fun View.widthParams() = layoutParams
    .width

fun View.heightParams() = layoutParams
    .height

fun View.bottomMargin() = frameParams()
    .bottomMargin

fun View.generateId() {
    id = View.generateViewId()
}

fun View.setBackgroundColorId(
    @ColorRes id: Int
) {
    setBackgroundColor(
        App.color(
            id
        )
    )
}

fun View.size(
    width: Int,
    height: Int
) {
    layoutParams = ViewGroup.LayoutParams(
        width,
        height
    )
}

fun View.boundsLinear(
    gravity: Int = Gravity.START,
    width: Int = -2,
    height: Int = -2,
    left: Float = 0f,
    right: Float = 0f,
    top: Float = 0f,
    bottom: Float = 0f
) {
    LinearLayout.LayoutParams(
        width,
        height
    ).let {
        it.gravity = gravity
        it.leftMargin = left.toInt()
        it.rightMargin = right.toInt()
        it.topMargin = top.toInt()
        it.bottomMargin = bottom.toInt()
        layoutParams = it
    }
}


fun View.boundsFrame(
    gravity: Int = Gravity.START,
    width: Int = -2,
    height: Int = -2,
    left: Float = 0f,
    right: Float = 0f,
    top: Float = 0f,
    bottom: Float = 0f
) {
    FrameLayout.LayoutParams(
        width,
        height
    ).let {
        it.gravity = gravity
        it.leftMargin = left.toInt()
        it.rightMargin = right.toInt()
        it.topMargin = top.toInt()
        it.bottomMargin = bottom.toInt()
        layoutParams = it
    }
}

private fun View.frameParams() = (
    layoutParams as FrameLayout.LayoutParams
)