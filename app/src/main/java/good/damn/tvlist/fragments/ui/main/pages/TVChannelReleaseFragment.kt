package good.damn.tvlist.fragments.ui.main.pages

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVChannelAdapter
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.network.api.services.TVChannel2Service
import good.damn.tvlist.network.api.services.TVChannelReleasesService
import good.damn.tvlist.views.recycler_views.TVChannelsRecyclerView
import good.damn.tvlist.views.recycler_views.scroll_listeners.StreamScrollListener
import kotlinx.coroutines.launch

class TVChannelReleaseFragment
: StackFragment() {

    companion object {
        private const val TAG = "TVProgramFragment"
        private const val UPDATE_COUNT = 5
    }

    private val mChannelService = TVChannel2Service()
    private val mReleaseService = TVChannelReleasesService()

    private var mRecyclerView: TVChannelsRecyclerView? = null

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

        App.IO.launch {

            val cachedProgram = mChannelService.getChannels(
                offset = 0,
                limit = UPDATE_COUNT,
                fromCache = true
            )

            App.IO.launch {
                val program = mChannelService.getChannels(
                    offset = 0,
                    limit = UPDATE_COUNT
                ) ?: return@launch

                if (cachedProgram == null) {
                    Log.d(
                        TAG,
                        "onCreateView: CACHED_PROGRAM:" +
                            "${program.size}"
                    )
                    App.ui {
                        mRecyclerView?.adapterChannels = TVChannelAdapter(
                            App.WIDTH,
                            App.HEIGHT,
                            program,
                            mReleaseService
                        )
                    }
                }
            }

            if (cachedProgram != null) {
                Log.d(
                    TAG,
                    "onCreateView: CACHED_PROGRAM:" +
                        "${cachedProgram.size}"
                )
                App.ui {
                    mRecyclerView?.adapterChannels = TVChannelAdapter(
                        App.WIDTH,
                        App.HEIGHT,
                        cachedProgram,
                        mReleaseService
                    )
                }
            }

        }
    }
}