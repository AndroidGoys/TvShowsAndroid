package good.damn.tvlist.models

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

data class AnimationConfig(
    val duration: Long,
    val interpolator: Interpolator = LinearInterpolator()
)