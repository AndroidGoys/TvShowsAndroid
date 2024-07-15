package good.damn.tvlist.view_holders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.extensions.size
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.views.TVIconNameView

class TVSearchResultViewHolder(
    private val mIconNameView: TVIconNameView
): RecyclerView.ViewHolder(
    mIconNameView
) {

    fun onBindViewHolder(
        result: TVSearchResult?
    ) {
        if (result == null) {
            return
        }


    }

    companion object {
        fun create(
            context: Context,
            holderWidth: Int,
            holderHeight: Int
        ): TVSearchResultViewHolder {
            val iconNameView = TVIconNameView(
                context
            )

            iconNameView.size(
                holderWidth,
                holderHeight
            )

            return TVSearchResultViewHolder(
                iconNameView
            )
        }
    }
}