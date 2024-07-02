package good.damn.tvlist.api.interfaces.enteties.channels

interface TvChannelDetails : TvChannel {
    val description: String
    val viewUrls: Iterable<String>
}