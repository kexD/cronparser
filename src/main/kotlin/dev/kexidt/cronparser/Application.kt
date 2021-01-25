package dev.kexidt.cronparser

object Application {
    private const val USAGE = """
        No cron expression supplied
        Usage: cronparser <expression>
    """

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size != 1) {
            println(USAGE)
        }
    }
}