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
            adapterPrograms.releases = v
            adapterPrograms.notifyDataSetChanged()
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