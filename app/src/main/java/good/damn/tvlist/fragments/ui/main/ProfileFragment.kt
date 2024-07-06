package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.View
import android.widget.Button
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack
import java.io.Closeable

class ProfileFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )

        val btnBack = ButtonBack.createLinear(
            context,
            measureUnit
        )

        layout.apply {
            addView(btnBack)
        }

        btnBack.setOnClickListener(
            this::onClickBtnBack
        )

        return layout
    }

}

private fun ProfileFragment.onClickBtnBack(
    v: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.alpha = 1.0f-f
        }
    )
}