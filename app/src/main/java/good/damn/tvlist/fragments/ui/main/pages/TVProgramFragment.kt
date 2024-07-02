package good.damn.tvlist.fragments.ui.main.pages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVChannelAdapter
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.network.api.models.TVChannel
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.network.api.models.enums.CensorAge

class TVProgramFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val recyclerView = RecyclerView(
            context
        )

        recyclerView.setBackgroundColorId(
            R.color.background
        )

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        recyclerView.clipToPadding = false
        (measureUnit * 0.17149f).toInt().let {
            padding ->
            recyclerView.setPadding(
                0,
                padding,
                0,
                padding
            )
        }

        val channels = arrayListOf(
            TVChannel(
                "CTC",
                programs = arrayOf(
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    )
                )
            ),
            TVChannel(
                "Первый канал",
                programs = arrayOf(
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    )
                )
            ),
            TVChannel(
                "2х2",
                programs = arrayOf(
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    )
                )
            ),
            TVChannel(
                "CTC",
                programs = arrayOf(
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    )
                )
            ),
            TVChannel(
                "Первый канал",
                programs = arrayOf(
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    ),
                    TVProgram(
                        0,
                        "Кухня",
                        CensorAge.TEEN_ADULT,
                        1719909862L,
                        4.9f
                    )
                )
            ),
            TVChannel(
                "2х2"
            ),
            TVChannel(
                "CTC"
            ),
            TVChannel(
                "Первый канал"
            ),
            TVChannel(
                "2х2"
            )
        )

        recyclerView.adapter = TVChannelAdapter(
            App.WIDTH,
            App.HEIGHT,
            channels
        )

        return recyclerView
    }


}