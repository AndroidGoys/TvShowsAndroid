package good.damn.tvlist.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt

class StatisticView(
    context: Context
): View(
    context
) {

    var rating: Float = 4.6f
        set(v) {
            field = (v * 10).toInt() / 10f
            mRatingStr = field.toString()
        }

    @ColorInt
    var textColor: Int = 0
        set(v) {
            field = v
            mPaintCount.color = v
            mPaintCategory.color = v
        }

    var count: String = "1234567"

    var textSizeRatingFactor = 0.2f
    var textSizeCountFactor = 0.1f
    var textSizeCategoryFactor = 0.15f

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintRating.typeface = v
            mPaintCount.typeface = v
            mPaintCategory.typeface = v
        }

    private var mRatingStr = "4.6"

    private val mPaintRating = Paint()
    private val mPaintCount = Paint()
    private val mPaintCategory = Paint()

    private var mRatingTextX = 0f
    private var mRatingTextY = 0f

    private var mCountTextX = 0f
    private var mCountTextY = 0f

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        super.setLayoutParams(params)
        if (params == null) {
            return
        }

        val width = params.width
        val height = params.height

        val textWidth = mPaintRating.measureText(
            mRatingStr
        )

        mPaintRating.textSize = height * textSizeRatingFactor
        mPaintCount.textSize = height * textSizeCountFactor

        val w = width * 0.30747f

        mRatingTextX = (w - textWidth) * 0.5f
        mRatingTextY = height * 0.16161f + mPaintRating.textSize

        mCountTextX = (w - mPaintCount.measureText(
            count
        )) * 0.5f

        mCountTextY = height * 0.68585f + mPaintCount.textSize

        val gradient = LinearGradient(
            mRatingTextX,
            mRatingTextY,
            mRatingTextX+textWidth,
            mRatingTextY,
            intArrayOf(
                RatingCanvas.detectColor(
                    rate = rating.toInt()
                ),
                RatingCanvas.detectColor(
                    rate = ((rating - rating.toInt()) * 10f).toInt(),
                    times = 2
                )
            ),
            floatArrayOf(
                0.0f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )
        mPaintRating.shader = gradient
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        canvas.drawText(
            mRatingStr,
            mRatingTextX,
            mRatingTextY,
            mPaintRating
        )

        canvas.drawText(
            count,
            mCountTextX,
            mCountTextY,
            mPaintCount
        )
    }

}