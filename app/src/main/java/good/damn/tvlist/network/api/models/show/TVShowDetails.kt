package good.damn.tvlist.network.api.models.show

import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.extractArray
import good.damn.tvlist.network.api.models.enums.CensorAge
import org.json.JSONObject

data class TVShowDetails(
    val id: Int,
    val name: String,
    val rating: Float,
    val censorAge: CensorAge,
    val imagesUrl: Array<TVShowImage>?,
    val description: String?
) {
    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVShowDetails? {

            val id = json.extract(
                "id"
            ) as? Int ?: return null

            val name = json.extract(
                "name"
            ) as? String ?: return null

            val rating = (json.extract(
                "assessment"
            ) as? Double)?.toFloat() ?: 0.0f

            val censorAge = CensorAge.TEEN

            val description = json.extract(
                "description"
            ) as? String

            val imagesArr = run {
                val arr = json.extractArray(
                    "frames"
                ) ?: return@run null

                return@run Array(arr.length()) {
                    TVShowImage(
                        arr.get(it) as? String
                    )
                }
            }

            return TVShowDetails(
                id,
                name,
                rating,
                censorAge,
                imagesArr,
                description
            )
        }
    }
}