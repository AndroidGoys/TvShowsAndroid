package good.damn.tvlist.view_holders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.views.TVProgramView

class TVProgramViewHolder(
    private val mProgramView: TVProgramView
): RecyclerView.ViewHolder(
    mProgramView
) {
    fun setProgram(
        p: TVProgram
    ) {
        mProgramView.title = p.shortName ?: p.name
        mProgramView.invalidate()
    }

    companion object {
        fun create(
            context: Context,
            widthRecyclerView: Int,
            heightRecyclerView: Int
        ): TVProgramViewHolder {
            val programView = TVProgramView(
                context
            )

            programView.textTintColor = 0xffffffff.toInt()

            programView.sizeAgeFactor = 0.05365f
            programView.sizeTimeFactor = 0.05365f
            programView.sizeTitleFactor = 0.07317f

            programView.typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            programView.setBackgroundColor(
                0xffaaaaaa.toInt()
            )

            programView.size(
                (widthRecyclerView * 0.384057f).toInt(),
                heightRecyclerView
            )

            (programView.widthParams() * 0.05031f)
            .toInt().let { leftPadding ->
                programView.setPadding(
                    leftPadding,
                    0,
                    leftPadding,
                    (programView.heightParams() * 0.12578f).toInt()
                )
            }

            programView.cornerRadius = heightRecyclerView * 0.08277f

            return TVProgramViewHolder(
                programView
            )
        }
    }
}