package good.damn.tvlist.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

class RoundedImageView(
    context: Context
): View(
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

    var cornerRadius = 2f


    var imageScaleX = 1f
    var imageScaleY = 1f

    private var mBackgroundColor = 0

    private val mPaint = Paint()
    private val mRectView = RectF()

    private val mClipPath = Path()

    init {
        mPaint.style = Paint.Style.STROKE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mRectView.top = 0f
        mRectView.left = 0f
        mRectView.bottom = height.toFloat()
        mRectView.right = width.toFloat()

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
        mClipPath.reset()
        mClipPath.addRoundRect(
            mRectView,
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
        mClipPath.close()

        canvas.clipPath(
            mClipPath
        )

        drawable?.draw(
            canvas
        )

        canvas.drawRoundRect(
            mRectView,
            cornerRadius,
            cornerRadius,
            mPaint
        )

    }

    final override fun setBackgroundColor(
        color: Int
    ) {
        mBackgroundColor = color
        super.setBackgroundColor(0)
    }

    final override fun setBackground(
        background: Drawable?
    ) {
        super.setBackground(null)
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