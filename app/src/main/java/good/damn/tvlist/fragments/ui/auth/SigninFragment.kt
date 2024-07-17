package good.damn.tvlist.fragments.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.accessToken
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.refreshToken
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.singleLined
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.network.api.services.AuthService
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.buttons.RoundButton
import good.damn.tvlist.views.text_fields.TextFieldRound
import kotlinx.coroutines.launch

class SigninFragment
: StackFragment() {

    private val mAuthService = AuthService()

    private var mTextFieldUsername: TextFieldRound? = null
    private var mTextFieldEmail: TextFieldRound? = null
    private var mTextFieldPassword: TextFieldRound? = null

    private var mBtnSignIn: RoundButton? = null

    private var mSharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        mSharedPreferences = sharedStorage()

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
                this@SigninFragment::onClickBtnBack
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
                R.string.lets_sign_in
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

        mTextFieldUsername = TextFieldRound(
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
                R.string.username
            )

            setHintTextColor(
                App.color(
                    R.color.text
                ).withAlpha(0.3f)
            )

            gravity = Gravity.CENTER_HORIZONTAL

            singleLined()

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP,
                width = (measureUnit * 338.normalWidth()).toInt(),
                height = (measureUnit * 50.normalWidth()).toInt(),
                top = measureUnit * 31.normalWidth()
            )

            cornerRadius = heightParams() * 0.5f

            strokeWidth = heightParams() * 0.04255f

            setTextSizePx(
                heightParams() * 0.32608f
            )

            contentLayout.addView(
                this
            )

        }

        mTextFieldEmail = TextFieldRound(
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
                ).withAlpha(0.3f)
            )

            gravity = Gravity.CENTER_HORIZONTAL

            singleLined()

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP,
                width = (measureUnit * 338.normalWidth()).toInt(),
                height = (measureUnit * 50.normalWidth()).toInt(),
                top = measureUnit * 19.normalWidth()
            )

            cornerRadius = heightParams() * 0.5f

            strokeWidth = heightParams() * 0.04255f

            setTextSizePx(
                heightParams() * 0.32608f
            )

            contentLayout.addView(
                this
            )

        }

        mTextFieldPassword = TextFieldRound(
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
                ).withAlpha(0.3f)
            )

            gravity = Gravity.CENTER_HORIZONTAL

            singleLined()

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP,
                width = (measureUnit * 338.normalWidth()).toInt(),
                height = (measureUnit * 50.normalWidth()).toInt(),
                top = measureUnit * 19.normalWidth()
            )

            cornerRadius = heightParams() * 0.5f

            strokeWidth = heightParams() * 0.04255f

            setTextSizePx(
                heightParams() * 0.32608f
            )

            contentLayout.addView(
                this
            )

        }

        AppCompatTextView(
            context
        ).apply {

            typeface = App.font(
                R.font.open_sans_regular,
                context
            )

            setTextSizePx(
                measureUnit * 13.normalWidth()
            )

            setTextColorId(
                R.color.text
            )

            setText(
                R.string.mask_password
            )

            gravity = Gravity.CENTER_HORIZONTAL

            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                top = measureUnit * 8.normalWidth()
            )

            contentLayout.addView(
                this
            )
        }

        mBtnSignIn = RoundButton(
            context
        ).apply {

            text = getString(
                R.string.sign_in
            )

            textColor = App.color(
                R.color.btnText
            )

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            textSizeFactor = 0.28301f

            setBackgroundColorId(
                R.color.btnBack
            )

            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                top = measureUnit * 42.normalWidth(),
                height = (measureUnit * 53.normalWidth()).toInt(),
                width = (measureUnit * 383.normalWidth()).toInt()
            )

            setOnClickListener(
                this@SigninFragment::onClickBtnSignIn
            )

            cornerRadius = heightParams() * 0.5f

            contentLayout.addView(
                this
            )
        }

        AppCompatTextView(
            context
        ).apply {

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            setTextSizePx(
                measureUnit * 15.normalWidth()
            )

            setTextColorId(
                R.color.text
            )

            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                top = measureUnit * 19.normalWidth()
            )

            setText(
                R.string.already_have_an_account
            )

            setOnClickListener(
                this@SigninFragment::onClickTextViewHaveAccount
            )

            contentLayout.addView(
                this
            )
        }


        layout.apply {
            size(
                width = App.WIDTH,
                height = App.HEIGHT
            )

            addView(contentLayout)
            addView(btnBack)
        }

        return layout
    }

    private fun onClickBtnSignIn(
        v: View
    ) {
        val username = mTextFieldUsername
            ?.text
            ?.toString()

        val email = mTextFieldEmail
            ?.text
            ?.toString()

        val password = mTextFieldPassword
            ?.text
            ?.toString()

        if (username.isNullOrBlank()) {
            toast(R.string.username_empty)
            return
        }

        if (email.isNullOrBlank()) {
            toast(R.string.email_empty)
            return
        }

        if (password.isNullOrBlank()) {
            toast(R.string.password_empty)
            return
        }

        v.isEnabled = false

        mBtnSignIn?.apply {
            text = getString(
                R.string.checking
            )
            invalidate()
        }

        App.IO.launch {
            val tokenAuthResult = mAuthService.registerUser(
                email,
                password,
                username
            )

            if (tokenAuthResult.result == null) {
                App.ui {
                    mBtnSignIn?.apply {
                        text = getString(
                            R.string.sign_in
                        )
                        invalidate()
                    }

                    val errorId = tokenAuthResult
                        .errorStringId

                    if (errorId == -1) {
                        return@ui
                    }
                    toast(errorId)
                }
                return@launch
            }

            val tokenAuth = tokenAuthResult.result
            mSharedPreferences?.edit()?.apply {
                accessToken(tokenAuth.accessToken)
                refreshToken(tokenAuth.refreshToken)
                commit() // sync write
            }


        }
    }
}

private fun SigninFragment.onClickTextViewHaveAccount(
    v: View
) {
    pushFragment(
        LoginFragment(),
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = f
        }
    )
}

private fun SigninFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f-f
        }
    )
}