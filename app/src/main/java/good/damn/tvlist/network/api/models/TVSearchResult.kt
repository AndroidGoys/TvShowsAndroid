package good.damn.tvlist.network.api.models

import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.enums.SearchResultCategory
import good.damn.tvlist.enums.SearchResultType
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.view_holders.TVSearchResultViewHolder
import java.net.URL

data class TVSearchResult(
    val name: String,
    val imageURL: String?,
    val type: SearchResultCategory,
    val model: Any
): Typeable {
    override fun onGetType() = SearchResultType.RESULT

    override fun onBindViewHolder(
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        (holder as? TVSearchResultViewHolder)
            ?.onBindViewHolder(this)
    }
}