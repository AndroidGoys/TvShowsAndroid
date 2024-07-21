package good.damn.tvlist.network.api.models.show

data class TVShowChannelDate(
    val time: String,
    val date: String,
    val imageUrl: String? = null,
    val name: String? = null
)