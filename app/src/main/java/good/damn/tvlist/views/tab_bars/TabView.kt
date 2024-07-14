package good.damn.tvlist.views.tab_bars

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import good.damn.tvlist.views.round.RoundView
import java.lang.reflect.Type

class TabView(
    context: Context
): RoundView(
    context
) {

    var index: Int = -1

    var text: String? = null

    var typeface: Typeface? = null
        set(v) {
            field = v
            mPaintText.typeface = v
        }

    @ColorInt
    var textColor = Color.RED
        set(v) {
            field = v
            mPaintText.color = v
        }

    @ColorInt
    var strokeColor = Color.CYAN
        set(v) {
            field = v
            mPaintStroke.color = v
        }

    var drawable: Drawable? = null

    var textSizeFactor = 0.2f
    var strokeWidth = 0f
        set(v) {
            field = v
            mPaintStroke.strokeWidth = v
        }

    private val mPaintStroke = Paint().apply {
        style = Paint.Style.STROKE
    }
    private val mPaintText = Paint()

    private var mTextX = 0f
    private var mTextY = 0f

    private val mRectStroke = RectF()

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

        val ts = mPaintText.textSize
        var widthText = 0f

        text?.let {
            widthText = mPaintText.measureText(
                it
            )
            mTextX = (width - widthText) * 0.5f
        }
        mTextY = (height + ts) * 0.475f

        val sw = mPaintStroke.strokeWidth
        mRectStroke.top = sw
        mRectStroke.left = sw
        mRectStroke.bottom = height - sw
        mRectStroke.right = width - sw

        drawable?.apply {
            mTextX -= ts * 0.5f

            val dx = mTextX + widthText
            setBounds(
                dx.toInt(),
                (mTextY - ts).toInt(),
                (dx + ts).toInt(),
                mTextY.toInt()
            )
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        drawable?.draw(
            canvas
        )

        if (text == null) {
            return
        }

        canvas.drawText(
            text!!,
            mTextX,
            mTextY,
            mPaintText
        )

        canvas.drawRoundRect(
            mRectStroke,
            cornerRadius,
            cornerRadius,
            mPaintStroke
        )
    }

}