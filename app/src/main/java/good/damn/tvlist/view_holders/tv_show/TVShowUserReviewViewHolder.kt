package good.damn.tvlist.view_holders.tv_show

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.UserHeaderView

class TVShowUserReviewViewHolder(
    private val mUserHeader: UserHeaderView,
    private val mTextViewUserReview: AppCompatTextView,
    layout: LinearLayout
): RecyclerView.ViewHolder(
    layout
) {
    companion object {
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

                typefaceRating = App.font(
                    R.font.open_sans_semi_bold,
                    context
                )

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