package dev.kexidt.cronparser.input

import dev.kexidt.cronparser.model.CronCharacter
import dev.kexidt.cronparser.model.Expression
import dev.kexidt.cronparser.model.exception.InvalidExpressionFormatException

object InputParser {
    fun parseExpression(inputString: String): Expression {
        val parts = inputString.split(" ")

        if (parts.size != 5)
            throw InvalidExpressionFormatException()

        return Expression(
            min = parseCharacters(parts[0]),
            hour = parseCharacters(parts[1]),
            dayOfMonth = parseCharacters(parts[2]),
            month = parseCharacters(parts[3]),
            dayOfWeek = parseCharacters(parts[4])
        )
    }

    fun parseCharacters(expressionPart: String): List<CronCharacter> {
        val characterStrings = expressionPart.split(",")

        // Run validation to check there is no funky formatting errors, before attempting to parse
        characterStrings.forEach { validateCharacter(it) }

        return characterStrings.map {
            val parts = it.split("/")
            val range = parts[0].split("-")

            // Handling * as special case here
            val start = if (range[0] == "*") 0 else range[0].toInt()
            val end = if (range.size == 2) range[1].toInt() else null

            // TODO: handling * as special case here is messy, might be worth to separate exact & range CronCharacters
            val increment = if (parts.size == 2)
                parts[1].toInt()
            // If * was used or end specified, then it's a range with increment, even if increment not specified
            else if (range[0] == "*" || end != null)
                1
            else null

            CronCharacter(start, end, increment)
        }
    }

    fun validateCharacter(character: String) {
        if (!character.matches(Regex("^([0-9]+(-[0-9]+)?|\\*)(/[0-9]+)?")))
            throw InvalidExpressionFormatException()
    }
}