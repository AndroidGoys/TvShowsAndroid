package good.damn.tvlist.view_holders

import android.content.Context
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.size

class TVSearchTitleViewHolder(
    private val mTextView: AppCompatTextView
): RecyclerView.ViewHolder(
    mTextView
) {
    fun setTitle(
        text: String
    ) {
        mTextView.text = text
    }

    companion object {
        fun create(
            context: Context,
            holderWidth: Int,
            holderHeight: Int
        ): TVSearchTitleViewHolder {
            val textView = AppCompatTextView(
                context
            ).apply {
                size(
                    holderWidth,
                    holderHeight
                )
                setTextSizePx(
                    holderHeight * 0.42372f
                )

                typeface = App.font(
                    R.font.open_sans_bold,
                    context
                )

                setTextColorId(
                    R.color.text
                )

                gravity = Gravity.CENTER_VERTICAL
            }

            return TVSearchTitleViewHolder(
                textView
            )
        }
    }
}