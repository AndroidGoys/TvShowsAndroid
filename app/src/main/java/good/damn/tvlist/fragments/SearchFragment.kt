package good.damn.tvlist.fragments

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.views.top_bars.TopBarView

class SearchFragment
: StackFragment(),
TextWatcher {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = FrameLayout(
            context
        )

        val topBar = FrameLayout(
            context
        ).apply {
            setBackgroundColor(0)

            boundsFrame(
                width = App.WIDTH,
                height = (getTopInset() +
                    measureUnit * 50.normalWidth()
                ).toInt()
            )
        }

        EditText(
            context
        ).apply {

            boundsFrame(
                width = App.WIDTH,
                height = topBar.heightParams()
            )

            addTextChangedListener(
                this@SearchFragment
            )

            topBar.addView(
                this
            )
        }

        val recyclerView = RecyclerView(
            context
        ).apply {
            setBackgroundColorId(
                R.color.background
            )
        }

        layout.apply {
            addView(recyclerView)
            addView(topBar)
        }

        return layout
    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {

    }


    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) = Unit


    override fun afterTextChanged(
        s: Editable?
    ) = Unit

}