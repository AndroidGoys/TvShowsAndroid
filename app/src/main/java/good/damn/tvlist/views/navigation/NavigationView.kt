package good.damn.tvlist.views.navigation

import android.content.Context
import android.graphics.Canvas
import androidx.cardview.widget.CardView

class NavigationView(
    context: Context
): CardView(
    context
) {

    var items: Array<NavigationItem>? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        items?.forEach {
            it.vector?.draw(
                canvas
            )
        }
    }

    override fun setBackgroundColor(color: Int) {
        setCardBackgroundColor(
            color
        )
    }

}