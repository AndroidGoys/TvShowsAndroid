package good.damn.kamchatka.views.interactions

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import good.damn.kamchatka.views.interactions.interfaces.OnActionListener
import good.damn.kamchatka.views.interactions.interfaces.OnUpdateAnimationListener
import good.damn.tvlist.extensions.checkBounds
import good.damn.tvlist.views.interactions.interfaces.OnTapListener

class AnimatedTouchInteraction
: BaseTouchInteraction(),
ValueAnimator.AnimatorUpdateListener,
OnActionListener {

    companion object {
        private const val TAG = "AnimatedTouchInteractio"
        private const val TICK_DOUBLE_MS = 1500L
        private const val TICK_LONG_PRESS_MS = 900L
    }

    var minValue: Float = 0.75f
    var maxValue: Float = 1.0f

    var onTapListener: OnTapListener? = null
    private var mOnUpdateListener: OnUpdateAnimationListener? = null
    private var mOnActionListener: OnActionListener? = null

    private val mAnimator = ValueAnimator()
    private var mCurrentValue = 0f

    private var mTimeDown = 0L

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
        mTimeDown = System.currentTimeMillis()
        mAnimator.setFloatValues(
            maxValue, minValue
        )
        mAnimator.start()

        mOnActionListener?.onDown(
            v,
            event
        )
    }

    override fun onCancel(
        v: View,
        event: MotionEvent
    ) {
        mAnimator.setFloatValues(
            mCurrentValue, maxValue
        )
        mAnimator.start()
    }

    override fun onUp(
        v: View,
        event: MotionEvent
    ) {
        onCancel(v, event)

        if (v.checkBounds(
            event.x,
            event.y
        )) {
            if (System.currentTimeMillis() - mTimeDown > TICK_LONG_PRESS_MS) {
                onTapListener?.onLongTap()
                return
            }

            onTapListener?.onSingleTap()
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