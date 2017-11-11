
import command.*
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Database
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
            parse(args)
            while (scanner.hasNext()) {
                parse(scanner.nextLine().split(" ").toTypedArray())
            }
            println()
        }

        fun parse(args: Array<String>) {
            repository = autorize()

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

        fun autorize(): Repository {
            val auth = Database.getInstance().getRedmine()
            var redmineEndpoint = auth?.redmine ?: ""
            var apiKey = auth?.key ?: ""

            if (apiKey.isBlank() || redmineEndpoint.isBlank()) {
                println("Firstly, you need to authorize")
                print("Redmine >> "); redmineEndpoint = readLine()!!
                print("Apikey >> "); apiKey = readLine()!!
            }

            return if (redmineEndpoint.isNotBlank() && apiKey.isNotBlank()) {
                val command = AuthCommand().apply {
                    this.redmineEndpoint = redmineEndpoint
                    this.apiKey = apiKey
                }
                return command.call()
            } else {
                if (redmineEndpoint.isBlank()) {
                    throw IllegalArgumentException("You must provide redmine endpoint")
                } else {
                    throw IllegalArgumentException("You must provide redmine api key")
                }
            }
        }
    }
}