package good.damn.tvlist.view_holders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.views.round.RoundedImageView

class TVShowImageViewHolder(
    private val mImage: RoundedImageView
): RecyclerView.ViewHolder(
    mImage
) {

    companion object {
        fun create(
            context: Context,
            recyclerViewWidth: Int,
            recyclerViewHeight: Int
        ): TVShowImageViewHolder {

            val image = RoundedImageView(
                context
            ).apply {

                size(
                    width = (recyclerViewWidth * 0.782608f).toInt(),
                    height = recyclerViewHeight
                )

                cornerRadius = heightParams() * 0.083333f

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