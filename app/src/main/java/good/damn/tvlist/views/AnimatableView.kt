package good.damn.tvlist.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

open class AnimatableView(
    context: Context
): View(
    context
), ValueAnimator.AnimatorUpdateListener,
Animator.AnimatorListener {

    var onAnimationFrameUpdate: ((Float)->Unit)? = null
    var onAnimationEnd: (()->Unit)? = null

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

    private val mAnimator = ValueAnimator.ofFloat(
        0.0f, 1.0f
    )

    init {
        mAnimator.apply {
            interpolator = LinearInterpolator()
            duration = 350
        }

        mAnimator.addUpdateListener(
            this
        )

        mAnimator.addListener(
            this
        )
    }

    fun startAnimation() {
        mAnimator.start()
    }

    final override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        onAnimationFrameUpdate?.invoke(
            animation.animatedValue as Float
        )
    }

    final override fun onAnimationEnd(
        animation: Animator
    ) {
        onAnimationEnd?.invoke()
    }

    final override fun onAnimationCancel(
        animation: Animator
    ) = onAnimationEnd(animation)


    final override fun onAnimationStart(animation: Animator) = Unit
    final override fun onAnimationRepeat(animation: Animator) = Unit
}