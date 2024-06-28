package good.damn.tvlist.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import java.util.LinkedList

class GroupViewAnimation
: ValueAnimator.AnimatorUpdateListener,
Animator.AnimatorListener {

    var onFrameUpdate: ((Float, View)->Unit)? = null
    var onAnimationEnd: (()-> Unit)? = null

    var duration: Long = 0
        set(value) {
            field = value
            mAnimator.setDuration(
                value
            )
        }

    var interpolator: Interpolator = LinearInterpolator()
        set(v) {
            field = v
            mAnimator.interpolator = interpolator
        }

    private val mList = LinkedList<View>()
    private val mAnimator = ValueAnimator()

    init {

        mAnimator.setFloatValues(
            0.0f,1.0f
        )

        mAnimator.addUpdateListener(
            this
        )

        mAnimator.addListener(
            this
        )
    }

    fun addView(
        view: View
    ) = mList.add(view)

    fun start() {
        mAnimator.start()
    }

    override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        val f = animation.animatedValue as Float
        mList.forEach {
            onFrameUpdate?.invoke(
                f, it
            )
        }
    }

    override fun onAnimationEnd(
        animation: Animator
    ) = onAnimationEnd?.invoke() ?: Unit


    override fun onAnimationStart(
        animation: Animator
    ) = Unit

    override fun onAnimationCancel(
        animation: Animator
    ) = onAnimationEnd(animation)

    override fun onAnimationRepeat(
        animation: Animator
    ) = onAnimationEnd(animation)

}