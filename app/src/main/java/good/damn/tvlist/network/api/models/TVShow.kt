package good.damn.tvlist.network.api.models

import good.damn.tvlist.Unicode
import good.damn.tvlist.extensions.extract
import org.json.JSONObject

data class TVShow(
    val id: Int,
    val name: String,
    val rating: Float,
    val previewUrl: String? = null,
    val shortName: String? = null
) {
    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVShow? {

            val id = json.extract(
                "id"
            ) as? Int ?: return null

            val name = json.extract(
                "name"
            ) as? String ?: return null

            val rating = (json.extract(
                "assessment"
            ) as? Double)?.toFloat() ?: 0.0f

            val imageUrl = json.extract(
                "previewUrl"
            ) as? String

            val shortName = if (name.length >= 20)
                name.substring(0,20) + Unicode.DOTS
            else null

            return TVShow(
                id,
                name,
                rating,
                imageUrl,
                shortName
            )

        }
    }
}