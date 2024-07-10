package good.damn.tvlist.view_holders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
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
                textSizeTimeFactor = 0.15454f
                textSizeDateFactor = 0.091f

                cornerRadius = holderHeight * 0.20909f

                textColor = 0xffffffff.toInt()
                typeface = App.font(
                    R.font.open_sans_semi_bold,
                    context
                )

                size(
                    holderWidth,
                    holderHeight
                )

                timeString = "12:30"
                dateString = "26.07"
            }

            return TVShowChannelsViewHolder(
                channel
            )
        }
    }
}