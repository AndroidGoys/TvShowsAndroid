package good.damn.tvlist.animators

import android.animation.Animator
import android.animation.ValueAnimator
import androidx.fragment.app.Fragment
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation

class FragmentAnimator
: ValueAnimator(),
ValueAnimator.AnimatorUpdateListener,
Animator.AnimatorListener {

    var onAnimationEnd: (()->Unit)? = null

    private var mInAnimation: FragmentAnimation? = null
    private var mInFragment: StackFragment? = null

    private var mOutAnimation: FragmentAnimation? = null
    private var mOutFragment: StackFragment? = null

    init {
        setFloatValues(
            0.0f, 1.0f
        )
        addUpdateListener(
            this
        )

        addListener(
            this
        )
    }

    fun startTransition(
        inAnimation: FragmentAnimation? = null,
        inFragment: StackFragment? = null,
        outAnimation: FragmentAnimation? = null,
        outFragment: StackFragment? = null
    ) {

        mInFragment = inFragment
        mOutFragment = outFragment

        mInAnimation = inAnimation
        mOutAnimation = outAnimation

        start()
    }

    override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        val v = animation.animatedValue as Float

        mInFragment?.let {
            mInAnimation
                ?.onFrameUpdate
                ?.invoke(v, it)
        }

        mOutFragment?.let {
            mOutAnimation
                ?.onFrameUpdate
                ?.invoke(v,it)
        }
    }

    override fun onAnimationCancel(
        animation: Animator
    ) = Unit

    override fun onAnimationRepeat(
        animation: Animator
    ) = Unit

    override fun onAnimationStart(
        animation: Animator
    ) = Unit

    override fun onAnimationEnd(
        animation: Animator
    ) {
        onAnimationEnd?.invoke()
    }
}