package good.damn.tvlist.views.buttons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import good.damn.kamchatka.views.interactions.AnimatedTouchInteraction
import good.damn.kamchatka.views.interactions.interfaces.OnActionListener
import good.damn.kamchatka.views.interactions.interfaces.OnUpdateAnimationListener
import good.damn.tvlist.views.interactions.interfaces.OnTapListener

class BigButtonView(
    context: Context
): CardView(
    context
), OnUpdateAnimationListener, OnTapListener {

    var text = ""
    var imageStart: Drawable? = null
    var imageEnd: Drawable? = null

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaint.typeface = v
        }

    var imageStartSizeFactor = 0.5f
    var imageEndSizeFactor = 0.2f

    var textSizeFactor: Float = 0.0f
        set(v) {
            field = v
            mPaint.textSize = height * v
        }

    @ColorInt
    var textColor: Int = 0
        set(v) {
            field = v
            mPaint.color = v
        }

    private var mTextX = 0f
    private var mTextY = 0f
    private val mPaint = Paint()

    private var mTouchInteraction = AnimatedTouchInteraction()

    private var mOnClickListener: OnClickListener? = null

    init {
        mTouchInteraction.setDuration(
            150
        )

        mTouchInteraction.setInterpolator(
            LinearOutSlowInInterpolator()
        )

        mTouchInteraction.onTapListener = this

        mTouchInteraction.setOnUpdateAnimationListener(
            this
        )

        super.setOnTouchListener(
            mTouchInteraction
        )

    }

    override fun setBackgroundColor(
        color: Int
    ) {
        setCardBackgroundColor(color)
        super.setBackgroundColor(color)
    }

    override fun setOnClickListener(
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


        mPaint.textSize = height * textSizeFactor

        mTextX = width * 0.20418f
        mTextY = (height + mPaint.textSize) * 0.5f

        (width * 0.0616f).toInt().let { leftMargin ->
            val imageSize = (
                height * imageStartSizeFactor
                ).toInt()

            val topMargin = ((height - imageSize) * 0.5f)
                .toInt()

            imageStart?.setBounds(
                leftMargin,
                topMargin,
                leftMargin + imageSize,
                topMargin + imageSize
            )
        }

        (width * 0.8613f).toInt().let { leftMargin ->

            val imageSize = (
                height * imageEndSizeFactor
            ).toInt()

            val topMargin = ((height - imageSize) * 0.5f)
                .toInt()

            imageEnd?.setBounds(
                leftMargin,
                topMargin,
                leftMargin + imageSize,
                topMargin + imageSize
            )
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        imageStart?.draw(
            canvas
        )

        canvas.drawText(
            text,
            mTextX,
            mTextY,
            mPaint
        )

        imageEnd?.draw(
            canvas
        )
    }

    override fun onUpdateAnimation(
        animatedValue: Float
    ) {
        alpha = animatedValue
    }

    override fun onLongTap() {

    }

    override fun onSingleTap() {
        mOnClickListener?.onClick(
            this
        )

    }

}