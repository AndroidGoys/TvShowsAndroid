package good.damn.tvlist.adapters.recycler_view

import android.view.ViewGroup
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

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(
        holder: TVChannelViewHolder,
        position: Int
    ) {
        val channel = data[position]
            ?: return

        holder.setChannel(
            channel
        )
    }
}