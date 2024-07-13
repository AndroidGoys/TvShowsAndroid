package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.models.tv_show.TVShowReview
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle

class TVShowPostReviewFragment
: StackFragment() {

    companion object {
        private const val TAG = "TVShowPostReviewFragmen"
        fun newInstance(
            tvShow: TVShowReview?,
            grade: Byte
        ) = TVShowPostReviewFragment().apply {
            this.review = tvShow
            this.grade = grade
        }
    }

    var review: TVShowReview? = null
    var grade: Byte = 0

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )

        TopBarView(
            context,
            getTopInset()
        ).apply {
            defaultTopBarStyle(
                measureUnit,
                getTopInset()
            )

            textViewTitle.text = review?.title

            btnBack.setOnClickListener(
                this@TVShowPostReviewFragment::onClickBtnBack
            )

            layout.addView(
                this
            )
        }

        return layout
    }
}

private fun TVShowPostReviewFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f - f
        }
    )
}