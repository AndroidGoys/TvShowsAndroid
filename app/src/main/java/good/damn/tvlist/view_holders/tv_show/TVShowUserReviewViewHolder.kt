package good.damn.tvlist.view_holders.tv_show

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.network.api.models.show.TVShowUserReview
import good.damn.tvlist.network.api.services.UserService
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.user.UserHeaderView
import kotlinx.coroutines.launch

class TVShowUserReviewViewHolder(
    private val mUserHeader: UserHeaderView,
    private val mTextViewUserReview: AppCompatTextView,
    layout: LinearLayout
): RecyclerView.ViewHolder(
    layout
) {

    fun onBindViewHolder(
        userReview: TVShowUserReview,
        userService: UserService
    ) {
        mUserHeader.textDate = userReview.dateString
        mUserHeader.grade = userReview.rating
        mUserHeader.invalidate()

        mTextViewUserReview.text = userReview.textReview

        App.IO.launch {
            val profile = userService.getProfile(
                userReview.userId,
                fromCache = !App.NETWORK_AVAILABLE
            )

            Log.d(TAG, "onBindViewHolder: ${profile?.username}")

            if (profile == null) {
                return@launch
            }

            App.ui {
                mUserHeader.textUsername =
                    profile.username
                mUserHeader.invalidate()

                if (profile.avatarUrl == null) {
                    return@ui
                }

                NetworkBitmap.loadFromNetwork(
                    profile.avatarUrl,
                    App.CACHE_DIR,
                    "userlogo",
                    mUserHeader.heightParams(),
                    mUserHeader.heightParams()
                ) {
                    mUserHeader.bitmap = it
                    mUserHeader.invalidate()
                }
            }
        }
    }

    companion object {
        private const val TAG = "TVShowUserReviewViewHol"
        fun create(
            context: Context,
            holderWidth: Int
        ): TVShowUserReviewViewHolder {
            val layout = ViewUtils.verticalLinear(
                context
            )


            val userHeader = UserHeaderView(
                context
            ).apply {

                boundsLinear(
                    width = holderWidth,
                    height = (holderWidth * 0.16489f).toInt()
                )

                textColor = App.color(
                    R.color.text
                )

                typefaceUsername = App.font(
                    R.font.open_sans_semi_bold,
                    context
                )

                typefaceDate = App.font(
                    R.font.open_sans_regular,
                    context
                )

                bitmapRadius = heightParams() * 0.2258f

                textSizeFUsername = 0.24193f
                textSizeFDate = 0.17742f

            }



            val textViewReview = AppCompatTextView(
                context
            ).apply {

                typeface = App.font(
                    R.font.open_sans_regular,
                    context
                )

                setTextSizePx(
                    userHeader.heightParams() * 0.21774f
                )

                setTextColor(
                    App.color(
                        R.color.text
                    ).withAlpha(
                        0.8f
                    )
                )

                boundsLinear(
                    width = holderWidth,
                    top = holderWidth * 0.04787f
                )

            }


            layout.apply {
                size(
                    holderWidth,
                    -2
                )
                addView(userHeader)
                addView(textViewReview)
            }

            return TVShowUserReviewViewHolder(
                userHeader,
                textViewReview,
                layout
            )
        }
    }
}