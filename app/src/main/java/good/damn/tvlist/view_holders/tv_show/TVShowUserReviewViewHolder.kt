package good.damn.tvlist.view_holders.tv_show

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.utils.ViewUtils

class TVShowUserReviewViewHolder(
    private val mUserHeader: View,
    private val mTextViewUserReview: AppCompatTextView,
    layout: LinearLayout
): RecyclerView.ViewHolder(
    layout
) {
    companion object {
        fun create(
            context: Context,
            holderWidth: Int,
            holderHeight: Int
        ): TVShowUserReviewViewHolder {
            val layout = ViewUtils.verticalLinear(
                context
            )
            val userHeader = View(
                context
            )
            val textViewReview = AppCompatTextView(
                context
            )


            return TVShowUserReviewViewHolder(
                userHeader,
                textViewReview,
                layout
            )
        }
    }
}