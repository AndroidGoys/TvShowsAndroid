package good.damn.tvlist.network.api.models

import android.util.Log
import good.damn.tvlist.extensions.extract
import good.damn.tvlist.network.api.models.enums.CensorAge
import org.json.JSONObject
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

data class TVProgram(
    val id: Long = -1,
    val name: String = "",
    val description: String? = null,
    val censorAge: CensorAge = CensorAge.ADULT,
    val startTime: Long = -1,
    val rating: Float = 0.0f,
    val shortName: String? = null,
    val startTimeString: String = "",
    val imageUrl: String? = null,
    val channelName: String? = null
) {

    companion object {
        private const val TAG = "TVProgram"

        fun createFromJSON(
            json: JSONObject,
            channelName: String? = null
        ): TVProgram {

            val id = try {
                json.get(
                    "id"
                ) as Long
            } catch (e: Exception) {
                Log.d(TAG, "createFromJSON: NO ID TV PROGRAM: ${e.message}")
                return TVProgram()
            }

            val name = try {
                json.get(
                    "title"
                ) as String
            } catch (e: Exception) {
                Log.d(TAG, "createFromJSON: NO NAME TV_PROGRAM: ${e.message}")
                return TVProgram()
            }

            val desc = json.extract(
                "desc"
            ) as? String

            val startTime = (json.extract(
                "timestart"
            ) as? Int)?.toLong()

            if (startTime == null) {
                Log.d(TAG, "createFromJSON: NO_START_TIME")
                return TVProgram()
            }
            
            val c = Calendar.getInstance()
            c.time = Date(startTime * 1000L)

            val hour = c.get(
                Calendar.HOUR_OF_DAY
            )

            val mins = c.get(
                Calendar.MINUTE
            )

            val hourString = "${hour / 10}${hour % 10}"
            val minutesString = "${mins/10}${mins % 10}"
            
            return TVProgram(
                id,
                name,
                desc,
                CensorAge.ADULT,
                startTime = startTime,
                rating = Random.nextFloat() * 5f,
                shortName = if (name.length > 15)
                    name.substring(0,15) + "…"
                else null,
                startTimeString = "$hourString:$minutesString",
                imageUrl = "https://www.cats.org.uk/media/13136/220325case013.jpg?width=500&height=333.49609375",
                channelName = channelName
            )

        }
    }

}