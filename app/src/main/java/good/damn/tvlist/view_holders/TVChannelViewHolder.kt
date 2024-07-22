package good.damn.tvlist.view_holders

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.main.tv_details.channel.TVChannelPageFragment
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.services.TVChannelReleasesService
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.TVChannelView
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.recycler_views.TVShowsRecyclerView
import kotlinx.coroutines.launch
import java.util.Calendar

class TVChannelViewHolder(
    private val mTvChannelView: TVChannelView,
    private val mRecyclerViewReleases: TVShowsRecyclerView,
    private val mLayout: LinearLayout
): RecyclerView.ViewHolder(
    mLayout
) {
    fun onBindViewHolder(
        t: TVChannel2?,
        releaseService: TVChannelReleasesService
    ) {
        mLayout.alpha = 0f
        if (t == null) {
            return
        }
        Log.d(TAG, "setChannel: IMAGE_URL ${t.imageUrl}")

        mTvChannelView.channel = t

        mTvChannelView.text = t.shortName ?: t.name
        mTvChannelView.invalidate()

        App.IO.launch {

            val hashedReleases = App.RELEASES[t.id]

            if (hashedReleases != null) {
                App.ui {
                    mRecyclerViewReleases.releases = hashedReleases
                }
                return@launch
            }

            val cachedReleases = releaseService.getReleases(
                t.id,
                App.LAST_SAVED_TIME_SECONDS,
                limit = 8,
                fromCache = true
            )

            Log.d(TAG, "onBindViewHolder: CACHED_RELEASES: ${cachedReleases?.size}")
            
            if (cachedReleases != null) {
                App.RELEASES[t.id] = cachedReleases
                App.ui {
                    mRecyclerViewReleases.releases = cachedReleases
                }
                return@launch
            }

            val releases = releaseService.getReleases(
                t.id,
                App.CURRENT_TIME_SECONDS,
                limit = 8,
                fromCache = false
            ) ?: return@launch

            App.RELEASES[t.id] = releases
            App.ui {
                mRecyclerViewReleases.releases = releases
            }

        }

        mLayout.animate()
            .alpha(1.0f)
            .start()

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
            val recyclerView = TVShowsRecyclerView(
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
                layout
            )
        }
    }
}