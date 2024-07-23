package good.damn.tvlist.fragments.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.WorkerThread
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.accessToken
import good.damn.tvlist.extensions.asteriskMask
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.refreshToken
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.singleLined
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.network.api.services.AuthService
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.buttons.RoundButton
import good.damn.tvlist.views.text_fields.TextFieldRound
import kotlinx.coroutines.launch

class LoginFragment
: StackFragment() {

    var onAuthListener: OnAuthListener? = null

    private var mTextFieldEmail: TextFieldRound? = null
    private var mTextFieldPassword: TextFieldRound? = null

    private var mBtnLogin: RoundButton? = null

    private val mAuthService = AuthService()

    private var mShared: SharedPreferences? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        mShared = sharedStorage()

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

            text = getString(
                R.string.welcome_back
            ) + "\n${getString(R.string.app_name)}"

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

        mBtnLogin = RoundButton(
            context
        ).apply {

            text = getString(
                R.string.login
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
                this@LoginFragment::onClickBtnLogin
            )

            cornerRadius = heightParams() * 0.5f

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

    private fun onClickBtnLogin(
        v: View
    ) {
        val password = mTextFieldPassword
            ?.text
            ?.toString()

        val email = mTextFieldEmail
            ?.text
            ?.toString()

        if (email.isNullOrBlank()) {
            toast(R.string.email_empty)
            return
        }

        if (password.isNullOrBlank()) {
            toast(R.string.password_empty)
            return
        }

        enableInteraction(false)

        App.IO.launch {
            processLoginRequest(
                email,
                password
            )
        }
    }

    @WorkerThread
    private fun processLoginRequest(
        email: String,
        password: String
    ) {
        val result = mAuthService.login(
            email,
            password
        )

        val tokenAuth = result.result

        if (tokenAuth == null) {
            App.ui {
                mBtnLogin?.apply {
                    text = getString(
                        R.string.login
                    )
                    recalculateTextPosition()
                    invalidate()
                }
                enableInteraction(true)

                if (result.errorStringId == -1) {
                    toast(R.string.some_error_happens)
                    return@ui
                }
                toast(result.errorStringId)
            }
            return
        }

        mShared?.edit()?.apply {
            accessToken(tokenAuth.accessToken)
            refreshToken(tokenAuth.refreshToken)
            commit() // synchronous write to share storage
        }

        App.TOKEN_AUTH = tokenAuth

        App.ui {
            mBtnLogin?.apply {
                text = getString(
                    R.string.success
                )
                recalculateTextPosition()
                invalidate()
            }

            onAuthListener?.onAuthSuccess()
        }
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