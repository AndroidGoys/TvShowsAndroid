package good.damn.tvlist.api.interfaces.enteties.shows

import good.damn.tvlist.api.interfaces.enteties.AgeLimit
import good.damn.tvlist.api.interfaces.enteties.TvEntity

interface TvShow : TvEntity {
    val name: String
    val assessment: Float
    val ageLimit: AgeLimit
    val previewUrl: String

    fun getDetails(callback: ()->Result<TvShowDetails>)
    fun getChannels(callback: () -> Result<TvShowChannel>)
}