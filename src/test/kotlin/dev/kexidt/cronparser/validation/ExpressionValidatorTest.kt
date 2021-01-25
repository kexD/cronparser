package dev.kexidt.cronparser.validation

import dev.kexidt.cronparser.model.CronCharacter
import dev.kexidt.cronparser.model.Expression
import dev.kexidt.cronparser.model.exception.InvalidExpressionFormatException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ExpressionValidatorTest {

    @Test
    fun `should allow valid expression`() {
        ExpressionValidator.validateExpression(
            Expression(VALID_MIN, VALID_HOUR, VALID_DAY_OF_MONTH, VALID_MONTH, VALID_DAY_OF_WEEK, VALID_COMMAND)
        )
    }

    @Nested
    @DisplayName("Minutes")
    inner class Time {
        @Test
        fun `should catch minutes starting too high`() {
            val expression = Expression(listOf(CronCharacter(60)),
                VALID_HOUR, VALID_DAY_OF_MONTH, VALID_MONTH, VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch minutes ending too high`() {
            val expression = Expression(listOf(CronCharacter(0, 61, 1)),
                VALID_HOUR, VALID_DAY_OF_MONTH, VALID_MONTH, VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch hours starting too high`() {
            val expression = Expression(
                VALID_MIN, listOf(CronCharacter(25)), VALID_DAY_OF_MONTH, VALID_MONTH, VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch hours ending too high`() {
            val expression = Expression(
                VALID_MIN, listOf(CronCharacter(0, 25, 1)), VALID_DAY_OF_MONTH, VALID_MONTH, VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }
    }

    @Nested
    @DisplayName("Dates")
    inner class Dates {
        @Test
        fun `should catch day of month starting with zero`() {
            val expression = Expression(
                VALID_MIN, VALID_HOUR, listOf(CronCharacter(0, 31, 1)), VALID_MONTH, VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch day of month starting too late`() {
            val expression = Expression(
                VALID_MIN, VALID_HOUR, listOf(CronCharacter(32)), VALID_MONTH, VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch day of month ending too late`() {
            val expression = Expression(
                VALID_MIN, VALID_HOUR, listOf(CronCharacter(1, 32, 1)), VALID_MONTH, VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch month starting with zero`() {
            val expression = Expression(
                VALID_MIN, VALID_HOUR, VALID_DAY_OF_MONTH, listOf(CronCharacter(0, 6, 1)), VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch month starting too late`() {
            val expression = Expression(
                VALID_MIN, VALID_HOUR, VALID_DAY_OF_MONTH, listOf(CronCharacter(13)), VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch month ending too late`() {
            val expression = Expression(
                VALID_MIN, VALID_HOUR, VALID_DAY_OF_MONTH, listOf(CronCharacter(1, 13, 1)), VALID_DAY_OF_WEEK, VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch day of week ending too late`() {
            val expression = Expression(
                VALID_MIN, VALID_HOUR, VALID_DAY_OF_MONTH, VALID_MONTH, listOf(CronCharacter(4, 7, 1)), VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }

        @Test
        fun `should catch day of week starting too late`() {
            val expression = Expression(
                VALID_MIN, VALID_HOUR, VALID_DAY_OF_MONTH, VALID_MONTH, listOf(CronCharacter(7)), VALID_COMMAND)

            assertThrows<InvalidExpressionFormatException> {
                ExpressionValidator.validateExpression(expression)
            }
        }
    }

    companion object {
        private val VALID_MIN = listOf(CronCharacter(31))
        private val VALID_HOUR = listOf(CronCharacter(4))
        private val VALID_DAY_OF_MONTH = listOf(CronCharacter(14))
        private val VALID_MONTH = listOf(CronCharacter(5))
        private val VALID_DAY_OF_WEEK = listOf(CronCharacter(4))
        private val VALID_COMMAND = "/usr/bin/find"
    }
}