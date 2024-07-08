package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.size
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
                        in 9..<18 -> 0.75f
                        in 18..<36 -> 0.5f
                        else -> 0.25f
                    }


                    setTextSizePx(
                        originalTextSize * downScaleFactor
                    )
                }

                val dSize = originalTextSize - textSize

                boundsLinear(
                    gravity = Gravity.START or Gravity.TOP,
                    width = (measureUnit * 0.5942f).toInt(),
                    top = measureUnit * 0.115942f - dSize * 1.5f
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

private fun TVShowDetailsFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.scaleX = 1.0f-f
        }
    )
}