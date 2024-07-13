package good.damn.tvlist.adapters.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.show.TVShowImage
import good.damn.tvlist.view_holders.tv_show.TVShowImageViewHolder

class TVShowImagesAdapter(
    private val mData: Array<TVShowImage>,
    private val mHolderWidth: Int,
    private val mHolderHeight: Int
): RecyclerView.Adapter<TVShowImageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVShowImageViewHolder.create(
        parent.context,
        mHolderWidth,
        mHolderHeight
    )

    override fun getItemCount() = mData.size


    override fun onBindViewHolder(
        holder: TVShowImageViewHolder,
        position: Int
    ) = holder.onBindViewHolder(
        mData[position]
    )
}