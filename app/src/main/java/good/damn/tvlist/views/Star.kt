package good.damn.tvlist.views

import android.graphics.Canvas
import good.damn.tvlist.App
import good.damn.tvlist.R

class Star {

    private val mDrawable = App.drawable(
        R.drawable.ic_star_out
    )

    fun draw(
        canvas: Canvas
    ) {
        mDrawable?.draw(
            canvas
        )
    }

    fun setBounds(
        x: Int,
        y: Int,
        width: Int,
        height: Int
    ) {
        mDrawable?.setBounds(
            x,
            y,
            x+width,
            y+height
        )
    }

}