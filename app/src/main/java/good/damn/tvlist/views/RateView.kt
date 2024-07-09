package good.damn.tvlist.views

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.tvlist.extensions.checkBounds

class RateView(
    context: Context
): View(
    context
), View.OnTouchListener {

    companion object {
        private const val TAG = "RateView"
    }

    private val mStars = Array(5) {
        Star()
    }

    private var mRate = 0

    init {
        super.setOnTouchListener(
            this
        )
    }

    override fun setOnTouchListener(
        l: OnTouchListener?
    ) {
        super.setOnTouchListener(
            this
        )
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

        for (i in mRate..<mStars.size) {
            mStars[i].draw(
                canvas
            )
        }

        for (i in 0..<mRate) {
            mStars[i].drawFill(
                canvas
            )
        }
    }

    override fun onTouch(
        v: View?,
        event: MotionEvent?
    ): Boolean {
        if (event == null || v == null) {
            return false
        }

        if (event.action == MotionEvent.ACTION_UP) {
            if (checkBounds(
                event.x,
                event.y
            )) {
                for (i in mStars.indices) {
                    val s = mStars[i].bounds
                        ?: return false
                    if (!(event.x <= s.left || event.x >= s.right)) {
                        Log.d(TAG, "onTouch: TOUCHED: $i")
                        mRate = i + 1
                        invalidate()
                        return true
                    }
                }

            }
        }

        return true
    }

}