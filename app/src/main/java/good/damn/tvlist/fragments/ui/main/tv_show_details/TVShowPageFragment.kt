package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import java.util.Calendar
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVShowChannelsAdapter
import good.damn.tvlist.adapters.recycler_view.TVShowImagesAdapter
import good.damn.tvlist.cache.CacheFile
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.getDayString
import good.damn.tvlist.extensions.getMonthString
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
import good.damn.tvlist.models.tv_show.TVShowReview
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.network.api.services.TVShowService
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.utils.BuildUtils
import good.damn.tvlist.utils.NotificationUtils
import good.damn.tvlist.utils.PermissionUtils
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.RateView
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.statistic.StatisticsView
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.round.RoundedImageView
import good.damn.tvlist.views.statistic.ProgressTitleDraw
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle

class TVShowPageFragment
: StackFragment() {

    companion object {
        private const val TAG = "TVShowDetailsFragment"
        const val DIR_PREVIEW = "bitmapProgramPreview"

        fun newInstance(
            program: TVProgram?
        ) = TVShowPageFragment().apply {
            this.program = program
        }

    }

    var program: TVProgram? = null

    private var mBlurView: BlurShaderView? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val showService = TVShowService(
            context.cacheDir
        )

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
            text = program?.shortName ?: program?.name
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

            program?.name?.let {
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

        // Censor age
        AppCompatTextView(
            context
        ).apply {
            setTextColor(
                App.color(
                    R.color.text
                ).withAlpha(0.49f)
            )

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            text = "${program?.censorAge?.age}+"

            setTextSizePx(
                measureUnit * 11.normalWidth()
            )

            boundsLinear(
                top = measureUnit * 7.normalWidth(),
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
        RateView(
            context
        ).apply {

            boundsLinear(
                left = marginHorizontal,
                right = marginHorizontal,
                width = -1,
                top = measureUnit * 23.normalWidth(),
                height = (measureUnit * 38.normalWidth()).toInt()
            )

            contentLayout.addView(
                this
            )
        }

        AppCompatTextView(
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
                this@TVShowPageFragment::onClickPostReview
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

            text = program?.description

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

        // CHAPTER: Screenshots
        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.tv_show_shots
            )
        )

        RecyclerView(
            context
        ).apply {

            boundsLinear(
                width = measureUnit,
                height = (measureUnit * 156.normalWidth()).toInt(),
                top = measureUnit * 19.normalWidth()
            )

            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            clipToPadding = false

            val margin = (measureUnit * 15.normalWidth()).toInt()
            val pad = marginHorizontal - margin

            setPadding(
                marginHorizontal.toInt(),
                0,
                pad.toInt(),
                0
            )

            addItemDecoration(
                MarginItemDecoration(
                    0,
                    0,
                    margin,
                    0
                )
            )

            showService.getImages {
                adapter = TVShowImagesAdapter(
                    it,
                    (heightParams() * 1.77777f).toInt(),
                    heightParams()
                )
            }

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

        StatisticsView(
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

            val c = 123456

            count = c.toString()

            progressTitles = arrayOf(
                ProgressTitleDraw(
                    "5",
                    23425f / c
                ),
                ProgressTitleDraw(
                    "4",
                    65842f / c
                ),
                ProgressTitleDraw(
                    "3",
                    25845f / c
                ),
                ProgressTitleDraw(
                    "2",
                    12452f / c
                ),
                ProgressTitleDraw(
                    "1",
                    12347f / c
                )
            )

            progressColor = App.color(
                R.color.lime
            )

            rating = program?.rating ?: 0.0f

            boundsLinear(
                gravity = Gravity.CENTER_HORIZONTAL,
                width = (measureUnit * 348.normalWidth()).toInt(),
                height = (measureUnit * 99.normalWidth()).toInt(),
                top = measureUnit * 0.048309f
            )

            setOnClickListener(
                this@TVShowPageFragment::onClickShowReviews
            )

            contentLayout.addView(
                this
            )
        }

        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.channels
            )
        )

        RecyclerView(
            context
        ).apply {

            boundsLinear(
                top = measureUnit * 20.normalWidth(),
                width = measureUnit,
                height = (measureUnit * 110.normalWidth()).toInt()
            )

            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            clipToPadding = false

            val margin = (measureUnit * 20.normalWidth()).toInt()
            val pad = marginHorizontal - margin

            setPadding(
                marginHorizontal.toInt(),
                0,
                pad.toInt(),
                0
            )

            addItemDecoration(
                MarginItemDecoration(
                    0,
                    0,
                    margin,
                    0
                )
            )

            showService.getChannelPointers {
                adapter = TVShowChannelsAdapter(
                    it,
                    heightParams(),
                    heightParams()
                )
            }

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
                height = (w * 1.23882f).toInt(),
                top = measureUnit * 0.03623f
            )

            cornerRadius = heightParams() * 0.06331f

            setBackgroundColor(
                0xffc4c4c4.toInt()
            )

            program?.imageUrl?.let { url ->
                NetworkBitmap.loadFromNetwork(
                    url,
                    App.CACHE_DIR,
                    DIR_PREVIEW,
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

        // Add notification
        RoundedImageView(
            context
        ).apply {
            val s = (measureUnit * 24.normalWidth()).toInt()
            drawable = App.drawable(
                R.drawable.ic_alarm
            )

            val preview = layoutRootContent.getChildAt(1)

            boundsFrame(
                gravity = Gravity.END or Gravity.TOP,
                width = s,
                height = s,
                right = marginHorizontal + s * 0.75f,
                top = preview.topHeightParams() - s * 0.5f
            )

            setOnClickListener(
                this@TVShowPageFragment::onClickScheduleAlarm
            )

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
                this@TVShowPageFragment::onClickShare
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
            setBackgroundColorId(
                R.color.background
            )
            addView(scrollView)
            addView(topBar)
        }

        return layout
    }

    override fun onResume() {
        super.onResume()
        mBlurView?.apply {
            startRenderLoop()
            onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        mBlurView?.apply {
            stopRenderLoop()
            onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBlurView?.clean()
    }

    // Manifest.permission.POST_NOTIFICATIONS 33
    @SuppressLint("InlinedApi")
    private fun onClickScheduleAlarm(
        v: View
    ) {

        val program = program
            ?: return

        val context = activity?.applicationContext
            ?: return

        if (BuildUtils.isTiramisu33() and (
                !PermissionUtils.checkNotifications(context)
                )
        ) {
            requestPermission(
                Manifest.permission.POST_NOTIFICATIONS
            )
            return
        }

        NotificationUtils.scheduleNotification(
            context,
            "${program.name}${program.startTime}${program.channelName}".hashCode(),
            getString(R.string.time_for_watch),
            program.name +
                " - " +
                "${program.startTimeString} " +
                "${getString(R.string.on_channel)} " +
                "\"${program.channelName}\"",
            (program.startTime - 900) * 1000L, // 900 - 15 minutes
            dirName = DIR_PREVIEW,
            imageUrl = program.imageUrl
        )

        Toast.makeText(
            context,
            "Set",
            Toast.LENGTH_SHORT
        ).show()
    }

}

private fun TVShowPageFragment.onClickShowReviews(
    v: View
) {
    val program = program ?: return
    pushFragment(
        TVShowReviewsFragment.newInstance(
            TVShowReview(
                program.id,
                program.shortName ?: program.name
            )
        ),
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = f
        }
    )
}

private fun TVShowPageFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f - f
        }
    )
}

private fun TVShowPageFragment.onClickShare(
    v: View
) {

    val program = program
        ?: return

    if (program.imageUrl == null) {
        shareTVShow(program)
        return
    }

    val file = CacheFile.cacheFile(
        App.CACHE_DIR,
        TVShowPageFragment.DIR_PREVIEW,
        program.imageUrl.hashCode().toString()
    )

    val context = v.context

    val uri = FileProvider.getUriForFile(
        context,
        context.packageName + ".provider",
        file
    )
    shareTVShow(
        program,
        uri
    )

    Log.d(
        "TVShowDetailsFragment",
        "onClickShare: FILE_IMAGE: $file ${file.exists()} $uri"
    )
}

private fun TVShowPageFragment.onClickPostReview(
    v: View
) {
    val program = program
        ?: return

    pushFragment(
        TVShowPostReviewFragment.newInstance(
            TVShowReview(
                program.id,
                program.shortName ?: program.name
            )
        ),
        FragmentAnimation {
            f, fragment ->
            fragment.view?.alpha = f
        }
    )
}

private fun TVShowPageFragment.shareTVShow(
    program: TVProgram,
    data: Uri? = null
) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = program.startTime * 1000L

    val channel = program.channelName ?: ""

    val text = "${getString(R.string.lets_see)} " +
        "\"${program.name}\" " +
        "${calendar.getDayString()}.${calendar.getMonthString()} " +
        "${getString(R.string.at_time)} " +
        "${program.startTimeString} " +
        "${getString(R.string.on_channel)} $channel " +
        "${getString(R.string.in_app)} ${getString(R.string.app_name)}?"

    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "image/*"
        putExtra(Intent.EXTRA_TEXT, text)
        putExtra(Intent.EXTRA_STREAM, data)
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    startActivity(
        Intent.createChooser(
            intent,
            null
        )
    )
}

private fun TVShowPageFragment.chapterTextView(
    context: Context,
    measureUnit: Int,
    @StringRes textId: Int
) = TextView(
    context
).apply {
    setText(
        textId
    )

    typeface = App.font(
        R.font.open_sans_bold,
        context
    )

    setTextSizePx(
        measureUnit * 0.05314f
    )

    setTextColorId(
        R.color.text
    )

    boundsLinear(
        left = measureUnit * 0.07004f,
        top = measureUnit * 0.08816f
    )
}