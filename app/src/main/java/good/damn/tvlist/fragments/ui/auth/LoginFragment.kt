package good.damn.tvlist.fragments.ui.auth

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.singleLined
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.text_fields.TextFieldRound

class LoginFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val layout = FrameLayout(
            context
        ).apply {
            setBackgroundColorId(
                R.color.background
            )
        }

        val contentLayout = ViewUtils.verticalLinear(
            context
        ).apply {
            setBackgroundColorId(
                R.color.background
            )

            boundsFrame(
                Gravity.CENTER_VERTICAL,
                width = measureUnit
            )
        }

        val btnBack = ButtonBack.createDefault(
            context
        ).apply {

            val s = (measureUnit * 0.11f).toInt()
            boundsFrame(
                left = measureUnit * 0.0386f,
                width = s,
                height = s,
                top = getTopInset().toFloat()
            )

            setOnClickListener(
                this@LoginFragment::onClickBtnBack
            )
        }

        AppCompatTextView(
            context
        ).apply {

            typeface = App.font(
                R.font.open_sans_extra_bold,
                context
            )

            setTextSizePx(
                measureUnit * 28.normalWidth()
            )

            gravity = Gravity.CENTER_HORIZONTAL

            setText(
                R.string.welcome_back
            )

            setTextColorId(
                R.color.text
            )

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL or
                    Gravity.TOP
            )

            contentLayout.addView(
                this
            )
        }

        TextFieldRound(
            context
        ).apply {

            strokeColor = App.color(
                R.color.lime
            )

            typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            setTextColorId(
                R.color.text
            )

            setHint(
                R.string.email
            )

            setHintTextColor(
                App.color(
                    R.color.text
                )
            )

            gravity = Gravity.CENTER_HORIZONTAL

            singleLined()

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP,
                width = (measureUnit * 338.normalWidth()).toInt(),
                height = (measureUnit * 47.normalWidth()).toInt()
            )

            strokeWidth = heightParams() * 0.04255f

            setTextSizePx(
                heightParams() * 0.32608f
            )

            contentLayout.addView(
                this
            )

        }

        TextFieldRound(
            context
        ).apply {

            strokeColor = App.color(
                R.color.lime
            )

            typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            setTextColorId(
                R.color.text
            )

            setHint(
                R.string.password
            )

            setHintTextColor(
                App.color(
                    R.color.text
                )
            )

            gravity = Gravity.CENTER_HORIZONTAL

            singleLined()

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP,
                width = (measureUnit * 338.normalWidth()).toInt(),
                height = (measureUnit * 47.normalWidth()).toInt()
            )

            strokeWidth = heightParams() * 0.04255f

            setTextSizePx(
                heightParams() * 0.32608f
            )

            contentLayout.addView(
                this
            )

        }

        layout.apply {
            addView(contentLayout)
            addView(btnBack)
        }

        return layout
    }

}

private fun LoginFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f-f
        }
    )
    v.isEnabled = false
}