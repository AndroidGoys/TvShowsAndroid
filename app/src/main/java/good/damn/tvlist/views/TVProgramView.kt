package good.damn.tvlist.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.network.api.models.enums.CensorAge
import good.damn.tvlist.views.round.RoundView

class TVProgramView(
    context: Context
): RoundView(
    context
) {

    var title = "Кухня"
    var time: String = "12:30"
    var age: String = "18+"

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintTitle.typeface = v
            mPaintAge.typeface = v
            mPaintTime.typeface = v
        }

    @ColorInt
    var textTintColor: Int = 0
        set(v) {
            field = v
            mPaintTitle.color = v
            mPaintAge.color = v
            mPaintTime.color = v
        }

    var sizeTitleFactor: Float = 0.01f
    var sizeAgeFactor: Float = 0.06f
    var sizeTimeFactor: Float = 0.04f

    private val mPaintTitle = Paint()
    private val mPaintAge = Paint()
    private val mPaintTime = Paint()
    private val mPaintGradientGray = Paint()

    private val mRating = RatingCanvas()

    private var mTitleY = 0f
    private var mTitleX = 0f

    private var mAgeY = 0f
    private var mAgeX = 0f

    private var mTimeY = 0f
    private var mTimeX = 0f

    init {
        mPaintGradientGray.isDither = true
    }

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        if (params == null) {
            return
        }
        val halfWidth = params.width * 0.5f
        val linearGradient = LinearGradient(
            halfWidth,
            params.height.toFloat(),
            halfWidth,
            0f,
            intArrayOf(
                0xff343434.toInt(),
                0x00ffffff
            ),
            floatArrayOf(
                0.0f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )
        mPaintGradientGray.shader = linearGradient

        val ratingHeight = params.height * 0.07804f
        mRating.layout(
            x = params.width * 0.05031f,
            y = params.height * 0.03902f,
            params.width * 0.15094f,
            ratingHeight
        )

        mRating.cornerRadius = ratingHeight * 0.3125f

        super.setLayoutParams(params)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mPaintTitle.textSize = height * sizeTitleFactor
        mPaintAge.textSize = height * sizeAgeFactor
        mPaintTime.textSize = height * sizeTimeFactor

        paddingBottom.toFloat().let { bottomPadding ->
            mTimeY = height - bottomPadding
            mAgeY = mTimeY
            mTitleY = mTimeY - mPaintTime.textSize * 1.5f
        }

        paddingStart.toFloat().let { startPadding ->
            mTitleX = startPadding
            mTimeX = startPadding
            mAgeX = startPadding + width * 0.04031f + mPaintTime.measureText(time)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mPaintGradientGray.shader != null) {
            canvas.drawPaint(
                mPaintGradientGray
            )
        }

        canvas.drawText(
            title,
            mTitleX,
            mTitleY,
            mPaintTitle
        )

        canvas.drawText(
            time,
            mTimeX,
            mTimeY,
            mPaintTime
        )

        canvas.drawText(
            age,
            mAgeX,
            mAgeY,
            mPaintAge
        )

        mRating.draw(
            canvas
        )
    }

}