package dev.kexidt.cronparser.model.exception

import java.lang.RuntimeException

class InvalidExpressionFormatException(override val message: String) : RuntimeException()
