package dev.kexidt.cronparser.input

import dev.kexidt.cronparser.model.CronCharacter
import dev.kexidt.cronparser.model.Expression
import dev.kexidt.cronparser.model.exception.InvalidExpressionFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("Input Parser Test")
internal class InputParserTest {

    @Nested
    @DisplayName("Cron Character Validation")
    inner class CronCharacterValidation {
        @ParameterizedTest
        @ValueSource(strings = ["*", "15", "5/11", "5-10", "5-10/11"])
        fun shouldPassCharValidationForPartialChronCharacters(characterString: String) {
            InputParser.validateCharacter(characterString)
        }

        @ParameterizedTest
        @ValueSource(strings = ["**", "*-10", "0-*"])
        fun shouldFailForAnyUsedInWrongPlaces(characterString: String) {
            assertThrows<InvalidExpressionFormatException> {
                InputParser.validateCharacter(characterString)
            }
        }

        @ParameterizedTest
        @ValueSource(strings = ["", "/10", "0-", "10/", "-10", "10-/5"])
        fun shouldFailForMissingMandatoryParts(characterString: String) {
            assertThrows<InvalidExpressionFormatException> {
                InputParser.validateCharacter(characterString)
            }
        }
    }

    @Nested
    @DisplayName("Cron Character Parsing")
    inner class CharacterParsing {

        @Test
        fun `should parse any character`() {
            val expected = listOf(CronCharacter(start = 0, end = null, increment = 1))
            val actual = InputParser.parseCharacters("*")
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `should parse any with explicit increment`() {
            val expected = listOf(CronCharacter(start = 0, end = null, increment = 3))
            val actual = InputParser.parseCharacters("*/3")
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `should parse exact time`() {
            val expected = listOf(CronCharacter(start = 15, end = null, increment = null))
            val actual = InputParser.parseCharacters("15")
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `should parse start time with implicit end and explicit increment`() {
            val expected = listOf(CronCharacter(start = 15, end = null, increment = 5))
            val actual = InputParser.parseCharacters("15/5")
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `should parse start time with explicit end and implicit increment`() {
            val expected = listOf(CronCharacter(start = 10, end = 20, increment = 1))
            val actual = InputParser.parseCharacters("10-20")
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `should parse start time with explicit end increment`() {
            val expected = listOf(CronCharacter(start = 10, end = 20, increment = 3))
            val actual = InputParser.parseCharacters("10-20/3")
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `should parse multiple characters from part of expression`() {
            val expected = listOf(
                CronCharacter(start = 10, end = 20, increment = 3),
                CronCharacter(start = 0, end = null, increment = 20),
                CronCharacter(start = 33, end = null, increment = null),
                CronCharacter(start = 50, end = 59, increment = 1),
            )

            val actual = InputParser.parseCharacters("10-20/3,*/20,33,50-59")
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Nested
    @DisplayName("Input String Parsing")
    inner class InputStringParsing {

        @Test
        fun `should throw exception if not 5 parts in input string`() {
            assertThrows<InvalidExpressionFormatException> {
                InputParser.parseExpression("* *")
            }
        }

        @Test
        fun `should parse every minute expression correctly`() {
            val expected = Expression(
                min = listOf(CronCharacter(start = 0, end = null, 1)),
                hour = listOf(CronCharacter(start = 0, end = null, 1)),
                dayOfMonth = listOf(CronCharacter(start = 0, end = null, 1)),
                month = listOf(CronCharacter(start = 0, end = null, 1)),
                dayOfWeek = listOf(CronCharacter(start = 0, end = null, 1)),
            )

            val actual = InputParser.parseExpression("* * * * *")
            assertThat(actual).isEqualTo(expected)
        }
    }
}