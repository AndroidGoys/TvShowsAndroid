package good.damn.tvlist.view_holders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.extensions.size
import good.damn.tvlist.views.ChannelDateView

class TVShowChannelsViewHolder(
    private val mChannelDate: ChannelDateView
): RecyclerView.ViewHolder(
    mChannelDate
) {
    companion object {
        fun create(
            context: Context,
            holderWidth: Int,
            holderHeight: Int
        ): TVShowChannelsViewHolder {
            val channel = ChannelDateView(
                context
            ).apply {
                size(
                    holderWidth,
                    holderHeight
                )

                cornerRadius = holderHeight * 0.20909f
            }

            return TVShowChannelsViewHolder(
                channel
            )
        }
    }
}