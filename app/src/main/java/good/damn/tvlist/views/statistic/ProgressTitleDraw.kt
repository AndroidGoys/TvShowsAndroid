package good.damn.tvlist.views.statistic

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class ProgressTitleDraw(
    private val mCategory: String,
    private val mNormalProgress: Float
) {

    private var mTextX = 0f
    private var mTextY = 0f

    private var mCornerRadius = 0f
    private val mProgressBackRect = RectF()
    private val mProgressRect = RectF()

    private lateinit var mPaintText: Paint
    private lateinit var mPaintProgress: Paint
    private lateinit var mPaintProgressBack: Paint

    fun stylePaint(
        paintText: Paint,
        paintProgress: Paint,
        paintProgressBack: Paint
    ) {
        mPaintText = paintText
        mPaintProgress = paintProgress
        mPaintProgressBack = paintProgressBack
    }

    fun layout(
        x: Float,
        y: Float,
        width: Float
    ) {
        val height = mPaintText.textSize
        mTextX = x
        mTextY = y + height

        val progressWidth = width * 0.914529f
        val progressHeight = height * 0.4f

        val marginV = (height - progressHeight) * 0.5f
        val marginLeftProgress = width - progressWidth

        val progressY = y + marginV

        mProgressBackRect.apply {
            top = progressY
            bottom = progressY + progressHeight
            left = x + marginLeftProgress
            right = x + width
        }

        mProgressRect.apply {
            top = mProgressBackRect.top
            bottom = mProgressBackRect.bottom
            left = mProgressBackRect.left
            right = x + width * mNormalProgress
        }

        mCornerRadius = height * 0.5f
    }

    fun draw(
        canvas: Canvas
    ) {
        canvas.drawText(
            mCategory,
            mTextX,
            mTextY,
            mPaintText
        )

        canvas.drawRoundRect(
            mProgressBackRect,
            mCornerRadius,
            mCornerRadius,
            mPaintProgressBack
        )


        canvas.drawRoundRect(
            mProgressRect,
            mCornerRadius,
            mCornerRadius,
            mPaintProgress
        )
    }

}