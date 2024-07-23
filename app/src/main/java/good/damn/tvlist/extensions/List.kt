package good.damn.tvlist.extensions

fun <T> List<T>.middle(): T? {
    val middleIndex = (size - 1) / 2
    return if (middleIndex < 0)
        null
    else get(middleIndex)
}

fun <T> List<T>.extract(
    index: Int
) = if (index > size || index < 0) {
    null
} else get(index)