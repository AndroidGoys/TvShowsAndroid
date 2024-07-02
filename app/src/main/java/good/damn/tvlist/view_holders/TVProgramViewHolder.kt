package good.damn.tvlist.view_holders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.extensions.size
import good.damn.tvlist.views.TVProgramView

class TVProgramViewHolder(
    private val mProgramView: TVProgramView
): RecyclerView.ViewHolder(
    mProgramView
) {

    companion object {
        fun create(
            context: Context,
            widthRecyclerView: Int,
            heightRecyclerView: Int
        ): TVProgramViewHolder {
            val programView = TVProgramView(
                context
            )

            programView.setBackgroundColor(
                0xffaaaaaa.toInt()
            )

            programView.size(
                (widthRecyclerView * 0.384057f).toInt(),
                heightRecyclerView
            )

            programView.cornerRadius = heightRecyclerView * 0.08277f

            return TVProgramViewHolder(
                programView
            )
        }
    }
}