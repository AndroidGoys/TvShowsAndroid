package good.damn.tvlist.adapters.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.view_holders.TVShowChannelsViewHolder

class TVShowChannelsAdapter(
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

    override fun getItemCount() = 10

    override fun onBindViewHolder(
        holder: TVShowChannelsViewHolder,
        position: Int
    ) {

    }
}