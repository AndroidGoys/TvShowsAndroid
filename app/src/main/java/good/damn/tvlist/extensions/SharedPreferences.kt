package good.damn.tvlist.extensions

import android.content.SharedPreferences

fun SharedPreferences.accessToken() = getString(
    "access_token",
    null
)

fun SharedPreferences.refreshToken() = getString(
    "refresh_token",
    null
)

fun SharedPreferences.Editor.accessToken(
    s: String
) = putString("access_token",s)

fun SharedPreferences.Editor.refreshToken(
    s: String
) = putString("refresh_token", s)