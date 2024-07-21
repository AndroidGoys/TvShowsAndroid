package good.damn.tvlist.extensions

fun <T> List<T>.extract(
    index: Int
) = if (index > size || index < 0) {
    null
} else get(index)