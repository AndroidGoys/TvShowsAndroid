package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.readBytes
import good.damn.tvlist.extensions.removeAccessToken
import good.damn.tvlist.extensions.removeRefreshToken
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.auth.OnAuthListener
import good.damn.tvlist.fragments.ui.auth.SigninFragment
import good.damn.tvlist.network.api.services.UserService
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.user.UserProfileView
import good.damn.tvlist.views.buttons.BigButtonView
import good.damn.tvlist.views.buttons.ButtonBack
import kotlinx.coroutines.launch

class ProfileFragment
: StackFragment(),
OnAuthListener {

    companion object {
        private const val TAG = "ProfileFragment"
    }

    var onAuthListener: OnAuthListener? = null

    private var mBtnLogout: BigButtonView? = null
    private var mBtnLogin: BigButtonView? = null

    private var mTextViewProfile: AppCompatTextView? = null
    private var mProfileView: UserProfileView? = null

    private var mLayout: LinearLayout? = null

    private val mUserService = UserService()

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

            mImageViewProfile.setOnClickListener(
                this@ProfileFragment::onClickProfilePickAvatar
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

        updateProfile()

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
                onAuthListener = this@ProfileFragment
            },
            FragmentAnimation { f, fragment ->
                fragment.view?.alpha = f
            }
        )
    }

    private fun updateProfile() {

        mUserService.updateAccessToken(
            App.TOKEN_AUTH?.accessToken ?: ""
        )

        if (App.TOKEN_AUTH == null) {
            return
        }

        App.IO.launch {
            val profile = mUserService.getProfile(
                fromCache = !App.NETWORK_AVAILABLE
            ) ?: return@launch

            App.ui {

                val profileView = mProfileView
                    ?: return@ui

                profileView.setUserInfo(
                    profile
                )

                profile.avatarUrl ?: return@ui

                val s = profileView.heightParams()

                NetworkBitmap.loadFromNetwork(
                    profile.avatarUrl,
                    App.CACHE_DIR,
                    "bitmapProfile",
                    s,
                    s
                ) {
                    profileView.setAvatar(it)
                }

                onAuthListener?.onAuthSuccess()
            }
        }
    }

    private fun updateLayoutAuthState() {
        val btnLogin = mBtnLogin
            ?: return

        val btnLogout = mBtnLogout
            ?: return

        val textViewProfile = mTextViewProfile
            ?: return

        val profileView = mProfileView
            ?: return

        mLayout?.apply {
            if (App.TOKEN_AUTH == null) {
                removeView(btnLogout)
                addView(btnLogin)

                removeView(profileView)
                addView(textViewProfile,1)
                return
            }

            removeView(btnLogin)
            addView(btnLogout)

            removeView(textViewProfile)
            addView(profileView,1)

            updateProfile()
        }
    }

    private fun onClickProfilePickAvatar(
        v: View
    ) {
        requestContentBrowser(
            "image/png"
        )
    }

    override fun onGetContentUri(
        uri: Uri?
    ) {
        if (uri == null) {
            return
        }

        try {
            val bytes  = context?.contentResolver?.openInputStream(
                uri
            )?.readBytes(4096)

            if (bytes == null) {
                return
            }

            App.IO.launch {
                val response = mUserService.uploadAvatar(
                    bytes
                )
            }

        } catch (e: Exception) {
            Log.d(TAG, "onGetContentUri: ERROR: ${e.message}")
        }

    }

    override fun onAuthSuccess() {
        popFragment(
            FragmentAnimation(
                150
            ) { f, fragment ->
                fragment.view?.alpha = 1.0f - f
            }
        )

        updateLayoutAuthState()
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