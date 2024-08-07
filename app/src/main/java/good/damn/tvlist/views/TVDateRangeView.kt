package good.damn.tvlist.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.getDateTimeString
import java.util.Calendar

class TVDateRangeView(
    context: Context
): View(
    context
) {

    companion object {
        private val mCalendar = Calendar.getInstance()
    }

    var timeSecondsLeft: Int = 0
        set(v) {
            field = v
            mCalendar.timeInMillis = v * 1000L
            mDateLeft = mCalendar.getDateTimeString()
        }

    var timeSecondsRight: Int = 0
        set(v) {
            field = v
            mCalendar.timeInMillis = v * 1000L
            mDateRight = mCalendar.getDateTimeString()
        }

    private var mDateLeft: String = ""
    private var mDateRight: String = ""

    private val mPaintText = Paint()

    private var mDateRightX = 0f

    init {
        mPaintText.color = 0xff000000.toInt()
        mPaintText.typeface = App.font(
            R.font.open_sans_semi_bold,
            context
        )
    }

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        super.setLayoutParams(
            params
        )

        if (params == null) {
            return
        }

        mPaintText.textSize = params.height.toFloat()

        mDateRightX = params.width - mPaintText.measureText(
            "00.00.0000 00:00"
        )
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        canvas.drawText(
            mDateLeft,
            0f,
            mPaintText.textSize,
            mPaintText
        )

        canvas.drawText(
            mDateRight,
            mDateRightX,
            mPaintText.textSize,
            mPaintText
        )

    }

}