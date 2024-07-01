package good.damn.tvlist.adapters.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.view_holders.TVChannelViewHolder

class TVChannelAdapter(
    private val width: Int,
    private val height: Int
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
        return 4
    }

    override fun onBindViewHolder(
        holder: TVChannelViewHolder,
        position: Int
    ) {
        holder.setChannelName(
            holder.hashCode().toString()
        )
    }
}