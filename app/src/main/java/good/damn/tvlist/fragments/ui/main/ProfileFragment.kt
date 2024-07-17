package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.auth.SigninFragment
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.BigButtonView
import good.damn.tvlist.views.buttons.ButtonBack

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
            defaultBigButton(
                this,
                R.string.settings,
                R.drawable.ic_gear,
                App.drawable(
                    R.drawable.ic_arrow_forward
                ),
                App.color(
                    R.color.text
                ),
                measureUnit,
                0.072463f,
                layout
            )
        }

        BigButtonView(
            context
        ).apply {
            defaultBigButton(
                this,
                R.string.about_app,
                R.drawable.ic_info,
                App.drawable(
                    R.drawable.ic_arrow_forward
                ),
                App.color(
                    R.color.text
                ),
                measureUnit,
                0.036231f,
                layout
            )
        }

        BigButtonView(
            context
        ).apply {
            defaultBigButton(
                this,
                R.string.rate_app,
                R.drawable.ic_star,
                App.drawable(
                    R.drawable.ic_arrow_forward
                ),
                App.color(
                    R.color.text
                ),
                measureUnit,
                0.036231f,
                layout
            )
        }

        BigButtonView(
            context
        ).apply {
            defaultBigButton(
                this,
                R.string.write_assessment,
                R.drawable.ic_message,
                null,
                App.color(
                    R.color.text
                ),
                measureUnit,
                0.036231f,
                layout
            )
        }

        BigButtonView(
            context
        ).apply {
            defaultBigButton(
                this,
                R.string.login,
                R.drawable.ic_logout_red,
                textColor = 0xffFF7E7E.toInt(),
                measureUnit = measureUnit,
                topFactor = 0.036231f,
                layout = layout
            )

            setOnClickListener(
                this@ProfileFragment::onClickLogIn
            )
        }


        return layout
    }

    private fun onClickLogIn(
        v: View
    ) {
        pushFragment(
            SigninFragment().apply {
                onSignInSuccess = this@ProfileFragment::onSignInSuccess
            },
            FragmentAnimation { f, fragment ->
                fragment.view?.alpha = f
            }
        )
    }

    private fun onSignInSuccess() {
        Handler(
            Looper.getMainLooper()
        ).postDelayed({
                popFragment()
            },
            1500
        )
    }

}

private fun ProfileFragment.defaultBigButton(
    btn: BigButtonView,
    @StringRes textId: Int,
    @DrawableRes imageStartId: Int,
    imageEnd: Drawable? = null,
    textColor: Int,
    measureUnit: Int,
    topFactor: Float,
    layout: LinearLayout
) {
    btn.apply {
        text = getString(
            textId
        )

        this.textColor = textColor

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
            imageStartId
        )

        this.imageEnd = imageEnd

        boundsLinear(
            Gravity.CENTER_HORIZONTAL,
            top = measureUnit * topFactor,
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