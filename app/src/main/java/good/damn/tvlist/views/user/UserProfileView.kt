package good.damn.tvlist.views.user

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.FrameLayout
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.toGregorianDateString
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.network.api.models.user.UserProfile
import good.damn.tvlist.views.round.RoundedImageView

class UserProfileView(
    context: Context
): FrameLayout(
    context
) {

    val mImageViewProfile = RoundedImageView(
        context
    ).apply {
        addView(
            this
        )
    }

    private val mUserInfoView = UserInfoView(
        context
    ).apply {
        addView(
            this
        )
    }

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        super.setLayoutParams(
            params
        )
        if (params == null) {
            return
        }

        val width = params.width
        val height = params.height

        mImageViewProfile.apply {
            boundsFrame(
                width = height,
                height = height
            )

            cornerRadius = height * 0.5f
            setBackgroundColor(
                0xaaaaaaaa.toInt()
            )
        }

        mUserInfoView.boundsFrame(
            width = (width * 0.7173f).toInt(),
            height = height,
            left = mImageViewProfile.widthParams()
                + (width * 0.0893f)
        )
    }

    fun setUserInfo(
        profile: UserProfile
    ) {
        mUserInfoView.apply {
            nickname = profile.username
            email = profile.email
            date = profile.registrationDateSeconds
                .toGregorianDateString()
            invalidate()
        }
    }

    fun setAvatar(
        bitmap: Bitmap?
    ) {
        mImageViewProfile.apply {
            this.bitmap = bitmap
            invalidate()
        }
    }

}