package good.damn.tvlist.api.interfaces.enteties.shows

interface TvShows {
    val current: Iterable<TvShows>
    fun moveNext(callback: () -> Result<Boolean>)
}