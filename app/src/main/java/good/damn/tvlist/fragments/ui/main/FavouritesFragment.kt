package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVProgramsAdapter
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.recycler_views.TVProgramsRecyclerView
import kotlinx.coroutines.launch

class FavouritesFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
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

        App.IO.launch {
            val arr = App.FAVOURITE_TV_SHOWS.values.toTypedArray()
            App.ui {
                recyclerView.adapter = recyclerView.adapterPrograms
                recyclerView.programs = arr
            }
        }

        return recyclerView
    }

}