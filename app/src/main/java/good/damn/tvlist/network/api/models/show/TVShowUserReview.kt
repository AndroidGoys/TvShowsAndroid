package good.damn.tvlist.network.api.models.show

data class TVShowUserReview(
    val username: String,
    val rating: Byte,
    val textReview: String? = null,
    val usernameImageUrl: String? = null
)