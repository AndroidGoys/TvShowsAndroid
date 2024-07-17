package good.damn.tvlist.network.api.models

import good.damn.tvlist.Unicode
import good.damn.tvlist.extensions.extract
import org.json.JSONObject

data class TVChannel2(
    val imageUrl: String? = null,
    val name: String,
    val id: Int,
    val rating: Float,
    val shortName: String? = null
) {
    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVChannel2? {

            val name = json.extract(
                "name"
            ) as? String ?: return null

            val id = json.extract(
                "id"
            ) as? Int ?: return null

            val rating = (json.extract(
                "assessment"
            ) as? Double)?.toFloat() ?: 0.0f

            val imageUrl = json.extract(
                "imageUrl"
            ) as? String

            val shortName = if (name.length >= 20)
                name.substring(0,20) + Unicode.DOTS
            else null

            return TVChannel2(
                imageUrl,
                name,
                id,
                rating,
                shortName
            )

        }
    }
}