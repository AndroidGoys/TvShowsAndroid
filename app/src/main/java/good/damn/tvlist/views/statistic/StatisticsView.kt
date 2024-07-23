package good.damn.tvlist.views.statistic

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.views.RatingCanvas

class StatisticsView(
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

    @ColorInt
    var progressColor: Int = 0
        set(v) {
            field = v
            mPaintProgress.color = v
        }

    @ColorInt
    var progressBackColor: Int = 0
        set(v) {
            field = v
            mPaintProgressBack.color = v
        }

    var count: String = "0000000"

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

    var progressTitles: Array<ProgressTitleDraw>? = null

    private var mRatingStr = "0.0"

    private val mPaintRating = Paint()
    private val mPaintCount = Paint()
    private val mPaintCategory = Paint()
    private val mPaintProgress = Paint()
    private val mPaintProgressBack = Paint()

    private var mRatingTextX = 0f
    private var mRatingTextY = 0f
    private var mRatingTextWidth = 0f

    private var mCountTextX = 0f
    private var mCountTextY = 0f

    private val mDrawablePeople = App.drawable(
        R.drawable.ic_people
    )

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        super.setLayoutParams(params)
        if (params == null) {
            return
        }

        layoutView(
            params.width,
            params.height
        )
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

        mDrawablePeople?.draw(
            canvas
        )

        progressTitles?.forEach {
            it.draw(canvas)
        }
    }

    fun updateStats() {
        layoutView(
            widthParams(),
            heightParams()
        )
    }

    fun updateGradient() {
        mPaintRating.shader = LinearGradient(
            mRatingTextX,
            mRatingTextY,
            mRatingTextX+mRatingTextWidth,
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
    }

    private fun layoutView(
        width: Int,
        height: Int
    ) {
        mRatingTextWidth = mPaintRating.measureText(
            mRatingStr
        )

        mPaintRating.textSize = height * textSizeRatingFactor
        mPaintCount.textSize = height * textSizeCountFactor
        mPaintCategory.textSize = height * textSizeCountFactor

        val w = width * 0.30747f

        mRatingTextX = (w - mRatingTextWidth) * 0.4f
        mRatingTextY = height * 0.09161f + mPaintRating.textSize

        mCountTextX = (w - mPaintCount.measureText(
            count
        )) * 0.5f
        mCountTextY = height * 0.68585f + mPaintCount.textSize

        progressTitles?.let {
            var progressY = 0f
            val marginProgress = height / it.size - mPaintCount.textSize

            val itemWidth = width - w

            it.forEach { title ->

                title.stylePaint(
                    mPaintCategory,
                    mPaintProgress,
                    mPaintProgressBack
                )

                title.layout(
                    w,
                    progressY,
                    itemWidth
                )

                progressY += mPaintCount.textSize + marginProgress
            }
        }

        (mPaintCount.textSize).let { size ->
            val margin = size * 0.5f
            val marginV = size * 0.1f
            mDrawablePeople?.setBounds(
                (mCountTextX - size - margin).toInt(),
                (mCountTextY - size + marginV).toInt(),
                (mCountTextX - margin).toInt(),
                (mCountTextY + marginV).toInt()
            )
        }

        updateGradient()
    }
}