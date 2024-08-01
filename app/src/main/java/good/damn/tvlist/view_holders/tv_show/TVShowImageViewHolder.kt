package good.damn.tvlist.view_holders.tv_show

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.network.api.models.show.TVShowImage
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.views.round.RoundedImageView

class TVShowImageViewHolder(
    private val mImage: RoundedImageView
): RecyclerView.ViewHolder(
    mImage
) {

    fun onBindViewHolder(
        t: TVShowImage
    ) {
        mImage.bitmap = null
        mImage.invalidate()
        if (t.imageUrl == null) {
            return
        }

        NetworkBitmap.loadFromNetwork(
            t.imageUrl,
            App.CACHE_DIR,
            "showImages",
            mImage.widthParams(),
            mImage.heightParams()
        ) {
            mImage.bitmap = it
            mImage.startImageAnimation()
        }
    }

    companion object {
        private const val TAG = "TVShowImageViewHolder"
        fun create(
            context: Context,
            width: Int,
            height: Int
        ): TVShowImageViewHolder {

            val image = RoundedImageView(
                context
            ).apply {

                size(
                    width,
                    height
                )

                cornerRadius = height * 0.083333f

                setBackgroundColor(
                    0xaac4c4c4.toInt()
                )
            }

            return TVShowImageViewHolder(
                image
            )
        }
    }

}