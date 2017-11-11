package command

import com.taskadapter.redmineapi.RedmineManagerFactory
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Database
import repository.Repository
import repository.RepositoryRedmine
import java.util.concurrent.Callable

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "auth", description = arrayOf("Authorize, after using MIRA"))
class AuthCommand: Callable<Repository> {

    @Option(names = arrayOf("-k", "--key"),
            description = arrayOf("https://redmine.magora.team/my/account", "Check \"API\" field"))
    var apiKey: String = ""

    @Option(names = arrayOf("-r", "--redmine"),
            description = arrayOf("Domain of your redmine. ex: redmine.magora.com"))
    var redmineEndpoint: String = ""

    override fun call(): Repository {
        val auth = Database.getInstance().getRedmine()
        apiKey = auth?.key ?: ""
        redmineEndpoint = auth?.redmine ?: ""

        if (redmineEndpoint.isBlank()) {
            while (redmineEndpoint.isBlank()) {
                print("Redmine >> "); redmineEndpoint = readLine()!!

                if (redmineEndpoint.isNotBlank()) {
                    if (!redmineEndpoint.startsWith("http://") || !redmineEndpoint.startsWith("https://")) {
                        redmineEndpoint = "https://$redmineEndpoint"
                    }
                }
            }
        }

        if (apiKey.isBlank()) {
            while (apiKey.isBlank()) {
                print("Apikey >> "); apiKey = readLine()!!
            }
        }

        Database.getInstance().putRedmine(redmineEndpoint, apiKey)
        return RepositoryRedmine(RedmineManagerFactory.createWithApiKey(
                redmineEndpoint, apiKey
        ))
    }

    fun autorize(): Repository {


        if (apiKey.isBlank() || redmineEndpoint.isBlank()) {
            println("Firstly, you need to authorize")
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