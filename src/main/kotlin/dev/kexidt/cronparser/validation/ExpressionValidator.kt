package dev.kexidt.cronparser.validation

import dev.kexidt.cronparser.model.CronCharacter
import dev.kexidt.cronparser.model.Expression
import dev.kexidt.cronparser.model.CharacterType
import dev.kexidt.cronparser.model.exception.InvalidExpressionFormatException

object ExpressionValidator {
    /**
     * Sanity check to verify we didn't get invalid start / end times in input
     */
    fun validateExpression(expression: Expression) {
        expression.min.forEach { validateCharacter(it, CharacterType.MIN) }
        expression.hour.forEach { validateCharacter(it, CharacterType.HOUR) }
        expression.dayOfMonth.forEach { validateCharacter(it, CharacterType.DAY_OF_MONTH) }
        expression.month.forEach { validateCharacter(it, CharacterType.MONTH) }
        expression.dayOfWeek.forEach { validateCharacter(it, CharacterType.DAY_OF_WEEK) }
    }

    private fun validateCharacter(
        character: CronCharacter,
        constraint: CharacterType
    ) {
        if (character.start < constraint.min || character.start > constraint.max) {
            throw InvalidExpressionFormatException(
                "${constraint.prettyName} of ${character.start} is not in allowed range (${constraint.min}, ${constraint.max})")
        }

        if (character.end != null && (character.end < constraint.min || character.end > constraint.max)) {
            throw InvalidExpressionFormatException(
                "${constraint.prettyName} of ${character.end} is not in allowed range (${constraint.min}, ${constraint.max})")
        }
    }
}