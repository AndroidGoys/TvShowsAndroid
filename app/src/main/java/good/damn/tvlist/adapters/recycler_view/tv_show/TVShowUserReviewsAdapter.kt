package good.damn.tvlist.adapters.recycler_view.tv_show

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.show.TVShowUserReview
import good.damn.tvlist.view_holders.tv_show.TVShowUserReviewViewHolder

class TVShowUserReviewsAdapter(
    private val mHolderWidth: Int,
    private val mData: Array<TVShowUserReview>
): RecyclerView.Adapter<
    TVShowUserReviewViewHolder
>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVShowUserReviewViewHolder.create(
        parent.context,
        mHolderWidth
    )

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(
        holder: TVShowUserReviewViewHolder,
        position: Int
    ) = holder.onBindViewHolder(
        mData[position]
    )


}