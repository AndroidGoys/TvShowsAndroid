package good.damn.tvlist.views.program

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.views.RatingCanvas
import good.damn.tvlist.views.interactions.interfaces.OnTapListener
import good.damn.tvlist.views.round.RoundView
import java.util.Calendar

class TVProgramView(
    context: Context
) : RoundView(
    context
), OnTapListener {

    companion object {
        private const val TAG = "TVProgramView"
    }

    var title = "Кухня"
    var timeString: String = "12:30"
    var age: String = "18+"

    var previewImage: Bitmap? = null

    var isFavourite: Boolean = false

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintTitle.typeface = v
            mPaintAge.typeface = v
            mPaintTime.typeface = v
            mRating.typeface = v
        }

    @ColorInt
    var textTintColor: Int = 0
        set(v) {
            field = v
            mPaintTitle.color = v
            mPaintAge.color = v
            mPaintTime.color = v
            mRating.textColor = v
        }

    var progressWidth: Int = 2
        set(v) {
            field = v
            if (height == 0) {
                return
            }
            mRectProgress.top = height.toFloat() - progressWidth
        }

    @ColorInt
    var progressColor: Int = 0
        set(v) {
            field = v
            mPaintProgress.color = v
        }

    var progress: Float = 0.8f
        set(v) {
            field = v
            if (width == 0) {
                return
            }
            mRectProgress.right = width * progress
        }

    var rating = 0f
        set(v) {
            field = v
            mRating.rating = v
        }

    var cacheProgram: TVProgram? = null

    var onLongClickProgramListener: OnClickProgramListener? = null
    var onClickProgramListener: OnClickProgramListener? = null

    var sizeTitleFactor: Float = 0.01f
    var sizeAgeFactor: Float = 0.06f
    var sizeTimeFactor: Float = 0.04f

    private val mPaintTitle = Paint()
    private val mPaintAge = Paint()
    private val mPaintTime = Paint()
    private val mPaintGradientGray = Paint()
    private val mPaintProgress = Paint()

    private val mRectProgress = RectF()

    private val mDrawableFavourites = App.drawable(
        R.drawable.ic_star_fill_lime
    )

    private val mRating = RatingCanvas()

    private var mProgressRadius = 0f

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

        val width = params.width.toFloat()
        val height = params.height.toFloat()

        mRectProgress.left = 0f
        mRectProgress.bottom = height
        mProgressRadius = height * 0.5f

        val halfWidth = width * 0.5f
        val linearGradient = LinearGradient(
            halfWidth,
            height,
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

        val ratingHeight = height * 0.07804f
        mRating.textSize = ratingHeight * 0.6875f
        mRating.layout(
            x = width * 0.05031f,
            y = height * 0.03902f,
            width * 0.15094f,
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

        mRectProgress.right = width * progress
        mRectProgress.top = height.toFloat() - progressWidth

        paddingBottom.toFloat().let { bottomPadding ->
            mTimeY = height - bottomPadding
            mAgeY = mTimeY
            mTitleY = mTimeY - mPaintTime.textSize * 1.5f
        }

        paddingStart.toFloat().let { startPadding ->
            mTitleX = startPadding
            mTimeX = startPadding
            mAgeX = startPadding + width * 0.04031f + mPaintTime.measureText(timeString)
        }

        val favSize = (width * 0.18f).toInt()

        val drawX = width - favSize - paddingStart
        val drawY = (height * 0.03902f).toInt()
        mDrawableFavourites?.setBounds(
            drawX,
            drawY,
            drawX + favSize,
            drawY + favSize
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (previewImage != null) {
            canvas.drawBitmap(
                previewImage!!,
                0f,
                0f,
                mPaintProgress
            )
        }

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
            timeString,
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

        if (progress > 0.0f) {
            canvas.drawRoundRect(
                mRectProgress,
                mProgressRadius,
                mProgressRadius,
                mPaintProgress
            )
        }

        if (isFavourite) {
            mDrawableFavourites?.draw(
                canvas
            )
        }

        mRating.draw(
            canvas
        )
    }

    override fun onLongTap() {
        onLongClickProgramListener?.onClickProgram(
            this,
            cacheProgram
        )
    }

    override fun onSingleTap() {
        onClickProgramListener?.onClickProgram(
            this,
            cacheProgram
        )
    }

}