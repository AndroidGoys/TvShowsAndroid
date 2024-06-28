package good.damn.tvlist.fragments.animation

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import good.damn.tvlist.fragments.StackFragment

data class FragmentAnimation(
    val duration: Int = 350,
    val interpolator: Interpolator = LinearInterpolator(),
    val onFrameUpdate: ((Float, StackFragment)->Unit)
)