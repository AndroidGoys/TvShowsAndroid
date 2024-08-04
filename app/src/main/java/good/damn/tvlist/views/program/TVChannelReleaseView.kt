package good.damn.tvlist.views.program

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.renderscript.Sampler.Value
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.MainThread
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.animators.RepeatValueAnimator
import good.damn.tvlist.network.api.models.TVChannelRelease
import good.damn.tvlist.views.RatingCanvas
import good.damn.tvlist.views.interactions.interfaces.OnTapListener
import good.damn.tvlist.views.round.RoundView
import kotlinx.coroutines.delay

class TVChannelReleaseView(
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
            mProgressAnimator.setFloatValues(
                0f,mRectProgress.right
            )
        }

    var rating = 0f
        set(v) {
            field = v
            mRating.rating = v
        }

    var cacheRelease: TVChannelRelease? = null

    var onLongClickProgramListener: OnClickChannelReleaseListener? = null
    var onClickChannelReleaseListener: OnClickChannelReleaseListener? = null

    var sizeTitleFactor: Float = 0.01f
    var sizeAgeFactor: Float = 0.06f
    var sizeTimeFactor: Float = 0.04f

    private val mPaintTitle = Paint()
    private val mPaintAge = Paint()
    private val mPaintTime = Paint()
    private val mPaintGradientGray = Paint()
    private val mPaintProgress = Paint()
    private val mPaintLoading = Paint()

    private val mRectProgress = RectF()

    private val mImageAnimator = ValueAnimator()
    private val mProgressAnimator = ValueAnimator()
    private val mDownloadAnimator = RepeatValueAnimator()

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

    private var mImageX = 0f

    private var mDownloadGradientX = 0f

    init {
        mPaintGradientGray.isDither = true
        mPaintLoading.isDither = true

        mImageAnimator.apply {
            duration = 175
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                mImageX = it.animatedValue as? Float ?: 0f
                invalidate()
            }
        }

        mDownloadAnimator.apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            repeatDelay = 1500
            addUpdateListener {
                mDownloadGradientX = it.animatedValue as? Float ?: 0f
                invalidate()
            }
        }

        mProgressAnimator.apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                mRectProgress.right = it.animatedValue as? Float ?: 0f
                invalidate()
            }
        }
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
        mPaintGradientGray.shader = LinearGradient(
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

        val widthGradientDownload = width * 0.5f
        mPaintLoading.shader = LinearGradient(
            0f,
            0f,
            widthGradientDownload,
            0f,
            intArrayOf(
                0x00ffffff,
                0xffffffff.toInt(),
                0x00ffffff
            ),
            floatArrayOf(
                0.0f,
                0.5f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )

        val ratingHeight = height * 0.07804f
        mRating.textSize = ratingHeight * 0.6875f
        mRating.layout(
            x = width * 0.05031f,
            y = height * 0.03902f,
            width * 0.15094f,
            ratingHeight
        )
        mRating.cornerRadius = ratingHeight * 0.3125f

        mDownloadAnimator.setFloatValues(
            -widthGradientDownload, width
        )

        mImageAnimator.setFloatValues(
            -width, 0f
        )

        super.setLayoutParams(params)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mPaintTitle.textSize = height * sizeTitleFactor
        mPaintAge.textSize = height * sizeAgeFactor
        mPaintTime.textSize = height * sizeTimeFactor

        mRectProgress.right = width * progress
        mRectProgress.top = height.toFloat() - progressWidth

        mProgressAnimator.setFloatValues(
            0f,mRectProgress.right
        )

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

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        if (mDownloadAnimator.isRunning) {
            canvas.translate(
                mDownloadGradientX,
                0f
            )
            canvas.drawPaint(
                mPaintLoading
            )
            return
        }

        if (previewImage != null) {
            canvas.drawBitmap(
                previewImage!!,
                mImageX,
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
        onLongClickProgramListener?.onClickChannelRelease(
            this,
            cacheRelease
        )
    }

    override fun onSingleTap() {
        onClickChannelReleaseListener?.onClickChannelRelease(
            this,
            cacheRelease
        )
    }

    @MainThread
    fun startProgressAnimation() {
        mProgressAnimator.start()
    }

    @MainThread
    fun startImageAnimation() {
        mImageAnimator.start()
    }

    @MainThread
    fun startDownloadAnimation() {
        mDownloadAnimator.start()
    }

    @MainThread
    fun stopDownloadAnimation() {
        mDownloadAnimator.cancel()
    }

}