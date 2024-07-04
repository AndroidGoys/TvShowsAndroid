package good.damn.kamchatka.views.interactions.interfaces

import android.view.MotionEvent
import android.view.View

interface OnActionListener {

    fun onDown(
        v: View,
        event: MotionEvent
    )

    fun onUp(
        v: View,
        event: MotionEvent
    )

}