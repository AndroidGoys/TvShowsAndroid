package good.damn.tvlist.models

import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.enums.SearchResultType
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.view_holders.TVSearchTitleViewHolder

data class TVSearchResultTitle(
    val title: String
) : Typeable {
    override fun onGetType() = SearchResultType.TITLE

    override fun onBindViewHolder(
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        (holder as? TVSearchTitleViewHolder)
            ?.setTitle(title)
    }
}