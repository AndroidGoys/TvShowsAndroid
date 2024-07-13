package good.damn.tvlist.view_holders.tv_show

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.network.api.models.show.TVShowChannelDate
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.views.ChannelDateView

class TVShowChannelsViewHolder(
    private val mChannelDate: ChannelDateView
): RecyclerView.ViewHolder(
    mChannelDate
) {

    fun onBindViewHolder(
        channel: TVShowChannelDate
    ) {

        mChannelDate.dateString = channel.date
        mChannelDate.timeString = channel.time
        mChannelDate.invalidate()

        if (channel.imageUrl == null) {
            return
        }

        NetworkBitmap.loadFromNetwork(
            channel.imageUrl,
            App.CACHE_DIR,
            dirName = "bitmapShowChannels",
            mChannelDate.widthParams(),
            mChannelDate.heightParams()
        ) {
            mChannelDate.channelPreview = it
            mChannelDate.invalidate()
        }

    }

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