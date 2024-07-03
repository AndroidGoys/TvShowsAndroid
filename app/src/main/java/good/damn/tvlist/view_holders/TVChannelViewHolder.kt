package good.damn.tvlist.view_holders

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.TVChannelView
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.recycler_views.TVProgramsRecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class TVChannelViewHolder(
    private val mTvChannelView: TVChannelView,
    private val mRecyclerViewPrograms: TVProgramsRecyclerView,
    layout: LinearLayout
): RecyclerView.ViewHolder(
    layout
) {
    fun setChannel(
        t: TVChannel
    ) {
        Log.d(TAG, "setChannel: IMAGE_URL ${t.imageUrl}")

        mTvChannelView.text = t.name

        t.imageUrl?.let {
            mTvChannelView.imagePreview = App.iconMap[
                it
            ]
        }
        mTvChannelView.invalidate()
        mRecyclerViewPrograms.programs = t.programs

        if (mTvChannelView.imagePreview != null) {
            return
        }

        if (t.imageUrl == null) {
            return
        }

        CoroutineScope(
            Dispatchers.IO
        ).launch {
            val inp = try {
                URL(
                    t.imageUrl
                ).openStream()
            } catch (e: Exception) {
                Log.d(TAG, "setChannel: ERROR_IMAGE_URL: ${e.message}")
                return@launch
            }

            val b = BitmapFactory.decodeStream(
                inp
            )

            val resolution = mTvChannelView
                .heightParams()

            val scaled = Bitmap.createScaledBitmap(
                b,
                resolution,
                resolution,
                false
            )

            App.iconMap[t.imageUrl] = scaled

            mTvChannelView.imagePreview = scaled
            App.ui {
                mTvChannelView.requestLayout()
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