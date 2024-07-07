package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVProgramsAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
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


        val layoutTopBar = FrameLayout(
            context
        )
        val btnBack = ButtonBack.createDefault(
            context
        )
        val textViewTitle = TextView(
            context
        )


        val recyclerView = TVProgramsRecyclerView(
            context
        )

        layout.setBackgroundColorId(
            R.color.background
        )

        layoutTopBar.apply {
            setBackgroundColor(
                App.color(
                    R.color.background
                ).withAlpha(0.5f)
            )

            boundsFrame(
                width = measureUnit,
                height = (measureUnit * 0.19324f).toInt()
            )
        }

        btnBack.apply {
            val size = (
                layoutTopBar.heightParams() * 0.45f
            ).toInt()

            boundsFrame(
                gravity = Gravity.CENTER_VERTICAL or Gravity.START,
                left = layoutTopBar.widthParams() * 0.03864f,
                width = size,
                height = size
            )
        }

        textViewTitle.apply {
            setTextSizePx(
                layoutTopBar.heightParams() * 0.325f
            )

            setText(
                R.string.favourites
            )

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            setTextColorId(
                R.color.text
            )

            boundsFrame(
                gravity = Gravity.CENTER_VERTICAL or(
                    Gravity.CENTER_HORIZONTAL
                )
            )
        }

        recyclerView.apply {
            setBackgroundColor(0)
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
            clipToPadding = false
            val pad = layoutTopBar.heightParams()
            setPadding(
                0,
                pad,
                0,
                pad
            )
        }

        layoutTopBar.apply {
            addView(btnBack)
            addView(textViewTitle)
        }

        layout.apply {
            addView(recyclerView)
            addView(layoutTopBar)
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