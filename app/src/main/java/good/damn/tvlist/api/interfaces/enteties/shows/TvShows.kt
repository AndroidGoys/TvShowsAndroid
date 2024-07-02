package good.damn.tvlist.api.interfaces.enteties.shows

interface TvShows<out T> where T : TvShow{
    val current: Iterable<T>
    fun moveNext(callback: (result: Result<Boolean>) -> Unit)
}