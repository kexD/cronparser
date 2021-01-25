package dev.kexidt.cronparser

import dev.kexidt.cronparser.input.InputParser
import dev.kexidt.cronparser.model.exception.InvalidExpressionFormatException
import dev.kexidt.cronparser.table.TableBuilder
import dev.kexidt.cronparser.validation.ExpressionValidator

object Application {
    private val USAGE =
        """
        No cron expression supplied
        Usage: cronparser <expression>
        """.trimIndent()

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size != 1) {
            println(USAGE)
            return
        }

        try {
            val expression = InputParser.parseExpression(args[0])
            ExpressionValidator.validateExpression(expression)

            println(TableBuilder.buildTable(expression))
        } catch (ex: InvalidExpressionFormatException) {
            println("Invalid expression format: ${ex.message}")
        }
    }
}
