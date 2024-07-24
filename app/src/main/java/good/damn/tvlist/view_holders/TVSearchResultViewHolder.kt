package good.damn.tvlist.view_holders

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.Unicode
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.enums.SearchResultCategory
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.main.tv_details.TVShowPageFragment
import good.damn.tvlist.fragments.ui.main.tv_details.channel.TVChannelPageFragment
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.models.TVChannelRelease
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.network.api.models.TVShow
import good.damn.tvlist.network.api.models.enums.CensorAge
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.views.TVIconNameView

class TVSearchResultViewHolder(
    private val mIconNameView: TVIconNameView
): RecyclerView.ViewHolder(
    mIconNameView
) {

    fun onBindViewHolder(
        result: TVSearchResult?
    ) {
        Log.d(TAG, "onBindViewHolder: ${result?.name}")

        if (result == null) {
            return
        }

        mIconNameView.apply {
            model = result
            text = result.name
            bitmap = null
            invalidate()
        }

        if (result.imageURL == null) {
            return
        }

        val bitmapSize = mIconNameView.heightParams()
        NetworkBitmap.loadFromNetwork(
            result.imageURL,
            App.CACHE_DIR,
            "bitmapSearch",
            bitmapSize,
            bitmapSize
        ) {
            mIconNameView.apply {
                bitmap = it
                invalidate()
            }
        }
    }

    companion object {
        private const val TAG = "TVSearchResultViewHolde"
        fun create(
            context: Context,
            holderWidth: Int,
            holderHeight: Int
        ): TVSearchResultViewHolder {
            val iconNameView = TVIconNameView(
                context
            )

            iconNameView.onClickDataListener = { result ->
                if (result.type == SearchResultCategory.SHOW) {
                    (result.model as? TVShow)?.let {
                        (context as? MainActivity)?.pushFragment(
                            TVShowPageFragment.newInstance(
                                TVChannelRelease(
                                    it.id.toLong(),
                                    it.name,
                                    rating = it.rating,
                                    shortName = it.shortName,
                                    previewUrl = it.previewUrl,
                                    startTimeString = "00:00",
                                    timeStart = 0,
                                    censorAge = CensorAge.ADULT
                                )
                            ),
                            FragmentAnimation {
                                f, fragment ->
                                fragment.view?.x = App.WIDTH * (1.0f-f)
                            }
                        )
                    }
                } else {
                    (result.model as? TVChannel2)?.let {
                        (context as? MainActivity)?.pushFragment(
                            TVChannelPageFragment.newInstance(
                                it
                            ),
                            FragmentAnimation { f, fragment ->
                                fragment.view?.x = App.WIDTH * (1.0f - f)
                            }
                        )
                    }
                }
            }

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