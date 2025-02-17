package good.damn.tvlist.fragments.ui.main.tv_details

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
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
import good.damn.tvlist.network.api.models.user.TVUserReview
import good.damn.tvlist.network.api.services.ReviewService
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.RoundButton
import good.damn.tvlist.views.rate.RateView
import good.damn.tvlist.views.text_fields.TextFieldRound
import good.damn.tvlist.views.top_bars.TopBarView
import good.damn.tvlist.views.top_bars.defaultTopBarStyle
import kotlinx.coroutines.launch

class TVPostReviewFragment
: StackFragment(),
TextWatcher {

    companion object {
        private const val TAG = "TVShowPostReviewFragmen"
        private const val MAX_LENGTH_REVIEW = 700
        fun newInstance(
            tvShow: TVShowReview?,
            grade: Byte,
            userReview: TVUserReview?,
            reviewService: ReviewService
        ) = TVPostReviewFragment().apply {
            this.review = tvShow
            this.userReview = userReview
            this.grade = grade
            this.reviewService = reviewService
        }
    }

    var review: TVShowReview? = null
    var userReview: TVUserReview? = null
    var grade: Byte = 0
    var reviewService: ReviewService? = null

    private var mTextViewCounter: AppCompatTextView? = null
    private var mTextFieldComment: TextFieldRound? = null
    private var mRateView: RateView? = null

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
                this@TVPostReviewFragment::onClickBtnBack
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

        mRateView = RateView(
            context
        ).apply {

            boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                height = (measureUnit * 38.normalWidth()).toInt(),
                width = (measureUnit * 347.normalWidth()).toInt(),
                top = measureUnit * 32.normalWidth()
            )

            setStarsRate(
                if (grade.toInt() == 0)
                    userReview?.rating ?: 0
                else grade
            )

            layout.addView(
                this
            )
        }

        mTextFieldComment = TextFieldRound(
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
                this@TVPostReviewFragment
            )

            filters = arrayOf(
                InputFilter.LengthFilter(
                    MAX_LENGTH_REVIEW
                )
            )

            setText(
                userReview?.textReview
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

            setOnClickListener(
                this@TVPostReviewFragment::onClickPostReview
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


    private fun onClickPostReview(
        v: View
    ) {
        val rateView = mRateView
            ?: return

        val rating = rateView.getRating()

        if (rating <= 0) {
            toast(R.string.please_rate_show)
            return
        }

        val showId = review?.id

        if (showId == null) {
            toast(R.string.some_error_happens)
            return
        }

        val reviewService = reviewService
            ?: return

        enableInteraction(
            false
        )

        val text = mTextFieldComment?.text?.toString() ?: ""
        App.IO.launch {
            val result = reviewService.postReview(
                rating,
                text
            )

            if (result.errorStringId == -1) {
                App.ui {
                    toast(
                        R.string.review_posted
                    )
                    exitFragment()
                }
                return@launch
            }

            App.ui {
                toast(result.errorStringId)
                enableInteraction(true)
            }
        }

    }

}

private fun TVPostReviewFragment.exitFragment() {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f - f
        }
    )
}

private fun TVPostReviewFragment.onClickBtnBack(
    v: View
) {
    exitFragment()
}