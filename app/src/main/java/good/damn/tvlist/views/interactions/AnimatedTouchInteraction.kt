package good.damn.kamchatka.views.interactions

import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import good.damn.kamchatka.views.interactions.interfaces.OnActionListener
import good.damn.kamchatka.views.interactions.interfaces.OnUpdateAnimationListener
import good.damn.tvlist.extensions.checkBounds

class AnimatedTouchInteraction
: BaseTouchInteraction(),
ValueAnimator.AnimatorUpdateListener,
OnActionListener {

    var minValue: Float = 0.75f
    var maxValue: Float = 1.0f

    private var mOnUpdateListener: OnUpdateAnimationListener? = null
    private var mOnActionListener: OnActionListener? = null

    private val mAnimator = ValueAnimator()
    private var mCurrentValue = 0f

    init {
        mAnimator.duration = 350
        mAnimator.interpolator = DecelerateInterpolator()
        mAnimator.addUpdateListener(
            this
        )

        super.setOnActionListener(
            this
        )
    }

    override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        mCurrentValue = animation.animatedValue as Float
        mOnUpdateListener?.onUpdateAnimation(
            mCurrentValue
        )
    }

    override fun setOnActionListener(
        l: OnActionListener?
    ) {
        mOnActionListener = l
    }

    override fun onDown(
        v: View,
        event: MotionEvent
    ) {
        mAnimator.setFloatValues(
            maxValue, minValue
        )
        mAnimator.start()

        if (event.action == MotionEvent.ACTION_CANCEL) {
            return
        }
        mOnActionListener?.onDown(
            v,
            event
        )
    }

    override fun onUp(
        v: View,
        event: MotionEvent
    ) {
        mAnimator.setFloatValues(
            mCurrentValue, maxValue
        )
        mAnimator.start()

        if (v.checkBounds(
            event.x,
            event.y
        )) {
            mOnActionListener?.onUp(
                v,  event
            )
        }
    }

    fun setDuration(
        i: Long
    ) {
        mAnimator.setDuration(
            i
        )
    }

    fun setInterpolator(
        i: Interpolator
    ) {
        mAnimator.interpolator = i
    }

    fun setOnUpdateAnimationListener(
        l: OnUpdateAnimationListener?
    ) {
        mOnUpdateListener = l
    }

}