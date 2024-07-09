package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.ContextThemeWrapper
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
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.size
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.round.RoundedImageView

class TVShowDetailsFragment
: StackFragment() {

    var program: TVProgram? = null

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

        ViewUtils.topBarStyleMain(
            layoutTopBar,
            measureUnit
        )

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

        // CHAPTER: Rate tv show
        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.rate_tv_show
            )
        )


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

        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.trailers
            )
        )

        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.ratings_and_reviews
            )
        )

        contentLayout.addView(
            chapterTextView(
                context,
                measureUnit,
                R.string.channels
            )
        )



        scrollView.apply {
            val pad = layoutTopBar.heightParams()
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