package good.damn.tvlist.fragments.ui.main.pages

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVChannelAdapter
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.network.api.models.TVChannel
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.network.api.models.enums.CensorAge
import good.damn.tvlist.network.api.services.TVChannelsService
import good.damn.tvlist.views.recycler_views.TVChannelsRecyclerView
import good.damn.tvlist.views.recycler_views.scroll_listeners.StreamScrollListener

class TVProgramFragment
: StackFragment() {

    companion object {
        private const val TAG = "TVProgramFragment"
    }

    private val mChannelService = TVChannelsService()

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val stream = StreamScrollListener(
            layoutManager,
            (App.HEIGHT * 0.31256f).toInt()
        )

        val recyclerView = TVChannelsRecyclerView(
            context,
            stream
        )

        stream.deltaPositionStream = 4
        stream.updateCount = 8

        recyclerView.setBackgroundColorId(
            R.color.background
        )

        recyclerView.layoutManager = layoutManager
        recyclerView.isEnabledStreaming = true

        recyclerView.clipToPadding = false
        (measureUnit * 0.17149f).toInt().let {
            padding ->
            recyclerView.setPadding(
                0,
                padding + getTopInset(),
                0,
                padding
            )
        }

        mChannelService.getChannels(
            from = 1,
            limit = stream.updateCount
        ) {
            Log.d(TAG, "onCreateView: CHANNELS: ${it.size}")
            App.ui {
                recyclerView.adapterChannels = TVChannelAdapter(
                    App.WIDTH,
                    App.HEIGHT,
                    it
                )
            }
        }

        return recyclerView
    }

}