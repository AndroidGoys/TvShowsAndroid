package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle

class TVShowPostReviewFragment
: StackFragment() {

    var tvShow: TVShowPost? = null

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

            btnBack.setOnClickListener(
                this@TVShowPostReviewFragment::onClickBtnBack
            )

            layout.addView(
                this
            )
        }

        return layout
    }

    companion object {
        private const val TAG = "TVShowPostReviewFragmen"
        fun newInstance(
            tvShow: TVShowPost?
        ) = TVShowPostReviewFragment().apply {
            this.tvShow = tvShow
        }
    }

    data class TVShowPost(
        val showId: Long,
        val title: String? = null
    )
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