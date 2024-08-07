package good.damn.tvlist.animators

import android.animation.Animator
import android.animation.ValueAnimator

class RepeatValueAnimator
: ValueAnimator(), Animator.AnimatorListener {

    private var mIsRunning = false

    var repeatDelay = 300L

    override fun isRunning(): Boolean {
        return mIsRunning
    }

    init {
        addListener(
            this
        )
    }

    override fun cancel() {
        super.cancel()
        mIsRunning = false
    }

    override fun start() {
        super.start()
        mIsRunning = true
    }

    override fun onAnimationStart(
        animation: Animator
    ) {

    }

    override fun onAnimationEnd(
        animation: Animator
    ) {
        if (mIsRunning) {
            startDelay = repeatDelay
            super.start()
        }
    }

    override fun onAnimationCancel(
        animation: Animator
    ) = Unit

    override fun onAnimationRepeat(
        animation: Animator
    ) = Unit

}