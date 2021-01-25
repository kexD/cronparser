package dev.kexidt.cronparser.input

import dev.kexidt.cronparser.model.CronCharacter
import dev.kexidt.cronparser.model.Expression
import dev.kexidt.cronparser.model.CharacterType
import dev.kexidt.cronparser.model.exception.InvalidExpressionFormatException

object InputParser {
    fun parseExpression(inputString: String): Expression {
        val parts = inputString.split(" ")

        if (parts.size != 5)
            throw InvalidExpressionFormatException("Cron expression should have 5 space separated parts")

        return Expression(
            min = parseCharacters(parts[0], CharacterType.MIN),
            hour = parseCharacters(parts[1], CharacterType.HOUR),
            dayOfMonth = parseCharacters(parts[2], CharacterType.DAY_OF_MONTH),
            month = parseCharacters(parts[3], CharacterType.MONTH),
            dayOfWeek = parseCharacters(parts[4], CharacterType.DAY_OF_WEEK)
        )
    }

    fun parseCharacters(expressionPart: String, characterType: CharacterType): List<CronCharacter> {
        val characterStrings = expressionPart.split(",")

        // Run validation to check there is no funky formatting errors, before attempting to parse
        characterStrings.forEach { validateCharacter(it) }

        return characterStrings.map {
            buildCharacterFromString(it, characterType)
        }
    }

    fun validateCharacter(character: String) {
        if (!character.matches(Regex("^([0-9]+(-[0-9]+)?|\\*)(/[0-9]+)?")))
            throw InvalidExpressionFormatException("Character `$character` is not valid cron character")
    }

    private fun buildCharacterFromString(characterString: String, characterType: CharacterType): CronCharacter {
        val parts = characterString.split("/")

        val start: Int
        var end: Int? = null
        var increment: Int? = null

        // * special case, min to max range
        if (parts[0] == "*") {
            start = characterType.min
            end = characterType.max
            increment = 1 // Setting default increment, will be overridden if explicitly supplied
        } else {
            val range = parts[0].split("-")
            start = range[0].toInt()

            if (range.size == 2) {
                end = range[1].toInt()
                increment = 1 // Setting default increment, will be overridden if explicitly supplied
            }
        }

        if (parts.size == 2) {
            increment = parts[1].toInt()

            if (end == null)
                end = characterType.max
        }

        return CronCharacter(start, end, increment)
    }
}