package good.damn.tvlist.adapters.recycler_view

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.enums.SearchResultType
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.models.TVSearchResultTitle
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.view_holders.TVSearchResultViewHolder
import good.damn.tvlist.view_holders.TVSearchTitleViewHolder

class TVSearchResultAdapter(
    private val mHolderWidth: Int,
    private val mHolderHeight: Int
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "TVSearchResultAdapter"
    }

    private val mData = ArrayList<Typeable?>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when(viewType) {
        SearchResultType.RESULT.type -> TVSearchResultViewHolder.create(
            parent.context,
            mHolderWidth,
            mHolderHeight
        )
        else -> TVSearchTitleViewHolder.create(
            parent.context,
            mHolderWidth,
            mHolderHeight
        )
    }

    override fun getItemViewType(
        position: Int
    ) = mData[position]?.onGetType()?.type
        ?: SearchResultType.TITLE.type

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val data = mData[position]
            ?: return

        when(data.onGetType()) {
            SearchResultType.RESULT -> {
                if (data !is TVSearchResult) {
                    return
                }
                (holder as? TVSearchResultViewHolder)
                    ?.onBindViewHolder(data)
            }
            else -> {
                if (data !is TVSearchResultTitle)
                    return

                (holder as? TVSearchTitleViewHolder)
                    ?.setTitle(
                        data.title
                    )
            }
        }
    }

    fun addResult(
        t: ArrayList<Typeable?>
    ) {
        mData.addAll(t)
    }

    fun cleanCurrentResult() {
        val len = mData.size
        if (len == 0) {
            return
        }

        mData.clear()

        notifyItemRangeRemoved(
            0,
            len
        )
    }

}