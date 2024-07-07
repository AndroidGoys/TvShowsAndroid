package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.BigButtonView
import good.damn.tvlist.views.buttons.ButtonBack
import java.io.Closeable

class ProfileFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )

        ButtonBack.createLinear(
            context,
            measureUnit
        ).apply {

            setOnClickListener(
                this@ProfileFragment::onClickBtnBack
            )

            layout.addView(
                this
            )
        }

        TextView(
            context
        ).apply {

            setTextSizePx(
                measureUnit * 0.072463f
            )

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            setText(
                R.string.profile
            )

            setTextColorId(
                R.color.text
            )

            boundsLinear(
                left = measureUnit * 0.03865f,
                top = measureUnit * 0.08057f
            )

            layout.addView(
                this
            )
        }

        BigButtonView(
            context
        ).apply {

            text = getString(
                R.string.settings
            )

            textColor = App.color(
                R.color.text
            )

            setCardBackgroundColor(
                App.color(
                    R.color.background
                )
            )

            typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            imageStart = App.drawable(
                R.drawable.ic_gear
            )

            imageEnd = App.drawable(
                R.drawable.ic_arrow_forward
            )

            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                top = measureUnit * 0.072463f,
                width = (measureUnit * 0.9227f).toInt(),
                height = (measureUnit * 0.186f).toInt()
            )

            radius = heightParams() * 0.181818f
            cardElevation = heightParams() * 0.07f

            imageStartSizeFactor = 0.394736f
            imageEndSizeFactor = 0.31579f
            textSizeFactor = 0.22368f

            layout.addView(
                this
            )
        }

        return layout
    }

}

private fun ProfileFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f-f
        }
    )
}