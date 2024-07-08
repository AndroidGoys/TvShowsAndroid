package good.damn.tvlist.view_holders

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.main.TVShowDetailsFragment
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.views.program.OnClickProgramListener
import good.damn.tvlist.views.program.TVProgramView
import java.util.Calendar

class TVProgramViewHolder(
    private val mProgramView: TVProgramView
): RecyclerView.ViewHolder(
    mProgramView
) {
    private val mCalendar = Calendar.getInstance()

    fun onBindViewHolder(
        p: TVProgram,
        next: TVProgram?
    ) {

        mProgramView.cacheProgram = p
        mProgramView.title = p.shortName ?: p.name
        mProgramView.timeString = p.startTimeString
        mProgramView.rating = p.rating
        if (next != null) {
            val t = mCalendar.timeInMillis / 1000
            val dt = t - p.startTime
            val dt2 = next.startTime - p.startTime
            mProgramView.progress = dt / dt2.toFloat()
        }
        mProgramView.invalidate()

        if (p.imageUrl == null) {
            return
        }

        NetworkBitmap.loadFromNetwork(
            p.imageUrl,
            App.CACHE_DIR,
            mProgramView.widthParams(),
            mProgramView.heightParams()
        ) {
            App.mediumBitmaps[p.imageUrl] = it
            mProgramView.previewImage = it
            mProgramView.invalidate()
        }

    }

    companion object {
        private const val TAG = "TVProgramViewHolder"
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

            programView.progressColor = App.color(
                R.color.lime
            )

            programView.typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            programView.onClickProgramListener = object : OnClickProgramListener {
                override fun onClickProgram(
                    view: View,
                    program: TVProgram?
                ) {
                    if (program == null) {
                        return
                    }
                    
                    App.FAVOURITE_TV_SHOWS.apply {
                        if (containsKey(program.id)) {
                            remove(program.id)
                            return@apply
                        }
                        put(program.id, program)
                    }

                    (view.context as? MainActivity)?.pushFragment(
                        TVShowDetailsFragment.newInstance(
                            program
                        ), FragmentAnimation { f, fragment ->
                            fragment.view?.scaleX = f
                        }
                    )

                }
            }

            programView.setBackgroundColor(
                0xffaaaaaa.toInt()
            )

            programView.size(
                (widthRecyclerView * 0.384057f).toInt(),
                heightRecyclerView
            )

            programView.progressWidth = (
                heightRecyclerView * 0.02951f
            ).toInt()

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