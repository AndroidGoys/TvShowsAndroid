package good.damn.tvlist.views.program

import android.view.View
import good.damn.tvlist.network.api.models.TVProgram

interface OnClickProgramListener {

    fun onClickProgram(
        view: View,
        program: TVProgram?
    )

}