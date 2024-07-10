package good.damn.tvlist.views.round

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

class RoundedImageView(
    context: Context
): RoundView(
    context
) {

    @ColorInt
    var strokeColor: Int = 0
        set(v) {
            field = v
            mPaint.color = v
        }

    var strokeWidth = 0f
        set(v) {
            field = v
            mPaint.strokeWidth = v
        }

    var bitmap: Bitmap? = null
    var drawable: Drawable? = null

    var imageScaleX = 1f
    var imageScaleY = 1f

    private val mPaint = Paint()

    private var mRectStroke = RectF()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        mPaint.color = 0xff000000.toInt()

        mTouchInteraction.apply {
            minValue = 1.2f
            maxValue = 1.0f
            setDuration(150)
            setInterpolator(
                AccelerateDecelerateInterpolator()
            )
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val dx = ((1.0f-imageScaleX) * width * 0.5f).toInt()
        val dy = ((1.0f-imageScaleY) * height * 0.5f).toInt()

        (mPaint.strokeWidth * 0.5f).let { strokeWidth ->
            mRectStroke.left = mRectViewRound.left + strokeWidth
            mRectStroke.top = mRectViewRound.top + strokeWidth
            mRectStroke.right = mRectViewRound.right - strokeWidth
            mRectStroke.bottom = mRectViewRound.bottom - strokeWidth
        }

        drawable?.setBounds(
            dx,
            dy,
            width-dx,
            height-dy
        )
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        if (bitmap != null) {
            canvas.drawBitmap(
                bitmap!!,
                0f,
                0f,
                mPaint
            )
        }

        drawable?.draw(
            canvas
        )

        if (mPaint.color == 0xff000000.toInt()) {
            return
        }

        canvas.drawRoundRect(
            mRectStroke,
            cornerRadius,
            cornerRadius,
            mPaint
        )
    }

    override fun onUpdateAnimation(
        animatedValue: Float
    ) {
        scaleX = animatedValue
        scaleY = animatedValue
    }

    fun setStrokeColorId(
        @ColorRes id: Int
    ) {
        strokeColor = ResourcesCompat.getColor(
            resources,
            id,
            null
        )
    }

}