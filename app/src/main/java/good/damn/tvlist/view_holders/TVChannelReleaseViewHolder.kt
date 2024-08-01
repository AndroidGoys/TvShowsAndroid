package good.damn.tvlist.view_holders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.main.tv_details.TVShowPageFragment
import good.damn.tvlist.network.api.models.TVChannelRelease
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.views.program.OnClickChannelReleaseListener
import good.damn.tvlist.views.program.TVChannelReleaseView
import java.util.Calendar

class TVChannelReleaseViewHolder(
    private val mReleaseView: TVChannelReleaseView
): RecyclerView.ViewHolder(
    mReleaseView
) {
    private val mCalendar = Calendar.getInstance()

    fun onBindViewHolder(
        p: TVChannelRelease,
        next: TVChannelRelease?
    ) {
        mReleaseView.cacheRelease = p
        mReleaseView.title = p.shortName ?: p.name
        mReleaseView.timeString = p.startTimeString
        mReleaseView.rating = p.rating
        mReleaseView.isFavourite = App.FAVOURITE_TV_SHOWS
            .containsKey(p.showId)
        mReleaseView.age = "${p.censorAge.age}+"
        mReleaseView.previewImage = null

        mReleaseView.progress = 0f
        mReleaseView.invalidate()

        if (next != null) {
            val t = mCalendar.timeInMillis / 1000
            val dt = t - p.timeStart
            val dt2 = next.timeStart - p.timeStart
            mReleaseView.progress = dt / dt2.toFloat()
        } else {
            mReleaseView.progress = 0f
        }
        mReleaseView.post {
            mReleaseView.startProgressAnimation()
        }

        if (p.previewUrl == null) {
            return
        }

        NetworkBitmap.loadFromNetwork(
            p.previewUrl,
            App.CACHE_DIR,
            "bitmapChannelRelease",
            mReleaseView.widthParams(),
            mReleaseView.heightParams()
        ) {
            mReleaseView.previewImage = it
            mReleaseView.startImageAnimation()
        }

    }

    companion object {
        private const val TAG = "TVChannelReleaseViewHolder"
        fun create(
            context: Context,
            widthRecyclerView: Int,
            heightRecyclerView: Int
        ): TVChannelReleaseViewHolder {
            val releaseView = TVChannelReleaseView(
                context
            ).apply {
                textTintColor = 0xffffffff.toInt()
                sizeAgeFactor = 0.05365f
                sizeTimeFactor = 0.05365f
                sizeTitleFactor = 0.07317f
                progressColor = App.color(
                    R.color.lime
                )

                size(
                    (widthRecyclerView * 0.384057f).toInt(),
                    heightRecyclerView
                )

                progressWidth = (
                    heightRecyclerView * 0.02951f
                    ).toInt()

                setBackgroundColor(
                    0xffaaaaaa.toInt()
                )

                typeface = App.font(
                    R.font.open_sans_semi_bold,
                    context
                )
                onLongClickProgramListener = object : OnClickChannelReleaseListener {
                    override fun onClickChannelRelease(
                        view: View,
                        release: TVChannelRelease?
                    ) {
                        if (release == null) {
                            return
                        }

                        val v = view as? TVChannelReleaseView
                            ?: return

                        App.FAVOURITE_TV_SHOWS.apply {
                            if (v.isFavourite) {
                                remove(release.showId)
                                v.isFavourite = false
                            } else {
                                put(release.showId, release)
                                v.isFavourite = true
                            }

                            v.invalidate()
                        }
                    }
                }

                onClickChannelReleaseListener = object : OnClickChannelReleaseListener {
                    override fun onClickChannelRelease(
                        view: View,
                        release: TVChannelRelease?
                    ) {
                        if (release == null) {
                            return
                        }

                        (view.context as? MainActivity)?.pushFragment(
                            TVShowPageFragment.newInstance(
                                release
                            ), FragmentAnimation { f, fragment ->
                                fragment.view?.y = App.HEIGHT * (1.0f-f)
                            }
                        )

                    }
                }
            }

            (releaseView.widthParams() * 0.05031f)
            .toInt().let { leftPadding ->
                releaseView.setPadding(
                    leftPadding,
                    0,
                    leftPadding,
                    (releaseView.heightParams() * 0.12578f).toInt()
                )
            }

            releaseView.cornerRadius = heightRecyclerView * 0.08277f

            return TVChannelReleaseViewHolder(
                releaseView
            )
        }
    }
}