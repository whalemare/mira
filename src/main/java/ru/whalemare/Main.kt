import com.taskadapter.redmineapi.RedmineManagerFactory
import com.taskadapter.redmineapi.bean.User
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import ru.whalemare.command.*
import ru.whalemare.model.Auth
import ru.whalemare.repository.Database
import ru.whalemare.repository.Repository
import ru.whalemare.repository.RepositoryRedmine
import java.util.*


/**
 * @since 2017
 * @authorName Anton Vlasov - whalemare
 */
@Command(name = "mira",
        sortOptions = false)
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
            val creds = login()
            Database.getInstance().putRedmine(creds.first.redmine!!, creds.first.key!!)
            sayHello(creds.second)

            parse(args)
            while (scanner.hasNext()) {
                parse(scanner.nextLine().split(" ").toTypedArray())
            }
            println()
        }

        @JvmStatic
        fun getCommandLine(): CommandLine {
            return CommandLine(Main())
                    .addSubcommand("auth", AuthCommand())
                    .addSubcommand("project", ProjectCommand(repository))
                    .addSubcommand("issue", IssueCommand(repository))
                    .addSubcommand("commit", CommitCommand(repository))
                    .addSubcommand("absent", AbsentCommand(repository))
                    .addSubcommand("start", StartCommand(repository))
                    .addSubcommand("favorite", FavoriteCommand(repository))
                    .setOverwrittenOptionsAllowed(true)
        }

        fun parse(args: Array<String>) {
            val commandLine = getCommandLine()
            commandLine.parseWithHandlers(
                    CommandLine.RunLast(),
                    System.out,
                    CommandLine.Help.Ansi.AUTO,
                    CommandLine.DefaultExceptionHandler(),
                    *args
            )
        }

        private fun login(): Pair<Auth, User> {
            val auth = AuthCommand().call()
            repository = RepositoryRedmine(RedmineManagerFactory.createWithApiKey(
                    auth.redmine, auth.key
            ))
            try {
                return auth to repository.getMe()
            } catch (e: Exception) {
                Database.getInstance().putRedmine("", "")
                throw e
            }
        }

        private fun sayHello(me: User) {
            println("Hello, ${me.firstName}")
        }
    }
}