package good.damn.tvlist.view_holders

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.TVChannelView

class TVChannelViewHolder(
    private val mTvChannelView: TVChannelView,
    private val mRecyclerViewPrograms: RecyclerView,
    layout: LinearLayout
): RecyclerView.ViewHolder(
    layout
) {

    fun setChannelName(
        t: String
    ) {
        mTvChannelView.text = t
        mTvChannelView.invalidate()
    }

    companion object {
        fun create(
            context: Context,
            width: Int,
            height: Int
        ): TVChannelViewHolder {
            val layout = ViewUtils.verticalLinear(
                context
            )
            val channelView = TVChannelView(
                context
            )
            val recyclerView = RecyclerView(
                context
            )



            // Text Colors
            channelView.textColor = App.color(
                R.color.accentColor
            )


            layout.setBackgroundColor(
                0xffff0000.toInt()
            )

            channelView.setBackgroundColor(
                0xff00ff00.toInt()
            )

            recyclerView.setBackgroundColor(
                0xff0000ff.toInt()
            )



            // Bounds

            (height * 0.31256f).toInt().let { heightView ->
                layout.size(
                    width,
                    heightView
                )

                channelView.boundsLinear(
                    width = width,
                    height = (heightView * 0.12587f)
                        .toInt()
                )

                recyclerView.boundsLinear(
                    width = width,
                    height = (heightView * 0.71811f)
                        .toInt()
                )

                // Text size
                channelView.textSize = channelView
                    .heightParams() * 0.5833f
            }

            layout.apply {
                addView(channelView)
                addView(recyclerView)
            }

            return TVChannelViewHolder(
                channelView,
                recyclerView,
                layout
            )
        }
    }
}