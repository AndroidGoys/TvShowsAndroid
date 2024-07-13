package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
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
import good.damn.tvlist.views.buttons.RoundButton
import good.damn.tvlist.views.rate.RateView
import good.damn.tvlist.views.text_fields.TextFieldRound
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle

class TVShowPostReviewFragment
: StackFragment(),
TextWatcher {

    companion object {
        private const val TAG = "TVShowPostReviewFragmen"
        private const val MAX_LENGTH_REVIEW = 700
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

    private var mTextViewCounter: AppCompatTextView? = null

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

            addTextChangedListener(
                this@TVShowPostReviewFragment
            )

            filters = arrayOf(
                InputFilter.LengthFilter(
                    MAX_LENGTH_REVIEW
                )
            )

            setHint(
                R.string.hint_text_review
            )

            layout.addView(
                this
            )
        }

        mTextViewCounter = AppCompatTextView(
            context
        ).apply {

            boundsLinear(
                Gravity.TOP or Gravity.END,
                right = measureUnit * 33.normalWidth(),
                top = measureUnit * 10.normalWidth()
            )

            typeface = App.font(
                R.font.open_sans_regular,
                context
            )

            text = "0 / $MAX_LENGTH_REVIEW"

            setTextSizePx(
                measureUnit * 15.normalWidth()
            )

            setTextColor(
                App.color(
                    R.color.primaryColor
                ).withAlpha(
                    0.3f
                )
            )

            layout.addView(
                this
            )
        }

        RoundButton(
            context
        ).apply {

            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                width = (measureUnit * 347.normalWidth()).toInt(),
                height = (measureUnit * 50.normalWidth()).toInt(),
                top = measureUnit * 29.normalWidth()
            )

            text = getString(
                R.string.post_review
            )

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            cornerRadius = heightParams() * 0.25f

            textSizeFactor = 0.34f
            textColor = App.color(
                R.color.btnText
            )

            setBackgroundColorId(
                R.color.btnBack
            )

            layout.addView(
                this
            )

        }

        return layout
    }

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) = Unit

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        mTextViewCounter?.text = "${s?.length} / $MAX_LENGTH_REVIEW"
    }

    override fun afterTextChanged(
        s: Editable?
    ) = Unit
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