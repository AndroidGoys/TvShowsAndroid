package good.damn.tvlist.interfaces

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.enums.SearchResultType

interface Typeable {

    fun onGetType(): SearchResultType

    fun onBindViewHolder(
        position: Int,
        holder: RecyclerView.ViewHolder
    )

}