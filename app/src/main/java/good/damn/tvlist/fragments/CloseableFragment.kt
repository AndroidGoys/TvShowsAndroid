package good.damn.tvlist.fragments

import android.content.Context
import android.view.View
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.animators.FragmentAnimator
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.topParams
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.round.RoundedImageView

abstract class CloseableFragment
: StackFragment() {

    final override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )
        val btnBack = ButtonBack(
            context
        )

        btnBack.strokeColor = App.color(
            R.color.bigButtonIcon
        )

        (measureUnit * 0.08695f).toInt().let { size ->
            btnBack.boundsLinear(
                width = size,
                height = size,
                left = 0.03864f * measureUnit,
                top = 0.06038f * measureUnit
            )
        }


        layout.apply {
            addView(btnBack)
            addView(
                onCreateContentView(
                    context,
                    measureUnit
                )
            )
        }

        btnBack.setOnClickListener(
            this::onClickBtnBack
        )

        return layout
    }

    abstract fun onCreateContentView(
        context: Context,
        measureUnit: Int
    ): View
}

private fun CloseableFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.x = -App.WIDTH * f
        }
    )
}