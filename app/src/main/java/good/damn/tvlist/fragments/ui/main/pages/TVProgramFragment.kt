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
import androidx.recyclerview.widget.RecyclerView.Recycler
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
        private const val UPDATE_COUNT = 8
    }

    private lateinit var mChannelService: TVChannelsService
    private var mRecyclerView: TVChannelsRecyclerView? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        mChannelService = TVChannelsService(
            context.cacheDir
        )

        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val stream = StreamScrollListener(
            layoutManager,
            (App.HEIGHT * 0.31256f).toInt()
        )

        mRecyclerView = TVChannelsRecyclerView(
            context,
            stream
        )

        stream.deltaPositionStream = 4
        stream.updateCount = UPDATE_COUNT

        mRecyclerView?.apply {
            setBackgroundColorId(
                R.color.background
            )

            this.layoutManager = layoutManager
            isEnabledStreaming = true

            clipToPadding = false
            (measureUnit * 0.17149f).toInt().let {
                    padding ->
                setPadding(
                    0,
                    padding + getTopInset(),
                    0,
                    padding
                )
            }
        }

        initChannels()

        return mRecyclerView!!
    }

    override fun onNetworkConnected() {
        if (mRecyclerView?.adapterChannels == null) {
            initChannels()
        }
    }


    private fun initChannels() {
        if (mRecyclerView == null) {
            return
        }

        mChannelService.getChannels(
            from = 1,
            limit = UPDATE_COUNT
        ) {
            Log.d(TAG, "onCreateView: CHANNELS: ${it.size}")
            App.ui {
                mRecyclerView?.adapterChannels = TVChannelAdapter(
                    App.WIDTH,
                    App.HEIGHT,
                    it
                )
            }
        }
    }
}