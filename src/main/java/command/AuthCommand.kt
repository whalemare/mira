package command

import model.Auth
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Database
import java.util.concurrent.Callable

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "auth", description = arrayOf("Authorize, before using MIRA"))
class AuthCommand: Callable<Auth> {

    @Option(names = arrayOf("-k", "--key"),
            description = arrayOf("https://redmine.magora.team/my/account", "Check \"API\" field"))
    var apiKey: String = ""

    @Option(names = arrayOf("-r", "--redmine"),
            description = arrayOf("Domain of your redmine. ex: redmine.magora.com"))
    var redmineEndpoint: String = ""

    override fun call(): Auth {
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

        return Auth().apply {
            this.redmine = redmineEndpoint
            this.key = apiKey
        }
//        return RepositoryRedmine(RedmineManagerFactory.createWithApiKey(
//                redmineEndpoint, apiKey
//        ))
    }
}