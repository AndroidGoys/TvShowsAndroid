package good.damn.tvlist.views

import android.content.Context
import android.graphics.Canvas
import android.view.View
import good.damn.tvlist.App
import good.damn.tvlist.R

class RateView(
    context: Context
): View(
    context
) {

    private val mStars = Array(5) {
        Star()
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(
            changed,
            left,
            top,
            right,
            bottom
        )

        val margin = width / 5 - height

        var xOffset = 0

        mStars.forEach {
            it.setBounds(
                xOffset,
                0,
                height,
                height
            )
            xOffset += margin + height
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        mStars.forEach {
            it.draw(canvas)
        }
    }

}