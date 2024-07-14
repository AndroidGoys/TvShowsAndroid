package good.damn.tvlist.views.rate

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.tvlist.extensions.checkBounds
import good.damn.tvlist.views.Star

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

    private var mRate: Byte = 0

    private var mX = 0
    private var mY = 0

    var onRateClickListener: OnRateClickListener? = null


    init {
        super.setOnTouchListener(
            this
        )
    }

    override fun setOnTouchListener(
        l: OnTouchListener?
    ) {
        super.setOnTouchListener(
            if (l == null) null
            else this
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

        var xOffset = mX + margin / 2

        mStars.forEach {
            it.setBounds(
                xOffset,
                mY,
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

        if (event.action != MotionEvent.ACTION_UP) {
            return true
        }

        if (checkBounds(
                event.x,
                event.y
            )) {
            for (i in mStars.indices) {
                val s = mStars[i].bounds
                    ?: return false
                if (!(event.x <= s.left || event.x >= s.right)) {
                    mRate = (i + 1).toByte()
                    onRateClickListener?.onClickRate(
                        mRate
                    )
                    invalidate()
                    return true
                }
            }
        }
        return true
    }

    fun setPosition(
        x: Int,
        y: Int
    ) {
        mX = x
        mY = y
    }

    fun setStarsRate(
        grade: Byte
    ) {
        mRate = grade
        invalidate()
    }

}