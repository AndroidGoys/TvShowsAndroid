package good.damn.tvlist.views

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.FrameLayout
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.views.round.RoundedImageView

class UserProfileView(
    context: Context
): FrameLayout(
    context
) {

    private val mImageViewProfile = RoundedImageView(
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