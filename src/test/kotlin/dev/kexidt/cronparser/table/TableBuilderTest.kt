package dev.kexidt.cronparser.table

import dev.kexidt.cronparser.model.CharacterType
import dev.kexidt.cronparser.model.CronCharacter
import dev.kexidt.cronparser.model.Expression
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TableBuilderTest {
    @Test
    fun `should respect min and max`() {
        val expected = "minute        10 12 14 16 18"
        val actual = TableBuilder.buildRow(chars = listOf(CronCharacter(10, 19, 2)), type = CharacterType.MIN)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should not contain duplicates`() {
        val expected = "minute        10 15 20"
        val actual = TableBuilder.buildRow(
            chars = listOf(CronCharacter(10, 20, 5), CronCharacter(10, 25, 10)),
            type = CharacterType.MIN
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should print single value for non-range characters`() {
        val expected = "minute        11"
        val actual = TableBuilder.buildRow(chars = listOf(CronCharacter(11)), type = CharacterType.MIN)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should build correct table for every midnight cron`() {
        val everyMidnight = Expression(
            min = listOf(CronCharacter(0)),
            hour = listOf(CronCharacter(0)),
            dayOfMonth = listOf(CronCharacter(1, 31, 1)),
            month = listOf(CronCharacter(1, 12, 1)),
            dayOfWeek = listOf(CronCharacter(0, 6, 1)),
            command = "/usr/bin/find"
        )

        val expected =
            """
            minute        0
            hour          0
            day of month  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
            month         1 2 3 4 5 6 7 8 9 10 11 12
            day of week   0 1 2 3 4 5 6
            command       /usr/bin/find
            """.trimIndent()

        val actual = TableBuilder.buildTable(everyMidnight)

        assertThat(actual).isEqualTo(expected)
    }
}
