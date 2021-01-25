package dev.kexidt.cronparser.model

data class CronCharacter(
    val start: Int,
    val end: Int?,
    val increment: Int?
)
