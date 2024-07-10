package good.damn.tvlist.view_holders

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.network.api.models.TVChannel
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.TVChannelView
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.recycler_views.TVProgramsRecyclerView

class TVChannelViewHolder(
    private val mTvChannelView: TVChannelView,
    private val mRecyclerViewPrograms: TVProgramsRecyclerView,
    private val mLayout: LinearLayout
): RecyclerView.ViewHolder(
    mLayout
) {

    fun onBindViewHolder(
        t: TVChannel?
    ) {
        mLayout.alpha = 0f
        if (t == null) {
            return
        }
        Log.d(TAG, "setChannel: IMAGE_URL ${t.imageUrl}")

        mLayout.animate()
            .alpha(1.0f)
            .start()

        mTvChannelView.text = t.shortName ?: t.name
        mTvChannelView.invalidate()
        mRecyclerViewPrograms.programs = t.programs

        t.imageUrl?.let { url ->
            if (App.iconMap.containsKey(url)) {
                Log.d(TAG, "setChannel: CONTAINS IN MAP $url")
                mTvChannelView.imagePreview = App
                    .iconMap[url]
                mTvChannelView.invalidate()
                return@let
            }

            val bounds = mTvChannelView.heightParams()
            
            NetworkBitmap.loadFromNetwork(
                url = url,
                App.CACHE_DIR,
                dirName = "bitmapChannels",
                bounds,
                bounds
            ) {
                App.iconMap[url] = it
                mTvChannelView.imagePreview = it
                mTvChannelView.invalidate()
            }
        }
    }

    companion object {
        private const val TAG = "TVChannelViewHolder"
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
            val recyclerView = TVProgramsRecyclerView(
                context
            )

            // Text Colors
            channelView.textColor = App.color(
                R.color.accentColor
            )

            //Typeface
            channelView.typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            // Drawable
            channelView.imageIcon = App.drawable(
                R.drawable.ic_info
            )

            layout.setBackgroundColor(0)
            channelView.setBackgroundColor(0)
            recyclerView.setBackgroundColor(0)

            // Bounds
            (height * 0.31256f).toInt().let { heightView ->
                layout.size(
                    width,
                    heightView
                )

                channelView.setPadding(
                    (width * 0.04347f).toInt(),
                    0,
                    0,
                    0
                )

                channelView.boundsLinear(
                    width = width,
                    height = (heightView * 0.12587f)
                        .toInt(),
                    top = heightView * 0.087412f
                )

                channelView.cornerRadiusPreview = channelView
                    .heightParams() * 0.25f

                recyclerView.boundsLinear(
                    width = width,
                    height = (heightView * 0.71811f)
                        .toInt(),
                    top = heightView * 0.06993f
                )

                recyclerView.heightHolder = recyclerView.heightParams()

                // Text size
                channelView.textSize = channelView
                    .heightParams() * 0.5833f
            }

            // RecyclerView setup
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            layout.apply {
                addView(channelView)
                addView(recyclerView)
            }

            recyclerView.clipToPadding = false

            recyclerView.setPadding(
                0,
                0,
                channelView.paddingLeft,
                0
            )

            recyclerView.adapter = recyclerView
                .adapterPrograms

            recyclerView.addItemDecoration(
                MarginItemDecoration(
                    left = channelView.paddingLeft
                )
            )

            return TVChannelViewHolder(
                channelView,
                recyclerView,
                layout
            )
        }
    }
}