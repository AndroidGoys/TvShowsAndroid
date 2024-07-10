package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVShowChannelsAdapter
import good.damn.tvlist.adapters.recycler_view.TVShowImagesAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.rgba
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.network.api.models.show.TVShowChannelDate
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.RateView
import good.damn.tvlist.views.statistic.StatisticsView
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.round.RoundedImageView
import good.damn.tvlist.views.statistic.ProgressTitleDraw

class TVShowDetailsFragment
: StackFragment() {

    var program: TVProgram? = null

    private var mBlurView: BlurShaderView? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val marginHorizontal = measureUnit * 0.07004f

        val layout = FrameLayout(
            context
        )
        val layoutTopBar = FrameLayout(
            context
        )
        val contentLayout = ViewUtils.verticalLinear(
            context
        )
        val scrollView = ScrollView(
            context
        )

        val textViewTitle = TextView(
            context
        )

        scrollView.setBackgroundColorId(
            R.color.background
        )

        ViewUtils.topBarStyleMain(
            layoutTopBar,
            measureUnit
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
                width = layoutTopBar.widthParams(),
                height = layoutTopBar.heightParams()
            )
            layoutTopBar.addView(this)
            startRenderLoop()
        }

        ButtonBack.createFrame(
            context,
            measureUnit
        ).apply {

            ViewUtils.topBarStyleBtnBack(
                layoutTopBar,
                this
            )

            setOnClickListener(
                this@TVShowDetailsFragment::onClickBtnBack
            )

            layoutTopBar.addView(
                this
            )
        }

        textViewTitle.apply {
            ViewUtils.topBarStyleTitle(
                layoutTopBar,
                this
            )

            alpha = 0f

            text = program?.shortName ?: program?.name

            layoutTopBar.addView(
                this
            )
        }

        // Preview tv show
        RoundedImageView(
            context
        ).apply {

            val w = measureUnit * 0.492753f
            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                width = w.toInt(),
                height = (w * 1.23882f).toInt(),
                top = measureUnit * 0.03623f
            )

            cornerRadius = heightParams() * 0.06331f

            setBackgroundColor(
                0xffc4c4c4.toInt()
            )

            contentLayout.addView(
                this
            )

        }


        // Title and buttons
        LinearLayout(
            context
        ).let { horizontalLayout ->

            horizontalLayout.orientation = LinearLayout
                .HORIZONTAL

            horizontalLayout.setBackgroundColorId(
                R.color.background
            )

            horizontalLayout.boundsLinear(
                height = -2,
                width = -1,
                left = marginHorizontal,
                right = marginHorizontal
            )

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
                    gravity = Gravity.START or Gravity.TOP,
                    width = (measureUnit * 0.5942f).toInt(),
                    top = measureUnit * 0.07971f
                )

                horizontalLayout.addView(
                    this
                )
            }

            AppCompatImageView(
                context
            ).apply {
                val s = (measureUnit * 0.05797f * 2f).toInt()
                setImageDrawable(
                    App.drawable(
                        R.drawable.ic_share
                    )
                )

                scaleX = 0.5f
                scaleY = 0.5f

                boundsLinear(
                    gravity = Gravity.START or Gravity.TOP,
                    width = s,
                    height = s,
                    top = measureUnit * 0.10386f * 0.75f
                )
                horizontalLayout.addView(
                    this
                )
            }

            AppCompatImageButton(
                context
            ).apply {

                val s = (measureUnit * 0.13768f).toInt()

                boundsLinear(
                    gravity = Gravity.START or Gravity.TOP,
                    width = s,
                    height = s,
                    top = measureUnit * 0.0628f
                )

                background = null

                scaleX = 2.0f
                scaleY = 2.0f

                setImageDrawable(
                    App.drawable(
                        R.drawable.ic_play_fill
                    )
                )

                horizontalLayout.addView(
                    this
                )
            }

            contentLayout.addView(
                horizontalLayout
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


            adapter = TVShowImagesAdapter(
                (widthParams() * 0.782608f).toInt(),
                heightParams()
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

            adapter = TVShowChannelsAdapter(
                arrayOf(
                    TVShowChannelDate(
                        "13:10",
                        "26.07",
                        imageUrl = "https://assets-iptv2022.cdnvideo.ru/static/channel/126/logo_256_1655445109.png"
                    ),
                    TVShowChannelDate(
                        "18:10",
                        "28.07",
                        imageUrl = "https://assets-iptv2022.cdnvideo.ru/static/channel/67/logo_256_1692687479.png"
                    ),
                    TVShowChannelDate(
                        "22:10",
                        "29.07",
                        imageUrl = "https://assets-iptv2022.cdnvideo.ru/static/channel/109/logo_256_1655448739.png"
                    ),
                    TVShowChannelDate(
                        "23:40",
                        "01.08",
                        imageUrl = "https://assets-iptv2022.cdnvideo.ru/static/channel/105/logo_256_1655386697.png"
                    )
                ),
                heightParams(),
                heightParams()
            )

            contentLayout.addView(
                this
            )
        }

        scrollView.apply {
            val pad = layoutTopBar.heightParams()
            clipToPadding = false
            setPadding(
                0,
                pad,
                0,
                pad
            )
            addView(contentLayout)
        }

        layout.apply {
            size(-1, -1)
            setBackgroundColorId(
                R.color.background
            )
            addView(scrollView)
            addView(layoutTopBar)
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

    companion object {
        fun newInstance(
            prog: TVProgram
        ) = TVShowDetailsFragment().apply {
            program = prog
        }
    }

}

private fun TVShowDetailsFragment.chapterTextView(
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

private fun TVShowDetailsFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.scaleX = 1.0f-f
        }
    )
}