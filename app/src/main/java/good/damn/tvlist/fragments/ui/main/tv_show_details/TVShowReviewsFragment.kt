package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.tv_show.TVShowUserReviewsAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.rgba
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.models.tv_show.TVShowReview
import good.damn.tvlist.network.api.services.TVShowService
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.tab_bars.TabBar
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

    private var mBlurView: BlurShaderView? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val layout = FrameLayout(
            context
        )

        val tabBarFilter = TabBar(
            context
        ).apply {
            setBackgroundColor(0)

            boundsFrame(
                Gravity.BOTTOM,
                width = measureUnit,
                height = (measureUnit * 31.normalWidth()).toInt()
            )

            interval = measureUnit * 14.normalWidth()

            setPadding(
                0,
                0,
                interval.toInt(),
                0
            )

            addTab("Все")
            addTab("5", R.drawable.ic_star_out)
            addTab("4", R.drawable.ic_star_out)
            addTab("3", R.drawable.ic_star_out)
            addTab("2", R.drawable.ic_star_out)
            addTab("1", R.drawable.ic_star_out)

        }

        val topBar = TopBarView(
            context,
            getTopInset()
        ).apply {
            defaultTopBarStyle(
                measureUnit,
                getTopInset()
            )

            boundsFrame(
                width = widthParams(),
                height = heightParams() + tabBarFilter.heightParams(),
            )

            textViewTitle.text = review?.title

            btnBack.setOnClickListener(
                this@TVShowReviewsFragment::onClickBtnBack
            )

            addView(tabBarFilter)
        }

        val recyclerView = RecyclerView(
            context
        ).apply {
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

        mBlurView = BlurShaderView(
            context,
            recyclerView,
            6,
            0.33f,
            shadeColor = App.color(
                R.color.background
            ).withAlpha(
                0.5f
            ).rgba()
        ).apply {

            boundsFrame(
                width = topBar.widthParams(),
                height = topBar.heightParams()
            )
            topBar.addView(this,0)

            startRenderLoop()
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
            setBackgroundColorId(
                R.color.background
            )
            addView(recyclerView)
            addView(topBar)
        }

        return layout
    }


    override fun onResume() {
        super.onResume()
        mBlurView?.apply {
            startRenderLoop()
            onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        mBlurView?.apply {
            stopRenderLoop()
            onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBlurView?.clean()
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