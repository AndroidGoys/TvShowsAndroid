package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVProgramsAdapter
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.recycler_views.TVProgramsRecyclerView
import kotlinx.coroutines.launch
import java.io.Closeable

class FavouritesFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = FrameLayout(
            context
        )
        val btnBack = ButtonBack.createFrame(
            context,
            measureUnit
        )
        val recyclerView = TVProgramsRecyclerView(
            context
        )

        recyclerView.setBackgroundColorId(
            R.color.background
        )

        recyclerView.apply {
            layoutManager = GridLayoutManager(
                context,
                2,
                GridLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                MarginItemDecoration(
                    left = (measureUnit * 0.05f).toInt(),
                    top = (measureUnit * 0.05f).toInt()
                )
            )
            heightHolder = (measureUnit * 0.495169f).toInt()
            boundsLinear(
                width = measureUnit,
                height = App.HEIGHT
            )
        }

        layout.apply {
            addView(recyclerView)
            addView(btnBack)
        }

        App.IO.launch {
            val arr = App.FAVOURITE_TV_SHOWS.values.toTypedArray()
            App.ui {
                recyclerView.adapter = recyclerView.adapterPrograms
                recyclerView.programs = arr
            }
        }


        btnBack.setOnClickListener(
            this::onClickBtnBack
        )

        return layout
    }

}

private fun FavouritesFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.x = -App.WIDTH * f
        }
    )
}