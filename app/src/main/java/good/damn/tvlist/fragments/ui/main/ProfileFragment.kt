package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.removeAccessToken
import good.damn.tvlist.extensions.removeRefreshToken
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.auth.SigninFragment
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.UserProfileView
import good.damn.tvlist.views.buttons.BigButtonView
import good.damn.tvlist.views.buttons.ButtonBack

class ProfileFragment
: StackFragment() {

    private var mBtnLogout: BigButtonView? = null
    private var mBtnLogin: BigButtonView? = null

    private var mTextViewProfile: AppCompatTextView? = null
    private var mProfileView: UserProfileView? = null

    private var mLayout: LinearLayout? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        mLayout = ViewUtils.verticalLinear(
            context
        ).apply {
            setPadding(
                0,
                getTopInset(),
                0,
                0
            )
        }

        ButtonBack.createLinear(
            context,
            measureUnit
        ).apply {

            setOnClickListener(
                this@ProfileFragment::onClickBtnBack
            )


            mLayout?.addView(
                this
            )
        }

        mTextViewProfile = AppCompatTextView(
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

            if (App.TOKEN_AUTH == null) {
                mLayout?.addView(
                    this
                )
            }
        }

        mProfileView = UserProfileView(
            context
        ).apply {

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP,
                top = measureUnit * 0.08057f,
                width = (measureUnit * 336.normalWidth()).toInt(),
                height = (measureUnit * 70.normalWidth()).toInt()
            )

            if (App.TOKEN_AUTH != null) {
                mLayout?.addView(
                    this
                )
            }
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
                mLayout
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
                mLayout
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
                mLayout
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
                mLayout
            )
        }

        mBtnLogin = BigButtonView(
            context
        ).apply {
            defaultBigButton(
                this,
                R.string.login,
                R.drawable.ic_logout,
                textColor = App.color(
                    R.color.text
                ),
                measureUnit = measureUnit,
                topFactor = 0.036231f
            )
            setOnClickListener(
                this@ProfileFragment::onClickLogIn
            )
            if (App.TOKEN_AUTH == null) {
                mLayout?.addView(
                    this
                )
            }
        }

        mBtnLogout = BigButtonView(
            context
        ).apply {

            defaultBigButton(
                this,
                R.string.logout,
                R.drawable.ic_logout_red,
                textColor = 0xffFF7E7E.toInt(),
                measureUnit = measureUnit,
                topFactor = 0.036231f
            )

            setOnClickListener(
                this@ProfileFragment::onClickLogout
            )

            if (App.TOKEN_AUTH != null) {
                mLayout?.addView(
                    this
                )
            }
        }


        return mLayout ?: View(context)
    }

    private fun onClickLogout(
        v: View
    ) {
        sharedStorage().edit().apply {
            removeAccessToken()
            removeRefreshToken()
            apply()
        }
        App.TOKEN_AUTH = null
        updateLayoutAuthState()
    }

    private fun onClickLogIn(
        v: View
    ) {
        pushFragment(
            SigninFragment().apply {
                onSignInSuccess = this@ProfileFragment::onAuthSuccess
            },
            FragmentAnimation { f, fragment ->
                fragment.view?.alpha = f
            }
        )
    }

    private fun onAuthSuccess() {
        popFragment(
            FragmentAnimation(
                150
            ) { f, fragment ->
                fragment.view?.alpha = 1.0f - f
            }
        )

        updateLayoutAuthState()
    }

    private fun updateLayoutAuthState() {
        val btnLogin = mBtnLogin
            ?: return

        val btnLogout = mBtnLogout
            ?: return

        mLayout?.apply {
            if (App.TOKEN_AUTH == null) {
                removeView(btnLogout)
                addView(btnLogin)
                return
            }

            removeView(btnLogin)
            addView(btnLogout)
        }
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
    layout: LinearLayout? = null
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

        layout?.addView(
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