package good.damn.tvlist.fragments.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.annotation.StringRes
import androidx.annotation.WorkerThread
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.children
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.accessToken
import good.damn.tvlist.extensions.asteriskMask
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.isEmail
import good.damn.tvlist.extensions.isStrongPassword
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
: StackFragment(),
OnAuthListener {

    var onAuthListener: OnAuthListener? = null

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

            gravity = Gravity.CENTER

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

            gravity = Gravity.CENTER

            singleLined()
            asteriskMask()

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

            gravity = Gravity.CENTER

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

        if (!email.isEmail()) {
            toast(R.string.it_not_email)
            return
        }

        val stringId = password
            .isStrongPassword()
            .errorStringId

        if (stringId != -1) {
            toast(stringId)
            return
        }

        enableInteraction(
            false
        )

        mBtnSignIn?.apply {
            text = getString(
                R.string.checking
            )
            recalculateTextPosition()
            invalidate()
        }

        App.IO.launch {
            processSignInRequest(
                email,
                password,
                username
            )
        }
    }

    @WorkerThread
    private fun processSignInRequest(
        email: String,
        password: String,
        username: String
    ) {
        val tokenAuthResult = mAuthService.registerUser(
            email,
            password,
            username
        )

        val tokenAuth = tokenAuthResult.result

        if (tokenAuth == null) {
            App.ui {
                mBtnSignIn?.apply {
                    text = getString(
                        R.string.sign_in
                    )
                    enableInteraction(true)
                    recalculateTextPosition()
                    invalidate()
                }
                val errorId = tokenAuthResult
                    .errorStringId

                if (errorId == -1) {
                    toast(R.string.some_error_happens)
                    return@ui
                }
                toast(errorId)
            }
            return
        }

        mSharedPreferences?.edit()?.apply {
            accessToken(tokenAuth.accessToken)
            refreshToken(tokenAuth.refreshToken)
            commit() // sync write
        }

        App.TOKEN_AUTH = tokenAuth

        App.ui {
            mBtnSignIn?.apply {
                text = getString(
                    R.string.success
                )
                recalculateTextPosition()
                invalidate()
            }
            onAuthListener?.onAuthSuccess()
        }
    }

    private fun onClickTextViewHaveAccount(
        v: View
    ) {
        pushFragment(
            LoginFragment().apply {
                onAuthListener = this@SigninFragment
            },
            FragmentAnimation { f, fragment ->
                fragment.view?.alpha = f
            }
        )
    }

    override fun onAuthSuccess() {
        enableInteraction(false)
        popFragment(
            FragmentAnimation(
                duration = 150
            ) { f, fragment ->
                fragment.view?.alpha = 1.0f - f
            }
        )

        // Fix this stick in wheel with onAnimationEnd
        Handler(
            Looper.getMainLooper()
        ).postDelayed({
            onAuthListener?.onAuthSuccess()
        }, 500)
    }

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