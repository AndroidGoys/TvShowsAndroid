package good.damn.tvlist.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.View
import good.damn.tvlist.App
import good.damn.tvlist.R

class TVIconNameView(
    context: Context
): View(
    context
) {

    var bitmap: Bitmap? = null
    var text: String? = null

    private var cornerRadius = 0f

    private val mPaintText = Paint()

    private var mTextX = 0f
    private var mTextY = 0f

    private val mRectBitmap = RectF()

    private val mPath = Path()

    init {
        mPaintText.typeface = App.font(
            R.font.open_sans_regular,
            context
        )

        mPaintText.color = App.color(
            R.color.text
        )
    }

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

        val height = height.toFloat()

        mPaintText.textSize = height * 0.31034f
        mTextX = width * 0.20108f
        mTextY = (height + mPaintText.textSize) * 0.475f

        mRectBitmap.top = 0f
        mRectBitmap.left = 0f
        mRectBitmap.bottom = height
        mRectBitmap.right = height

        cornerRadius = height * 0.24137f
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        if (text != null) {
            canvas.drawText(
                text!!,
                mTextX,
                mTextY,
                mPaintText
            )
        }

        if (bitmap == null) {
            return
        }

        mPath.reset()
        mPath.addRoundRect(
            mRectBitmap,
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
        mPath.close()

        canvas.clipPath(
            mPath
        )

        canvas.drawBitmap(
            bitmap!!,
            0f,
            0f,
            mPaintText
        )
    }
}