package good.damn.tvlist.views.interactions.interfaces

import android.view.MotionEvent
import android.view.View

interface OnTapListener {

    fun onDoubleTap(
        v: View,
        event: MotionEvent
    )

    fun onSingleTap()
}