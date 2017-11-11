
import command.*
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Repository
import java.util.*


/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "mira")
class Main : Runnable {

    @Option(names = arrayOf("-h", "--help"),
            usageHelp = true,
            description = arrayOf("Show help message for mira"))
    private val help: Boolean = false

    override fun run() {

    }

    companion object {
        lateinit var repository: Repository

        val scanner = Scanner(System.`in`)

        @JvmStatic
        fun main(args: Array<String>) {
            repository = AuthCommand().call()

            parse(args)
            while (scanner.hasNext()) {
                parse(scanner.nextLine().split(" ").toTypedArray())
            }
            println()
        }

        fun parse(args: Array<String>) {

            val commandLine = CommandLine(Main())
                    .addSubcommand("auth", AuthCommand())
                    .addSubcommand("project", ProjectCommand(repository))
                    .addSubcommand("issue", IssueCommand(repository))
                    .addSubcommand("commit", CommitCommand(repository))
                    .addSubcommand("absent", AbsentCommand(repository))
                    .addSubcommand("start", StartCommand(repository))
                    .setOverwrittenOptionsAllowed(true)
            commandLine.parseWithHandlers(
                    CommandLine.RunLast(),
                    System.out,
                    CommandLine.Help.Ansi.AUTO,
                    CommandLine.DefaultExceptionHandler(),
                    *args
            )
        }
    }
}