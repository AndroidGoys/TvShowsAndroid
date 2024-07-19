package good.damn.tvlist.views.toasts

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.models.AnimationConfig
import good.damn.tvlist.views.round.RoundView

class ToastImage(
    context: Context
): CardView(
    context
), ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    companion object {
        private const val TAG = "ToastImage"
    }

    var durationShow: Long = 3000

    var textSizeFactor: Float = 0.2f

    val imageView = AppCompatImageView(
        context
    )

    val textView = AppCompatTextView(
        context
    ).apply {
        setTextColor(
            0xffffffff.toInt()
        )

        gravity = Gravity.CENTER_VERTICAL

        typeface = App.font(
            R.font.open_sans_semi_bold,
            context
        )
    }

    private var mYPos = 0.0f
    private var mBottom = 0.0f

    private val mAnimator = ValueAnimator()

    private var mIsEndAnimation = false

    init {

        mAnimator.addUpdateListener(
            this
        )

        mAnimator.addListener(
            this
        )

        mAnimator.setFloatValues(
            1.0f,0.0f
        )

        setCardBackgroundColor(
            App.color(
                R.color.background_toast
            )
        )
        addView(imageView)
        addView(textView)
    }

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        super.setLayoutParams(params)
        if (params == null) {
            return
        }

        val width = params.width
        val height = params.height

        val s = (height * 0.4705f)
            .toInt()

        val leftImage = width * 0.04f

        val leftTextView = s + leftImage + leftImage

        imageView.boundsFrame(
            Gravity.CENTER_VERTICAL,
            width = s,
            height = s,
            left = leftImage
        )

        textView.boundsFrame(
            width = (width - leftTextView).toInt(),
            height = height,
            left = leftTextView
        )

        textView.setTextSizePx(
            height * textSizeFactor
        )

    }

    fun show(
        animation: AnimationConfig
    ) {
        mAnimator.apply {
            interpolator = animation.interpolator
            duration = animation.duration
            start()
        }

        y = App.HEIGHT.toFloat()

        mYPos = App.HEIGHT * 0.85f
        mBottom = App.HEIGHT - mYPos
    }

    override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        y = mYPos + mBottom * (
            animation.animatedValue as Float
        )
    }

    override fun onAnimationEnd(
        animation: Animator
    ) {
        if (mIsEndAnimation) {
            (parent as? ViewGroup)?.removeView(
                this
            )
            return
        }
        mIsEndAnimation = true
        mAnimator.setFloatValues(
            0.0f,1.0f
        )
        Handler(
            Looper.getMainLooper()
        ).postDelayed({
            mAnimator.start()
            },
            durationShow
        )
    }

    override fun onAnimationStart(
        animation: Animator
    ) = Unit

    override fun onAnimationCancel(
        animation: Animator
    ) = onAnimationEnd(animation)

    override fun onAnimationRepeat(
        animation: Animator
    ) = Unit

}