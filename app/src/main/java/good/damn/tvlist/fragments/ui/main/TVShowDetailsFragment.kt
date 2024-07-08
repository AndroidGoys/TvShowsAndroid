package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.size
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack

class TVShowDetailsFragment
: StackFragment() {

    var program: TVProgram? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = FrameLayout(
            context
        )
        val layoutTopBar = FrameLayout(
            context
        )
        val contentLayout = ViewUtils.verticalLinear(
            context
        )
        val scrollView = ScrollView(
            context
        )

        val textViewTitle = TextView(
            context
        )

        ViewUtils.topBarStyleMain(
            layoutTopBar,
            measureUnit
        )

        ButtonBack.createFrame(
            context,
            measureUnit
        ).apply {

            ViewUtils.topBarStyleBtnBack(
                layoutTopBar,
                this
            )

            setOnClickListener(
                this@TVShowDetailsFragment::onClickBtnBack
            )

            layoutTopBar.addView(
                this
            )
        }


        textViewTitle.apply {
            ViewUtils.topBarStyleTitle(
                layoutTopBar,
                this
            )

            text = program?.shortName ?: program?.name

            layoutTopBar.addView(
                this
            )
        }












        scrollView.addView(
            contentLayout
        )

        layout.apply {
            size(-1, -1)
            setBackgroundColorId(
                R.color.background
            )
            addView(scrollView)
            addView(layoutTopBar)
        }

        return layout
    }

    companion object {
        fun newInstance(
            prog: TVProgram
        ) = TVShowDetailsFragment().apply {
            program = prog
        }
    }

}

private fun TVShowDetailsFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.scaleX = 1.0f-f
        }
    )
}