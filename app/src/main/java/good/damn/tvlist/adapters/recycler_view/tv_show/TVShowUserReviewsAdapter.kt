package good.damn.tvlist.adapters.recycler_view.tv_show

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.user.TVUserReview
import good.damn.tvlist.network.api.services.UserService
import good.damn.tvlist.view_holders.tv_show.TVShowUserReviewViewHolder

class TVShowUserReviewsAdapter(
    private val mHolderWidth: Int,
    private var mData: ArrayList<TVUserReview>,
    private val mUserService: UserService
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
        mData[position],
        mUserService
    )

    fun addData(
        d: ArrayList<TVUserReview>
    ) {
        mData.addAll(
            d
        )
    }

}