package good.damn.tvlist.models

import good.damn.tvlist.enums.SearchResultType
import good.damn.tvlist.interfaces.Typeable

data class TVSearchResultTitle(
    val title: String
) : Typeable {
    override fun onGetType() = SearchResultType.TITLE
}