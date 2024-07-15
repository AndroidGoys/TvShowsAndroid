package good.damn.tvlist.interfaces

import good.damn.tvlist.enums.SearchResultType

interface Typeable {

    fun onGetType(): SearchResultType
}