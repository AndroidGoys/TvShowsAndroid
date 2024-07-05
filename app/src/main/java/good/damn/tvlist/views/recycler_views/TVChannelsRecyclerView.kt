package good.damn.tvlist.views.recycler_views

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.adapters.recycler_view.TVChannelAdapter
import good.damn.tvlist.network.api.models.TVChannel
import good.damn.tvlist.network.api.services.TVChannelsService
import good.damn.tvlist.views.recycler_views.scroll_listeners.StreamScrollListener
import good.damn.tvlist.views.recycler_views.scroll_listeners.interfaces.OnUpdateStreamDataListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TVChannelsRecyclerView(
    context: Context,
    streamScrollListener: StreamScrollListener
): StreamableRecyclerView(
    context,
    streamScrollListener
) {

    private val mChannelService = TVChannelsService(
        context.cacheDir
    )

    var adapterChannels: TVChannelAdapter? = null
        set(v) {
            field = v
            adapter = v
        }

    override fun onUpdateDataStream(
        currentIndex: Int
    ) {
        mChannelService.getChannels(
            from = (currentIndex / mStreamScrollListener.updateCount)+1,
            mStreamScrollListener.updateCount
        ) {
            adapterChannels?.addChannels(
                it
            )

            App.ui {
                adapterChannels?.notifyItemRangeInserted(
                    currentIndex,
                    mStreamScrollListener.updateCount
                )
            }

        }
    }

}