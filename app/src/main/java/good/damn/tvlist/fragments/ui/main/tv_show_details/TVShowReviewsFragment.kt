package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.tv_show.TVShowUserReviewsAdapter
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.models.tv_show.TVShowReview
import good.damn.tvlist.network.api.services.TVShowService
import good.damn.tvlist.views.decorations.MarginItemDecoration
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

            boundsLinear(
                width = -1,
                height = -1
            )

            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            (measureUnit * 0.045895f).toInt().let {
                addItemDecoration(
                    MarginItemDecoration(
                        left = it,
                        top = it
                    )
                )
            }

            clipToPadding = false

            val pad = topBar.heightParams()
            setPadding(
                0,
                pad,
                0,
                pad
            )
        }

        review?.id?.let { showId ->
            TVShowService(
                App.CACHE_DIR
            ).getReviews(
                showId
            ) {
                recyclerView.adapter = TVShowUserReviewsAdapter(
                    (measureUnit * 0.90811f).toInt(),
                    it
                )
            }
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