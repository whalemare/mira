package ru.whalemare

import Main
import org.junit.Test
import org.mockito.Mockito
import picocli.CommandLine
import ru.whalemare.repository.Repository
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class HelpGenerator {

    val file = File("help.md").apply {
        createNewFile()
    }

    @Test
    fun generate() {
        file.writeText("")

        Main.repository = Mockito.mock(Repository::class.java)
        val mainCommandLine = Main.getCommandLine()

        file.appendText(
                "Help for MIRA\n"
                        + "-----------"
                        + "\n\n"
        )
        printToFile(Main.getCommandLine(), "-h")

        file.appendText("\nList of commands\n" +
                "----------------")
        val commands = mainCommandLine.subcommands
        commands.forEach { key, commandLine ->
            file.appendText("\n")
            file.appendText("### $key\n")
            printToFile(commandLine, "-?")
        }
    }

    fun printToFile(commandLine: CommandLine, command: String) {
        file.appendText("```\n")
        commandLine.parseWithHandler(
                CommandLine.RunAll(),
                PrintStream(FileOutputStream(file, true)),
                command
        )
        file.appendText("```\n")
    }

}