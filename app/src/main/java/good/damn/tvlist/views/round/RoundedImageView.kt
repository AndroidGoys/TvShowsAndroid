package good.damn.tvlist.views.round

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
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

    var strokeWidth = 2f
        set(v) {
            field = v
            mPaint.strokeWidth = v
        }

    var drawable: Drawable? = null

    var imageScaleX = 1f
    var imageScaleY = 1f

    private val mPaint = Paint()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        mTouchInteraction.setDuration(
            150
        )

        mTouchInteraction.setInterpolator(
            AccelerateDecelerateInterpolator()
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val dx = ((1.0f-imageScaleX) * width * 0.5f).toInt()
        val dy = ((1.0f-imageScaleY) * height * 0.5f).toInt()

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

        drawable?.draw(
            canvas
        )

        canvas.drawRoundRect(
            mRectViewRound,
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