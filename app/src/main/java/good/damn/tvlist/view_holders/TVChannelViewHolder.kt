package good.damn.tvlist.view_holders

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.middle
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.size
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.main.tv_details.channel.TVChannelPageFragment
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.services.TVChannelReleasesService
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.TVChannelView
import good.damn.tvlist.views.TVDateRangeView
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.recycler_views.TVShowsRecyclerView
import kotlinx.coroutines.launch

class TVChannelViewHolder(
    private val mTvChannelView: TVChannelView,
    private val mRecyclerViewReleases: TVShowsRecyclerView,
    private val mDateRangeView: TVDateRangeView,
    private val mLayout: LinearLayout
): RecyclerView.ViewHolder(
    mLayout
) {
    fun onBindViewHolder(
        t: TVChannel2?,
        onTimeSeconds: Int,
        releaseService: TVChannelReleasesService
    ) {
        if (t == null) {
            return
        }
        Log.d(TAG, "setChannel: IMAGE_URL ${t.imageUrl}")

        mTvChannelView.apply {
            channel = t
            text = t.shortName ?: t.name
            imagePreview = null
            invalidate()
        }

        App.createIO().launch {
            releaseService.getReleases(
                t.id,
                0,
                limit = 8,
                fromCache = true
            )?.apply {
                App.ui {
                    mRecyclerViewReleases.releases = this
                    mDateRangeView.apply {
                        timeSecondsLeft = firstOrNull()?.timeStart ?: 0
                        timeSecondsRight = lastOrNull()?.timeStart ?: 0

                        invalidate()
                    }
                }

                middle()?.let {
                    if (it.timeStart > App.CURRENT_TIME_SECONDS) {
                        return@launch
                    }
                }
            }

            releaseService.getReleases(
                t.id,
                onTimeSeconds,
                limit = 8,
                fromCache = false
            )?.apply {
                App.ui {
                    mRecyclerViewReleases.releases = this
                    mDateRangeView.apply {
                        timeSecondsLeft = firstOrNull()?.timeStart ?: 0
                        timeSecondsRight = lastOrNull()?.timeStart ?: 0

                        invalidate()
                    }
                }
            }
        }

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
                if (it == null) {
                    return@loadFromNetwork
                }
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
            ).apply {
                setBackgroundColor(0)
            }


            val channelView = TVChannelView(
                context
            ).apply {
                textColor = App.color(
                    R.color.accentColor
                )
                typeface = App.font(
                    R.font.open_sans_semi_bold,
                    context
                )
                imageIcon = App.drawable(
                    R.drawable.ic_info
                )
                setBackgroundColor(0)
            }


            val recyclerView = TVShowsRecyclerView(
                context
            ).apply {
                setBackgroundColor(0)
            }


            val dateRangeView = TVDateRangeView(
                context
            )


            // Bounds
            (height * 0.31256f).toInt().let { heightView ->

                val dateViewHeight = (
                    heightView * 0.05f
                ).toInt()

                val dateViewTop = (
                    heightView * 0.05f
                ).toInt()

                layout.size(
                    width,
                    heightView + dateViewHeight + dateViewTop
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
                    height = (heightView * 0.71811f).toInt(),
                    top = heightView * 0.06993f
                )

                dateRangeView.boundsLinear(
                    gravity = Gravity.CENTER_HORIZONTAL,
                    width = width - channelView.paddingLeft * 2,
                    height = dateViewHeight,
                    top = dateViewTop.toFloat()
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
                addView(dateRangeView)
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

            channelView.onClickChannel = {
                (context as? MainActivity)?.apply {
                    pushFragment(
                        TVChannelPageFragment.newInstance(
                            it
                        ),
                        FragmentAnimation {
                            f, fragment ->
                            fragment.view?.x = App.WIDTH * (1.0f-f)
                        }
                    )
                }
            }

            return TVChannelViewHolder(
                channelView,
                recyclerView,
                dateRangeView,
                layout
            )
        }
    }
}