package good.damn.tvlist.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt

class TVChannelView(
    context: Context
): View(
    context
) {
    var text = ""
        set(v) {
            field = v
            calculateIconBounds()
        }
    var imagePreview: Bitmap? = null
    var imageIcon: Drawable? = null
    var cornerRadiusPreview = 15f

    @ColorInt
    var textColor: Int = 0
        set(v) {
            field = v
            mPaintText.color = v
        }

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintText.typeface = v
        }

    var textSize: Float = 0f
        set(v) {
            field = v
            mPaintText.textSize = v
        }

    private val mPaintText = Paint()
    private val mPath = Path()

    private var mTextX = 0f
    private var mTextY = 0f

    private val mRectPreview = RectF()

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

        val marginLeft = paddingLeft.toFloat()
        val previewSize = imagePreview?.height ?: 0

        val offset = if (imagePreview == null) {
            marginLeft
        } else {
            marginLeft + previewSize
        }

        mTextX = offset + previewSize * 0.5f
        mTextY = (height + mPaintText.textSize) * 0.47f

        mRectPreview.left = marginLeft
        mRectPreview.top = (height - previewSize) * 0.5f

        mRectPreview.right = mRectPreview.left + previewSize
        mRectPreview.bottom = mRectPreview.top + previewSize

        calculateIconBounds()
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        canvas.drawText(
            text,
            mTextX,
            mTextY,
            mPaintText
        )

        imageIcon?.draw(
            canvas
        )

        if (imagePreview == null) {
            return
        }

        mPath.reset()
        mPath.addRoundRect(
            mRectPreview,
            cornerRadiusPreview,
            cornerRadiusPreview,
            Path.Direction.CW
        )
        mPath.close()

        canvas.clipPath(
            mPath
        )

        canvas.drawBitmap(
            imagePreview!!,
            mRectPreview.left,
            mRectPreview.top,
            mPaintText
        )
    }

    private fun calculateIconBounds() {
        (height * 0.47222f).toInt().let { iconSize ->
            val leftIcon = (mTextX + mPaintText.measureText(
                text
            ) + iconSize * 0.64705f).toInt()

            val topIcon = (height * 0.20833f).toInt()
            imageIcon?.setBounds(
                leftIcon,
                topIcon,
                leftIcon + iconSize,
                topIcon + iconSize
            )
        }
    }

}