package good.damn.tvlist.adapters.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.view_holders.TVShowImageViewHolder

class TVShowImagesAdapter(
    private val mRecyclerViewWidth: Int,
    private val mRecyclerViewHeight: Int
): RecyclerView.Adapter<TVShowImageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVShowImageViewHolder.create(
        parent.context,
        mRecyclerViewWidth,
        mRecyclerViewHeight
    )

    override fun getItemCount() = 5

    override fun onBindViewHolder(
        holder: TVShowImageViewHolder,
        position: Int
    ) {

    }
}