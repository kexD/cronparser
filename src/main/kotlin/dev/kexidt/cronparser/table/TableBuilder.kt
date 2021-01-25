package dev.kexidt.cronparser.table

import dev.kexidt.cronparser.model.CharacterType
import dev.kexidt.cronparser.model.CronCharacter
import dev.kexidt.cronparser.model.Expression
import java.lang.StringBuilder

object TableBuilder {
    fun buildTable(expression: Expression): String {
        val sb = StringBuilder()

        sb.appendLine(buildRow(expression.min, CharacterType.MIN))
        sb.appendLine(buildRow(expression.hour, CharacterType.HOUR))
        sb.appendLine(buildRow(expression.dayOfMonth, CharacterType.DAY_OF_MONTH))
        sb.appendLine(buildRow(expression.month, CharacterType.MONTH))
        sb.append(buildRow(expression.dayOfWeek, CharacterType.DAY_OF_WEEK))

        return sb.toString()
    }

    fun buildRow(chars: List<CronCharacter>, type: CharacterType): String {
        val values = mutableSetOf<Int>()

        for (character in chars) {
            if (character.end == null) {
                 values.add(character.start)
            } else {
                for (i in character.start..character.end step character.increment!!) {
                    values.add(i)
                }
            }
        }

        return type.prettyName.padEnd(14) + values.toList().sorted().joinToString(" ")
    }
}