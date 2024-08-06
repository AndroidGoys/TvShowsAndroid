package good.damn.tvlist.fragments.ui.main.pages

import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVChannelAdapter
import good.damn.tvlist.extensions.getTimeInSeconds
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.interfaces.OnCalendarSetListener
import good.damn.tvlist.network.api.services.TVChannel2Service
import good.damn.tvlist.network.api.services.TVChannelReleasesService
import good.damn.tvlist.views.recycler_views.TVChannelsRecyclerView
import good.damn.tvlist.views.recycler_views.scroll_listeners.StreamScrollListener
import kotlinx.coroutines.launch
import java.util.Calendar

class TVChannelReleaseFragment
: StackFragment(),
OnCalendarSetListener {

    companion object {
        private const val TAG = "TVProgramFragment"
        private const val UPDATE_COUNT = 5
    }

    private val mChannelService = TVChannel2Service()
    private val mReleaseService = TVChannelReleasesService()

    private var mRecyclerView: TVChannelsRecyclerView? = null

    override fun onAnimationEnd() {
        super.onAnimationEnd()
        initChannels()
    }

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
            val cachedChannels = mChannelService.getChannels(
                offset = 0,
                limit = UPDATE_COUNT,
                fromCache = true
            )

            App.IO.launch {
                val channels = mChannelService.getChannels(
                    offset = 0,
                    limit = UPDATE_COUNT
                ) ?: return@launch

                if (cachedChannels == null) {
                    Log.d(
                        TAG,
                        "onCreateView: CACHED_CHANNELS:" +
                            "${channels.size}"
                    )
                    App.ui {
                        mRecyclerView?.adapterChannels = TVChannelAdapter(
                            App.WIDTH,
                            App.HEIGHT,
                            channels,
                            mReleaseService
                        )
                    }
                }
            }

            if (cachedChannels != null) {
                Log.d(
                    TAG,
                    "onCreateView: CACHED_CHANNELS:" +
                        "${cachedChannels.size}"
                )
                App.ui {
                    mRecyclerView?.adapterChannels = TVChannelAdapter(
                        App.WIDTH,
                        App.HEIGHT,
                        cachedChannels,
                        mReleaseService
                    )
                }
            }

        }
    }

    override fun onCalendarSet(
        calendar: Calendar
    ) {
        mRecyclerView?.adapterChannels?.apply {
            timeSeconds = calendar.getTimeInSeconds()
            notifyItemRangeChanged(
                0,
                itemCount
            )
        }
    }
}