package good.damn.tvlist.views.recycler_views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.views.recycler_views.scroll_listeners.StreamScrollListener
import good.damn.tvlist.views.recycler_views.scroll_listeners.interfaces.OnUpdateStreamDataListener

abstract class StreamableRecyclerView(
    context: Context,
    protected val mStreamScrollListener: StreamScrollListener
): RecyclerView(
    context
), OnUpdateStreamDataListener {
    var isEnabledStreaming: Boolean = true
        set(v) {
            field = v
            if (!v) {
                removeOnScrollListener(
                    mStreamScrollListener
                )
                return
            }

            mStreamScrollListener
                .onUpdateDataStreamDataListener = this

            addOnScrollListener(
                mStreamScrollListener
            )
        }
}