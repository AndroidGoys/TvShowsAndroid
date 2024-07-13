package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.models.tv_show.TVShowReview
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.rate.RateView
import good.damn.tvlist.views.text_fields.TextFieldRound
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle

class TVShowPostReviewFragment
: StackFragment() {

    companion object {
        private const val TAG = "TVShowPostReviewFragmen"
        fun newInstance(
            tvShow: TVShowReview?,
            grade: Byte
        ) = TVShowPostReviewFragment().apply {
            this.review = tvShow
            this.grade = grade
        }
    }

    var review: TVShowReview? = null
    var grade: Byte = 0

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )

        TopBarView(
            context,
            getTopInset()
        ).apply {
            defaultTopBarStyle(
                measureUnit,
                getTopInset()
            )

            textViewTitle.text = review?.title

            btnBack.setOnClickListener(
                this@TVShowPostReviewFragment::onClickBtnBack
            )

            layout.addView(
                this
            )
        }

        AppCompatTextView(
            context
        ).apply {

            setTextColorId(
                R.color.text
            )

            setTextSizePx(
                measureUnit * 25.normalWidth()
            )

            typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )

            setText(
                R.string.your_rate
            )

            boundsLinear(
                Gravity.TOP or Gravity.CENTER_HORIZONTAL,
                top = measureUnit * 36.normalWidth()
            )

            layout.addView(
                this
            )
        }

        RateView(
            context
        ).apply {

            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                height = (measureUnit * 38.normalWidth()).toInt(),
                width = (measureUnit * 347.normalWidth()).toInt(),
                top = measureUnit * 32.normalWidth()
            )

            setStarsRate(
                grade
            )

            layout.addView(
                this
            )
        }

        TextFieldRound(
            context
        ).apply {

            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                width = (measureUnit * 347.normalWidth()).toInt(),
                height = (measureUnit * 228.normalWidth()).toInt(),
                top = measureUnit * 33.normalWidth()
            )

            typeface = App.font(
                R.font.open_sans_regular,
                context
            )

            setTextSizePx(
                heightParams() * 0.06579f
            )

            setHintTextColor(
                App.color(
                    R.color.primaryColor
                ).withAlpha(0.3f)
            )

            setTextColorId(
                R.color.primaryColor
            )

            strokeColor = App.color(
                R.color.lime
            )

            cornerRadius = heightParams() * 0.078947f
            strokeWidth = heightParams() * 0.00877f
            gravity = Gravity.START or Gravity.TOP

            (widthParams() * 0.0461f).toInt().let {
                setPadding(
                    it,
                    it,
                    it,
                    it
                )
            }

            setHint(
                R.string.hint_text_review
            )

            layout.addView(
                this
            )
        }

        return layout
    }
}

private fun TVShowPostReviewFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f - f
        }
    )
}