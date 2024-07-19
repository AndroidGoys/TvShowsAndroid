package good.damn.tvlist.views.round

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import good.damn.kamchatka.views.interactions.AnimatedTouchInteraction
import good.damn.kamchatka.views.interactions.interfaces.OnActionListener
import good.damn.kamchatka.views.interactions.interfaces.OnUpdateAnimationListener
import good.damn.tvlist.views.interactions.interfaces.OnTapListener

open class RoundView(
    context: Context
): View(
    context
), OnUpdateAnimationListener, OnTapListener {

    var cornerRadius = 2f
    protected val mRectViewRound = RectF()

    private val mPaintBack = Paint()
    private val mClipPath = Path()

    protected var mOnClickListener: OnClickListener? = null
    protected val mTouchInteraction = AnimatedTouchInteraction()

    init {
        mPaintBack.color = 0
        mPaintBack.style = Paint.Style.FILL

        super.setOnTouchListener(
            mTouchInteraction
        )

        mTouchInteraction.setDuration(
            150
        )

        mTouchInteraction.setInterpolator(
            AccelerateInterpolator()
        )

        mTouchInteraction.setOnUpdateAnimationListener(
            this
        )

        mTouchInteraction.onTapListener = this

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

    override fun onUpdateAnimation(
        animatedValue: Float
    ) {
        alpha = animatedValue
    }


    final override fun setOnClickListener(
        l: OnClickListener?
    ) {
        mOnClickListener = l
        super.setOnClickListener(
            null
        )
    }

    final override fun setOnTouchListener(
        l: OnTouchListener?
    ) {
        super.setOnTouchListener(
            mTouchInteraction
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

    override fun onLongTap() {}

    override fun onSingleTap() {
        mOnClickListener?.onClick(
            this
        )
    }


}