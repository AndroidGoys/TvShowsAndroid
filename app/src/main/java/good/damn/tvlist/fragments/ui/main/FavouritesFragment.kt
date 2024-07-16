package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVProgramsAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.pause
import good.damn.tvlist.extensions.resume
import good.damn.tvlist.extensions.rgba
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.recycler_views.TVProgramsRecyclerView
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle
import kotlinx.coroutines.launch
import java.io.Closeable

class FavouritesFragment
: StackFragment() {

    companion object {
        private const val TAG = "FavouritesFragment"
    }
    
    private var mBlurView: BlurShaderView? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = FrameLayout(
            context
        )

        val recyclerView = TVProgramsRecyclerView(
            context
        )

        val topBar = TopBarView(
            context,
            getTopInset()
        ).apply {
            defaultTopBarStyle(
                measureUnit,
                getTopInset()
            )

            btnBack.setOnClickListener(
                this@FavouritesFragment::onClickBtnBack
            )

            textViewTitle.setText(
                R.string.favourites
            )
        }

        mBlurView = BlurShaderView(
            context,
            recyclerView,
            5,
            0.35f,
            shadeColor = App.color(
                R.color.background
            ).withAlpha(
                0.5f
            ).rgba()
        )


        layout.setBackgroundColorId(
            R.color.background
        )



        recyclerView.apply {
            setBackgroundColorId(
                R.color.background
            )
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
            val pad = topBar.heightParams()
            setPadding(
                0,
                pad,
                0,
                pad
            )
        }

        mBlurView?.apply {
            boundsFrame(
                width = topBar.widthParams(),
                height = topBar.heightParams()
            )
            topBar.addView(
                this,
                0
            )

            startRenderLoop()
        }


        layout.apply {
            addView(recyclerView)
            addView(topBar)
        }


        App.IO.launch {
            val arr = App.FAVOURITE_TV_SHOWS.values.toTypedArray()
            App.ui {
                recyclerView.adapter = recyclerView.adapterPrograms
                recyclerView.programs = arr
            }
        }


        return layout
    }

    override fun onFocusChanged(
        isFragmentFocused: Boolean
    ) {
        super.onFocusChanged(
            isFragmentFocused
        )

        mBlurView?.apply {
            if (isFragmentFocused) {
                resume()
                return@apply
            }

            pause()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBlurView?.clean()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")

        if (isFragmentFocused()) {
            mBlurView?.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        if (isFragmentFocused()) {
            mBlurView?.pause()
        }
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