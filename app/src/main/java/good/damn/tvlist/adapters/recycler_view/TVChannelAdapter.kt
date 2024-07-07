package good.damn.tvlist.adapters.recycler_view

import android.view.ViewGroup
import androidx.annotation.WorkerThread
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.TVChannel
import good.damn.tvlist.view_holders.TVChannelViewHolder

class TVChannelAdapter(
    private val width: Int,
    private val height: Int,
    private val data: ArrayList<TVChannel?>
): RecyclerView.Adapter<TVChannelViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVChannelViewHolder.create(
        parent.context,
        width,
        height
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(
        holder: TVChannelViewHolder,
        position: Int
    ) = holder.onBindViewHolder(
       data[position]
    )

    @WorkerThread
    fun addChannels(
        t: ArrayList<TVChannel?>
    ) {
        data.addAll(t)
    }
}