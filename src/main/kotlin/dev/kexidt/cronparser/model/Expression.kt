package dev.kexidt.cronparser.model

/**
General idea:
- represent expression as data class with 5 fields of type List<CronCharacter>
- each CronCharacter corresponds to , separated part of the expression for day/week/whatever
- each CronCharacter has start (mandatory), end (optional, default is either start or max value),
increment

Not sure:
- should exact time be separate type of character?
 */
data class Expression(
    val min: List<CronCharacter>,
    val hour: List<CronCharacter>,
    val dayOfMonth: List<CronCharacter>,
    val month: List<CronCharacter>,
    val dayOfWeek: List<CronCharacter>,
    val command: String
)