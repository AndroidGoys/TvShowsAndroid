package good.damn.tvlist.views.user

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.withAlpha

class UserInfoView(
    context: Context
): View(
    context
) {

    var nickname: String? = null
    var date: String? = null
    var email: String? = null

    private val mPaintNickname = Paint().apply {
        color = App.color(
            R.color.text
        )

        typeface = App.font(
            R.font.open_sans_bold,
            context
        )
    }

    private val mPaintAdd = Paint().apply {
        color = App.color(
            R.color.text
        ).withAlpha(0.63f)

        typeface = App.font(
            R.font.open_sans_regular,
            context
        )
    }

    private var mNickY = 0f
    private var mDateY = 0f
    private var mEmailY = 0f

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(
            changed,
            left,
            top,
            right,
            bottom
        )

        mPaintNickname.textSize = height * 0.377f
        mPaintAdd.textSize = height * 0.2174f

        mNickY = mPaintNickname.textSize
        mDateY = mPaintNickname.textSize + mPaintAdd.textSize * 1.5f
        mEmailY = mDateY + mPaintAdd.textSize
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        if (nickname != null) {
            canvas.drawText(
                nickname!!,
                0f,
                mNickY,
                mPaintNickname
            )
        }

        if (date != null) {
            canvas.drawText(
                date!!,
                0f,
                mDateY,
                mPaintAdd
            )
        }

        if (email != null) {
            canvas.drawText(
                email!!,
                0f,
                mEmailY,
                mPaintAdd
            )
        }

    }

}