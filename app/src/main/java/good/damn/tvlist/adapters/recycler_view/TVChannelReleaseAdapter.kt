package good.damn.tvlist.adapters.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.TVChannelRelease
import good.damn.tvlist.view_holders.TVChannelReleaseViewHolder

class TVChannelReleaseAdapter
: RecyclerView.Adapter<TVChannelReleaseViewHolder>() {

    var releases: ArrayList<TVChannelRelease>? = null

    var recyclerViewHeight: Int = 0
    var recyclerViewWidth: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVChannelReleaseViewHolder.create(
        parent.context,
        recyclerViewWidth,
        recyclerViewHeight
    )

    override fun getItemCount(): Int {
        return releases?.size ?: 0
    }

    override fun onBindViewHolder(
        holder: TVChannelReleaseViewHolder,
        position: Int
    ) {
        if (releases == null) {
            return
        }
        holder.onBindViewHolder(
            releases!![position],
            if (position + 1 >= itemCount)
                null else releases!![position+1]
        )
    }
}