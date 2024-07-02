package good.damn.tvlist.network.api.models

data class TVChannel(
    val name: String,
    val imageUrl: String? = null,
    val programs: Array<TVProgram>? = null
)