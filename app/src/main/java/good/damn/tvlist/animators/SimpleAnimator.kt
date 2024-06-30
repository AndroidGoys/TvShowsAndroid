package good.damn.tvlist.animators

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

class SimpleAnimator<VIEW: View>(
    private val mView: VIEW
): ValueAnimator.AnimatorUpdateListener,
Animator.AnimatorListener {

    var onFrameUpdate: ((Float, VIEW)->Unit)? = null
    var onEndAnimation: ((VIEW)->Unit)? = null

    var interpolator: Interpolator = LinearInterpolator()
        set(v) {
            field = v
            mAnimator.interpolator = v
        }

    var duration: Long = 0
        set(v) {
            field = v
            mAnimator.duration = v
        }

    var startDelay: Long = 0
        set(v) {
            field = v
            mAnimator.startDelay = v
        }

    private val mAnimator = ValueAnimator.ofFloat(
        0.0f, 1.0f
    )

    init {
        mAnimator.addUpdateListener(
            this
        )

        mAnimator.addListener(
            this
        )
    }

    fun start() {
        mAnimator.start()
    }

    override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        onFrameUpdate?.invoke(
            animation.animatedValue as Float,
            mView
        )
    }

    override fun onAnimationEnd(
        animation: Animator
    ) {
        onEndAnimation?.invoke(
            mView
        )
    }

    override fun onAnimationCancel(
        animation: Animator
    ) = onAnimationEnd(
        animation
    )

    override fun onAnimationStart(
        animation: Animator
    ) = Unit

    override fun onAnimationRepeat(
        animation: Animator
    ) = Unit

}