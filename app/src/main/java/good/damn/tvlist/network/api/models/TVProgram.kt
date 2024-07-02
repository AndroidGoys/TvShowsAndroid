package good.damn.tvlist.network.api.models

import good.damn.tvlist.network.api.models.enums.CensorAge

data class TVProgram(
    val channelId: Int,
    val name: String,
    val censorAge: CensorAge,
    val startTime: Long,
    val rating: Float
)