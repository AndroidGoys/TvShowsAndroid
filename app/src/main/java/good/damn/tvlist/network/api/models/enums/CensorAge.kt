package good.damn.tvlist.network.api.models.enums

enum class CensorAge(
    val type: Byte,
    val age: Byte
) {
    CHILD(0b0000, 0),
    CHILD_TEEN(0b0001,6),
    TEEN(0b0010, 12),
    TEEN_ADULT(0b0011, 16),
    ADULT(0b0100, 18);

    companion object {
        fun find(
            type: Byte
        ) = when (type) {
            CHILD.type -> CHILD
            CHILD_TEEN.type -> CHILD_TEEN
            TEEN.type -> TEEN
            TEEN_ADULT.type -> TEEN_ADULT
            else -> ADULT
        }
    }
}