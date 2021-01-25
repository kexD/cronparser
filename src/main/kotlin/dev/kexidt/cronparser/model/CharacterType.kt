package dev.kexidt.cronparser.model

enum class CharacterType(
    val min: Int = 0,
    val max: Int,
    val prettyName: String // Used in validation messages
) {
    MIN(max = 59, prettyName = "minute"),
    HOUR(max = 23, prettyName = "hour"),
    DAY_OF_MONTH(min = 1, max = 31, "day of month"),
    MONTH(min = 1, max = 12, "month"),
    DAY_OF_WEEK(max = 6, prettyName = "day of week"),
}
