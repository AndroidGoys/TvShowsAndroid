package good.damn.tvlist.views.tab_bars

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.utils.ViewUtils

class TabBar(
    context: Context
): HorizontalScrollView(
    context
), View.OnClickListener {

    companion object {
        private const val TAG = "TabBar"
    }

    var interval = 0f

    private var mSelectedView: View? = null

    private val mLayout = LinearLayout(
        context
    )

    init {
        mLayout.orientation = LinearLayout
            .HORIZONTAL

        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false

        clipToPadding = false

        addView(mLayout)
    }

    fun addTab(
        title: String,
        @DrawableRes drawId: Int = 0
    ) {
        TabView(
            context
        ).apply {
            val h = this@TabBar.heightParams()
            val w = this@TabBar.widthParams()
            boundsLinear(
                height = h,
                width = (w * 63.normalWidth()).toInt(),
                left = interval
            )
            text = title
            textSizeFactor = 0.5f
            typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )
            strokeWidth = h * 0.03f
            strokeColor = App.color(
                R.color.lime
            )
            cornerRadius = h * 0.5f

            if (drawId != 0) {
                drawable = App.drawable(
                    R.drawable.ic_star_out
                )
            }

            setBackgroundColor(0)

            setOnClickListener(
                this@TabBar
            )

            mLayout.addView(
                this
            )
        }

        mSelectedView = mLayout.getChildAt(0)
        mSelectedView?.setBackgroundColorId(
            R.color.lime
        )
    }

    override fun onClick(
        v: View?
    ) {
        Log.d(TAG, "onClick: $v")

        mSelectedView?.apply {
            setBackgroundColor(0)
            invalidate()
        }
        v?.apply {
            setBackgroundColorId(
                R.color.lime
            )
            invalidate()
        }

        mSelectedView = v
    }

}