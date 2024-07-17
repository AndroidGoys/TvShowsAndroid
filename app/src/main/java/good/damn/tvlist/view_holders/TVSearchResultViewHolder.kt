package good.damn.tvlist.view_holders

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.Unicode
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.main.pages.TVProgramFragment
import good.damn.tvlist.fragments.ui.main.tv_show_details.TVShowPageFragment
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.network.api.models.TVShow
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
                (result.model as? TVShow)?.let {
                    (context as? MainActivity)?.pushFragment(
                        TVShowPageFragment.newInstance(
                            TVProgram(
                                it.id.toLong(),
                                it.name,
                                rating = it.rating,
                                shortName = if (it.name.length > 20)
                                    it.name.substring(0,20) + Unicode.DOTS
                                else null,
                                imageUrl = it.previewUrl
                            )
                        ),
                        FragmentAnimation {
                            f, fragment ->
                            fragment.view?.x = App.WIDTH * (1.0f-f)
                        }
                    )
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