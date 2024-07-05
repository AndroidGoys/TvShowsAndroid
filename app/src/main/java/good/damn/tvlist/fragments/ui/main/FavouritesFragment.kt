package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import good.damn.tvlist.App
import good.damn.tvlist.adapters.recycler_view.TVProgramsAdapter
import good.damn.tvlist.extensions.boundsLinear
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

        recyclerView.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )

        recyclerView.addItemDecoration(
            MarginItemDecoration(
                left = (measureUnit * 0.05f).toInt()
            )
        )

        recyclerView.heightHolder = (App.HEIGHT * 0.18f).toInt()

        recyclerView.boundsLinear(
            width = measureUnit,
            height = App.HEIGHT
        )

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