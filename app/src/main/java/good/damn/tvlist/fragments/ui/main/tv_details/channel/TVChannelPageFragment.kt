package good.damn.tvlist.fragments.ui.main.tv_details.channel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.FileProvider
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.cache.CacheFile
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.rgba
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.topHeightParams
import good.damn.tvlist.extensions.topParams
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.main.stream.StreamFragment
import good.damn.tvlist.fragments.ui.main.tv_details.TVShowPageFragment
import good.damn.tvlist.fragments.ui.main.tv_details.TVPostReviewFragment
import good.damn.tvlist.fragments.ui.main.tv_details.TVReviewsFragment
import good.damn.tvlist.models.tv_show.TVShowReview
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.models.user.TVUserReview
import good.damn.tvlist.network.api.services.ReviewService
import good.damn.tvlist.network.api.services.TVChannel2Service
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.utils.ShareUtils
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.utils.ViewUtils.Companion.chapterTextView
import good.damn.tvlist.views.rate.OnRateClickListener
import good.damn.tvlist.views.rate.RateView
import good.damn.tvlist.views.round.RoundedImageView
import good.damn.tvlist.views.statistic.ProgressTitleDraw
import good.damn.tvlist.views.statistic.StatisticsView
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle
import kotlinx.coroutines.launch

class TVChannelPageFragment
: StackFragment(),
OnRateClickListener {

    companion object {
        private const val TAG = "TVChannelPageFragment"
        const val DIR_PREVIEW = "bitmapChannelPreview"

        fun newInstance(
            channel: TVChannel2?
        ) = TVChannelPageFragment().apply {
            this.channel = channel
        }

    }

    var channel: TVChannel2? = null
    var channelDescription: String? = null

    private var urlView: String? = null
    private var mUserReview: TVUserReview? = null

    private var mBlurView: BlurShaderView? = null
    private var mReviewService: ReviewService? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val channelService = TVChannel2Service()

        val marginHorizontal = measureUnit * 0.07004f

        val layout = FrameLayout(
            context
        )

        val layoutRootContent = FrameLayout(
            context
        )
        val contentLayout = ViewUtils.verticalLinear(
            context
        )
        val scrollView = ScrollView(
            context
        )

        val topBar = TopBarView(
            context,
            getTopInset()
        )

        topBar.defaultTopBarStyle(
            measureUnit,
            getTopInset()
        )

        mBlurView = BlurShaderView(
            context,
            scrollView,
            blurRadius = 5,
            scaleFactor = 0.35f,
            shadeColor = App.color(
                R.color.background
            ).withAlpha(
                0.5f
            ).rgba()
        ).apply {
            boundsFrame(
                width = topBar.widthParams(),
                height = topBar.heightParams()
            )
            topBar.addView(this,0)
            startRenderLoop()
        }


        topBar.btnBack.setOnClickListener(
            this::onClickBtnBack
        )

        topBar.textViewTitle.apply {
            alpha = 0f
            text = channel?.shortName ?: channel?.name
        }

        scrollView.apply {
            setBackgroundColorId(
                R.color.background
            )

            contentLayout.post {
                var isShown = false
                val triggerY = contentLayout.y * 1.3f
                viewTreeObserver.addOnScrollChangedListener {
                    if (!isShown && scrollY > triggerY) {
                        isShown = true
                        topBar.textViewTitle.animate()
                            .alpha(1.0f)
                            .start()
                    }

                    if (isShown && scrollY < triggerY) {
                        isShown = false
                        topBar.textViewTitle.animate()
                            .alpha(0.0f)
                            .start()
                    }
                }
            }
        }



        // Title
        TextView(
            context
        ).apply {

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            setTextColorId(
                R.color.text
            )

            val originalTextSize = measureUnit * 0.08937f

            channel?.name?.let {
                text = it

                val downScaleFactor = when(it.length) {
                    in 0..<9 -> 1.0f
                    in 9..<18 -> 0.85f
                    in 18..<36 -> 0.6f
                    in 36..<72 -> 0.5f
                    else -> 0.35f
                }


                setTextSizePx(
                    originalTextSize * downScaleFactor
                )
            }

            boundsLinear(
                width = (measureUnit * 0.5942f).toInt(),
                left = marginHorizontal
            )

            contentLayout.addView(
                this
            )
        }


        // The cinema studio
        // who develops this show
        AppCompatTextView(
            context
        ).apply {
            setTextSizePx(
                measureUnit * 15.normalWidth()
            )

            typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            setTextColor(
                App.color(
                    R.color.text
                ).withAlpha(0.49f)
            )

            text = "Yellow Studio Inc."

            boundsLinear(
                width = (measureUnit * 247.normalWidth()).toInt(),
                top = measureUnit * 5.normalWidth(),
                left = marginHorizontal
            )
            contentLayout.addView(
                this
            )
        }

        // CHAPTER: Rate tv show
        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.rate_tv_show
            )
        )
        val rateView = RateView(
            context
        ).apply {

            boundsLinear(
                left = marginHorizontal,
                right = marginHorizontal,
                width = -1,
                top = measureUnit * 23.normalWidth(),
                height = (measureUnit * 38.normalWidth()).toInt()
            )

            this.onRateClickListener = this@TVChannelPageFragment

            contentLayout.addView(
                this
            )
        }

        val textViewPostReview = AppCompatTextView(
            context
        ).apply {

            setTextColorId(
                R.color.navigationIcon
            )

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            setText(
                R.string.write_review
            )

            setTextSizePx(
                measureUnit * 11.normalWidth()
            )

            boundsLinear(
                left = marginHorizontal,
                top = measureUnit * 30.normalWidth()
            )

            setOnClickListener(
                this@TVChannelPageFragment::onClickPostReview
            )

            contentLayout.addView(
                this
            )
        }

        // CHAPTER: description
        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.description
            )
        )

        AppCompatTextView(
            context
        ).apply {

            typeface = App.font(
                R.font.open_sans_regular,
                context
            )

            setTextColor(
                App.color(
                    R.color.text
                ).withAlpha(
                    0.63f
                )
            )

            setTextSizePx(
                measureUnit * 0.036231f
            )

            App.IO.launch {
                val id = channel?.id
                    ?: return@launch

                val details = channelService.getChannelDetails(
                    id,
                    fromCache = !App.NETWORK_AVAILABLE
                )

                if (details == null) {
                    return@launch
                }

                App.ui {
                    text = details.description
                    channelDescription = details.description
                    urlView = details.viewUrl
                }
            }

            boundsLinear(
                gravity = Gravity.START or Gravity.TOP,
                top = measureUnit * 0.043478f,
                width = -1,
                left = marginHorizontal,
                right = marginHorizontal
            )

            contentLayout.addView(
                this
            )
        }

        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.ratings_and_reviews
            )
        )

        val statisticsView = StatisticsView(
            context
        ).apply {

            typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            textSizeRatingFactor = 0.40404f
            textSizeCountFactor = 0.11f
            textSizeCategoryFactor = 0.151515f

            progressBackColor = App.color(
                R.color.searchViewBack
            )

            count = 0.toString()

            progressTitles = arrayOf(
                ProgressTitleDraw(
                    "5",
                    0.1f
                ),
                ProgressTitleDraw(
                    "4",
                    0.1f
                ),
                ProgressTitleDraw(
                    "3",
                    0.1f
                ),
                ProgressTitleDraw(
                    "2",
                    0.1f
                ),
                ProgressTitleDraw(
                    "1",
                    0.1f
                )
            )

            progressColor = App.color(
                R.color.lime
            )

            rating = channel?.rating ?: 0.0f

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL,
                width = (measureUnit * 348.normalWidth()).toInt(),
                height = (measureUnit * 99.normalWidth()).toInt(),
                top = measureUnit * 0.048309f
            )

            setOnClickListener(
                this@TVChannelPageFragment::onClickShowReviews
            )

            contentLayout.addView(
                this
            )
        }

        layoutRootContent.addView(
            contentLayout
        )

        // Preview tv show
        RoundedImageView(
            context
        ).apply {

            val w = measureUnit * 0.492753f
            boundsFrame(
                Gravity.CENTER_HORIZONTAL,
                width = w.toInt(),
                height = w.toInt(),
                top = measureUnit * 0.03623f
            )

            cornerRadius = heightParams() * 0.25f

            setBackgroundColor(
                0xffc4c4c4.toInt()
            )

            channel?.imageUrl?.let { url ->
                NetworkBitmap.loadFromNetwork(
                    url,
                    App.CACHE_DIR,
                    TVShowPageFragment.DIR_PREVIEW,
                    widthParams(),
                    heightParams()
                ) {
                    bitmap = it
                    invalidate()
                }
            }

            layoutRootContent.addView(
                this
            )
        }


        // Share option
        RoundedImageView(
            context
        ).apply {
            val s = (measureUnit * 24.normalWidth()).toInt()
            drawable = App.drawable(
                R.drawable.ic_share
            )

            val prevView = layoutRootContent.getChildAt(1)

            boundsFrame(
                gravity = Gravity.TOP or Gravity.END,
                width = s,
                height = s,
                right = measureUnit * 105.normalWidth(),
                top = prevView.topHeightParams().toFloat() + measureUnit * 0.10386f
            )

            setOnClickListener(
                this@TVChannelPageFragment::onClickShare
            )

            layoutRootContent.addView(
                this
            )
        }

        // Play option
        RoundedImageView(
            context
        ).apply {

            val s = (measureUnit * 0.13768f).toInt()

            val preview = layoutRootContent.getChildAt(1)

            boundsFrame(
                gravity = Gravity.END,
                width = s,
                height = s,
                right = marginHorizontal,
                top = preview.topHeightParams() + measureUnit * 0.0628f
            )

            background = null

            drawable = App.drawable(
                R.drawable.ic_play_fill
            )

            layoutRootContent.addView(
                this
            )

            setOnClickListener(
                this@TVChannelPageFragment::onClickPlay
            )

            contentLayout.boundsFrame(
                width = App.WIDTH,
                height = -2,
                top = topParams().toFloat()
            )
        }

        scrollView.apply {
            isHorizontalScrollBarEnabled = false
            isVerticalScrollBarEnabled = false
            clipToPadding = false

            val topPadding = getTopInset() +
                topBar.heightParams()

            setPadding(0,
                topPadding,
                0,
                topPadding
            )

            addView(layoutRootContent)
        }

        layout.apply {
            addView(scrollView)
            addView(topBar)
        }

        val id = channel?.id?.toLong()
            ?: return layout

        mReviewService = ReviewService(
            id,
            "channels"
        )

        App.IO.launch {

            val distrosReview = mReviewService?.getDistributionReviews(
                fromCache = !App.NETWORK_AVAILABLE
            )

            App.ui {
                distrosReview?.let { dist ->
                    if (dist.count == 0) {
                        return@ui
                    }

                    statisticsView.apply {
                        progressTitles = Array(5) {
                            val d = dist.distribution[it]

                            ProgressTitleDraw(
                                d.title,
                                if (d.count == 0) 0.1f
                                else d.count.toFloat() / dist.count
                            )
                        }
                        count = dist.count.toString()

                        updateStats()
                        invalidate()
                    }
                }
            }

            if (App.TOKEN_AUTH == null) {
                return@launch
            }

            val userReview = mReviewService?.getUserReview(
                false
            ) ?: return@launch

            mUserReview = userReview.result

            if (userReview.errorStringId != -1) {
                App.ui {
                    toast(userReview.errorStringId)
                }
                return@launch
            }

            val review = mUserReview
                ?: return@launch

            App.ui {
                textViewPostReview.setText(
                    R.string.update_review
                )

                rateView.setStarsRate(
                    review.rating
                )
            }
        }

        return layout
    }

    override fun onResume() {
        super.onResume()

        if (!isFragmentFocused()) {
            return
        }

        mBlurView?.apply {
            startRenderLoop()
            onResume()
        }
    }

    override fun onPause() {
        super.onPause()

        if (!isFragmentFocused()) {
            return
        }

        mBlurView?.apply {
            stopRenderLoop()
            onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBlurView?.clean()
    }

    override fun onFocusChanged(
        isFragmentFocused: Boolean
    ) {
        super.onFocusChanged(
            isFragmentFocused
        )

        if (isFragmentFocused) {
            mBlurView?.apply {
                onResume()
                startRenderLoop()
            }
            return
        }

        mBlurView?.apply {
            onPause()
            stopRenderLoop()
        }

    }

    override fun onClickRate(
        rate: Byte
    ) {
        createReview(
            grade = rate
        )
    }

    private fun createReview(
        grade: Byte
    ) {
        if (App.TOKEN_AUTH == null) {
            toast(R.string.need_sign_in_for_reviews)
            return
        }

        val channel = channel
            ?: return

        val reviewService = mReviewService
            ?: return

        pushFragment(
            TVPostReviewFragment.newInstance(
                TVShowReview(
                    channel.id.toLong(),
                    channel.shortName ?: channel.name,
                ),
                grade,
                mUserReview,
                reviewService
            ),
            FragmentAnimation {
                    f, fragment ->
                fragment.view?.alpha = f
            }
        )
    }

    private fun onClickPostReview(
        v: View
    ) {
        createReview(
            grade = 0
        )
    }

    private fun onClickShowReviews(
        v: View
    ) {
        val channel = channel ?: return
        val reviewService = mReviewService ?: return
        pushFragment(
            TVReviewsFragment.newInstance(
                TVShowReview(
                    channel.id.toLong(),
                    channel.shortName ?: channel.name
                ),
                reviewService
            ),
            FragmentAnimation { f, fragment ->
                fragment.view?.x = App.WIDTH * (1.0f - f)
            }
        )
    }

    private fun onClickPlay(
        v: View
    ) {
        pushFragment(
            StreamFragment.newInstance(
                urlView ?: ""
            ),
            FragmentAnimation {
                f, fragment ->
                fragment.view?.x = App.WIDTH * (1.0f-f)
            }
        )
    }

}

private fun TVChannelPageFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f - f
        }
    )
}

private fun TVChannelPageFragment.onClickShare(
    v: View
) {

    val channel = channel
        ?: return

    if (channel.imageUrl == null) {
        shareChannel(
            channel,
            channelDescription
        )
        return
    }

    val file = CacheFile.cacheFile(
        App.CACHE_DIR,
        TVShowPageFragment.DIR_PREVIEW,
        channel.imageUrl.hashCode().toString()
    )

    val context = v.context

    val uri = FileProvider.getUriForFile(
        context,
        context.packageName + ".provider",
        file
    )
    shareChannel(
        channel,
        channelDescription,
        uri
    )

    Log.d(
        "TVShowDetailsFragment",
        "onClickShare: FILE_IMAGE: $file ${file.exists()} $uri"
    )
}

private fun TVChannelPageFragment.shareChannel(
    channel: TVChannel2,
    channelDescription: String?,
    imageUri: Uri? = null
) {
    val context = context
        ?: return

    val subtext = channelDescription ?: ""

    val firstParag = subtext.indexOf("\n\n")
    val shortText = if (firstParag == -1)
        subtext
    else subtext.substring(0, firstParag)

    ShareUtils.shareWithImage(
        context,
        "${getString(R.string.app_name)} " +
            "${getString(R.string.has_channel)} " +
            "\"${channel.name}\" " +
            "${getString(R.string.with_rating)} " +
            "${channel.rating}.\n\n$shortText \n\n" +
            getString(R.string.lets_see_it),
        imageUri
    )
}