package good.damn.tvlist.views.buttons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import androidx.annotation.ColorInt
import good.damn.tvlist.views.round.RoundView

class RoundButton(
    context: Context
): RoundView(
    context
) {

    var text: String? = null

    var textSizeFactor: Float = 0.2f

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintText.typeface = v
        }

    @ColorInt
    var textColor: Int = Color.RED
        set(v) {
            field = v
            mPaintText.color = v
        }

    private val mPaintText = Paint()

    private var mTextX = 0f
    private var mTextY = 0f

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

        mPaintText.textSize = height * textSizeFactor
        recalculateTextPosition()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (text == null) {
            return
        }

        canvas.drawText(
            text!!,
            mTextX,
            mTextY,
            mPaintText
        )
    }

    fun recalculateTextPosition() {
        mTextY = (height + mPaintText.textSize) * 0.45f

        if (text == null) {
            return
        }

        mTextX = (width - mPaintText.measureText(
            text
        )) * 0.5f
    }

}