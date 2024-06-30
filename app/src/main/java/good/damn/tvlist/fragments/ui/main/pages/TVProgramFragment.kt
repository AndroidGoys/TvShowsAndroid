package good.damn.tvlist.fragments.ui.main.pages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment

class TVProgramFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val recyclerView = RecyclerView(
            context
        )

        recyclerView.setBackgroundColor(
            0xffff0000.toInt()
        )

        return recyclerView
    }


}