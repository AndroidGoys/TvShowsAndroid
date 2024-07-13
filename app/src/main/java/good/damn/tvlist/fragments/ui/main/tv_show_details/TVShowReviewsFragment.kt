package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.models.tv_show.TVShowReview
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle

class TVShowReviewsFragment
: StackFragment() {

    companion object {
        fun newInstance(
            review: TVShowReview?
        ) = TVShowReviewsFragment().apply {
            this.review = review
        }
    }

    var review: TVShowReview? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val layout = FrameLayout(
            context
        )

        val topBar = TopBarView(
            context
        ).apply {
            defaultTopBarStyle(
                measureUnit,
                getTopInset()
            )

            textViewTitle.text = review?.title

            btnBack.setOnClickListener(
                this@TVShowReviewsFragment::onClickBtnBack
            )
        }

        val recyclerView = RecyclerView(
            context
        )

        recyclerView.apply {
            setBackgroundColorId(
                R.color.background
            )

            clipToPadding = false

            val pad = topBar.heightParams()
            setPadding(
                0,
                pad,
                0,
                pad
            )
        }

        layout.apply {
            addView(recyclerView)
            addView(topBar)
        }

        return layout
    }
}

private fun TVShowReviewsFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.x = f * -App.WIDTH
        }
    )
}