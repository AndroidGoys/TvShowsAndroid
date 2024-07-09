package good.damn.tvlist.views

import android.graphics.Canvas
import android.graphics.Rect
import good.damn.tvlist.App
import good.damn.tvlist.R

class Star {

    private val mDrawable = App.drawable(
        R.drawable.ic_star_out
    )

    private val mDrawableFill = App.drawable(
        R.drawable.ic_star_fill_lime
    )

    var bounds: Rect? = null
        private set
        get() = mDrawable?.bounds

    fun draw(
        canvas: Canvas
    ) {
        mDrawable?.draw(
            canvas
        )
    }

    fun drawFill(
        canvas: Canvas
    ) {
        mDrawableFill?.draw(
            canvas
        )
    }

    fun setBounds(
        x: Int,
        y: Int,
        width: Int,
        height: Int
    ) {
        mDrawable?.let {
            it.setBounds(
                x,
                y,
                x+width,
                y+height
            )
            mDrawableFill?.bounds = it.bounds
        }
    }

}