package dev.kexidt.cronparser.model

enum class CharacterType(
    val min: Int = 0,
    val max: Int,
    val prettyName: String // Used in validation messages
) {
    MIN(max = 59, prettyName = "Minute"),
    HOUR(max = 23, prettyName = "Hour"),
    DAY_OF_MONTH(min = 1, max = 31, "Day of month"),
    MONTH(min = 1, max = 12, "Month"),
    DAY_OF_WEEK(max = 6, prettyName = "Day of week"),
}