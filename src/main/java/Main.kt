import command.*
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Repository
import repository.RepositoryRedmine
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger


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
        val repository: Repository = RepositoryRedmine()

        val scanner = Scanner(System.`in`)

        @JvmStatic
        fun main(args: Array<String>) {
            parse(args)
            while (scanner.hasNext()) {
                parse(scanner.nextLine().split(" ").toTypedArray())
            }
            println()
        }

        fun parse(args: Array<String>) {
            Logger.getGlobal().level = Level.OFF
//            println("for parse: ${Arrays.toString(args)}")
            val commandLine = CommandLine(Main())
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