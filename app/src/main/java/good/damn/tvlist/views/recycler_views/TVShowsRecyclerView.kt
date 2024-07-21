package good.damn.tvlist.views.recycler_views

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.adapters.recycler_view.TVChannelReleaseAdapter
import good.damn.tvlist.network.api.models.TVChannelRelease

class TVShowsRecyclerView(
    context: Context
): RecyclerView(
    context
) {

    val adapterPrograms = TVChannelReleaseAdapter()

    var heightHolder: Int = 1
        set(v) {
            field = v
            adapterPrograms.recyclerViewHeight = v
        }
    var releases: ArrayList<TVChannelRelease>? = null
        set(v) {
            field = v
            val prevSize = adapterPrograms.itemCount
            adapterPrograms.releases = v
            if (v == null) {
                if (prevSize == 0) {
                    return
                }
                adapterPrograms.notifyItemRangeRemoved(
                    0,
                    prevSize
                )
                return
            }

            val curSize = v.size

            if (prevSize > curSize) {
                val delta = prevSize - curSize
                adapterPrograms.notifyItemRangeRemoved(
                    curSize,
                    delta
                )
                return
            }

            adapterPrograms.notifyItemRangeChanged(
                0,
                curSize
            )

            if (curSize > prevSize) {
                val delta = curSize - prevSize
                adapterPrograms.notifyItemRangeInserted(
                    prevSize,
                    delta
                )
            }

        }

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        if (params == null) {
            return
        }
        adapterPrograms.recyclerViewWidth = params.width
        super.setLayoutParams(params)
    }

}