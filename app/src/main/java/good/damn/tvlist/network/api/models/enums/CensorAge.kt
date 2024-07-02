package good.damn.tvlist.network.api.models.enums

enum class CensorAge(
    val type: Int,
    val age: Int
) {
    CHILD(0, 0),
    CHILD_TEEN(1,6),
    TEEN(2, 12),
    TEEN_ADULT(3, 16),
    ADULT(4, 18)
}