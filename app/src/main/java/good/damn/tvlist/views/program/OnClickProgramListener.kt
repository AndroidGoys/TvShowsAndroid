package good.damn.tvlist.views.program

import good.damn.tvlist.network.api.models.TVProgram

interface OnClickProgramListener {

    fun onClickProgram(
        program: TVProgram?
    )

}