package good.damn.tvlist.views.round

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.View

open class RoundView(
    context: Context
): View(
    context
) {

    var cornerRadius = 2f
    protected val mRectViewRound = RectF()

    private val mPaintBack = Paint()
    private val mClipPath = Path()

    init {
        mPaintBack.color = 0
        mPaintBack.style = Paint.Style.FILL
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mRectViewRound.top = 0f
        mRectViewRound.left = 0f
        mRectViewRound.bottom = height.toFloat()
        mRectViewRound.right = width.toFloat()
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        mClipPath.reset()
        mClipPath.addRoundRect(
            mRectViewRound,
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
        mClipPath.close()

        canvas.clipPath(
            mClipPath
        )

        if (mPaintBack.color == 0) {
            return
        }

        canvas.drawPaint(
            mPaintBack
        )
    }

    final override fun setBackgroundColor(
        color: Int
    ) {
        mPaintBack.color = color
        super.setBackgroundColor(0)
    }

    final override fun setBackground(
        background: Drawable?
    ) {
        super.setBackground(null)
    }


}