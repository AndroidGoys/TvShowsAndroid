package good.damn.tvlist.views.toasts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.views.round.RoundView

class ToastImage(
    context: Context
): CardView(
    context
) {

    val imageView = AppCompatImageView(
        context
    )

    val textView = AppCompatTextView(
        context
    ).apply {
        setTextColor(
            0xffffffff.toInt()
        )

        gravity = Gravity.CENTER_VERTICAL

        typeface = App.font(
            R.font.open_sans_semi_bold,
            context
        )
    }

    init {
        setBackgroundColorId(
            R.color.background_toast
        )
        addView(imageView)
        addView(textView)
    }

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        super.setLayoutParams(params)
        if (params == null) {
            return
        }

        val width = params.width
        val height = params.height

        val s = (height * 0.4705f)
            .toInt()

        imageView.boundsFrame(
            Gravity.CENTER_VERTICAL or Gravity.TOP,
            width = s,
            height = s
        )

        textView.boundsFrame(
            width = width - s,
            height = height,
            left = s.toFloat()
        )

    }

}