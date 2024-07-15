package good.damn.tvlist.adapters.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.view_holders.TVSearchResultViewHolder

class TVSearchResultAdapter(
    private val mHolderWidth: Int,
    private val mHolderHeight: Int
): RecyclerView.Adapter<TVSearchResultViewHolder>() {

    private val mData = ArrayList<TVSearchResult?>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVSearchResultViewHolder.create(
        parent.context,
        mHolderWidth,
        mHolderHeight
    )

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(
        holder: TVSearchResultViewHolder,
        position: Int
    ) = holder.onBindViewHolder(
        mData[position]
    )

    fun addResult(
        t: ArrayList<TVSearchResult?>
    ) {
        mData.addAll(t)
    }

}