package good.damn.tvlist.views.recycler_views

import android.content.Context
import good.damn.tvlist.App
import good.damn.tvlist.adapters.recycler_view.TVChannelAdapter
import good.damn.tvlist.network.api.services.TVChannel2Service
import good.damn.tvlist.views.recycler_views.scroll_listeners.StreamScrollListener
import kotlinx.coroutines.launch

class TVChannelsRecyclerView(
    context: Context,
    streamScrollListener: StreamScrollListener
): StreamableRecyclerView(
    context,
    streamScrollListener
) {

    private val mChannelService = TVChannel2Service()

    var adapterChannels: TVChannelAdapter? = null
        set(v) {
            field = v
            adapter = v
        }

    override fun onUpdateDataStream(
        currentIndex: Int
    ) {
        App.IO.launch {
            val currentIndex = currentIndex
            val from = currentIndex + 1
            val updateCount = mStreamScrollListener.updateCount

            val cachedChannels = mChannelService.getChannels(
                from,
                updateCount,
                fromCache = true
            )

            if (cachedChannels != null) {
                adapterChannels?.addChannels(
                    cachedChannels
                )

                App.ui {
                    adapterChannels?.notifyItemRangeInserted(
                        currentIndex,
                        updateCount
                    )
                }
            }

            val networkChannels = mChannelService.getChannels(
                from,
                updateCount,
                fromCache = false
            ) ?: return@launch

            if (cachedChannels == null) {
                adapterChannels?.addChannels(
                    networkChannels
                )
                App.ui {
                    adapterChannels?.notifyItemRangeInserted(
                        currentIndex,
                        updateCount
                    )
                }
                return@launch
            }

            adapterChannels?.updateData(
                currentIndex,
                networkChannels
            )

            App.ui {
                adapterChannels?.notifyItemRangeChanged(
                    currentIndex,
                    updateCount
                )
            }

        }
    }

}