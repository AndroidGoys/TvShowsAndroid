package good.damn.tvlist.network.api.models

import good.damn.tvlist.enums.SearchResultType
import good.damn.tvlist.interfaces.Typeable
import java.net.URL

data class TVSearchResult(
    val name: String,
    val imageURL: String?
): Typeable {
    override fun onGetType() = SearchResultType.RESULT
}