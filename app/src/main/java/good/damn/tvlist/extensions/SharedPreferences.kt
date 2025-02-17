package good.damn.tvlist.extensions

import android.content.SharedPreferences

private const val keyAccess = "access_token"
private const val keyRefresh = "refresh_token"
private const val keyLastTimeSec = "lastTimeSec"

fun SharedPreferences.hasAccessToken() =
    contains(keyAccess)

fun SharedPreferences.hasRefreshToken() =
    contains(keyRefresh)

fun SharedPreferences.lastTimeSeconds() =
    getInt(keyLastTimeSec,0)

fun SharedPreferences.accessToken() = getString(
    keyAccess,
    null
)

fun SharedPreferences.refreshToken() = getString(
    keyRefresh,
    null
)

fun SharedPreferences.Editor.removeAccessToken() =
    remove(keyAccess)


fun SharedPreferences.Editor.removeRefreshToken() =
    remove(keyRefresh)

fun SharedPreferences.Editor.lastTimeSeconds(
    s: Int
) = putInt(keyLastTimeSec, s)

fun SharedPreferences.Editor.accessToken(
    s: String
) = putString(keyAccess, s)

fun SharedPreferences.Editor.refreshToken(
    s: String
) = putString(keyRefresh, s)
