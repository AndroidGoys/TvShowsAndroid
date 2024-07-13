package good.damn.tvlist.adapters.recycler_view.tv_show

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.show.TVShowChannelDate
import good.damn.tvlist.view_holders.tv_show.TVShowChannelsViewHolder

class TVShowChannelsAdapter(
    private val mData: Array<TVShowChannelDate>,
    private val mHolderWidth: Int,
    private val mHolderHeight: Int
): RecyclerView.Adapter<TVShowChannelsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVShowChannelsViewHolder.create(
        parent.context,
        mHolderWidth,
        mHolderHeight
    )

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(
        holder: TVShowChannelsViewHolder,
        position: Int
    ) = holder.onBindViewHolder(
        mData[position]
    )
}