package command

import com.taskadapter.redmineapi.RedmineManagerFactory
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Database
import repository.Repository
import repository.RepositoryRedmine
import java.util.concurrent.Callable
import javax.mail.AuthenticationFailedException

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
        if (apiKey.isBlank()) {
            throw AuthenticationFailedException("You must provide not empty api key")
        }

        if (redmineEndpoint.isBlank()) {
            throw AuthenticationFailedException("You must provide not empty redmine endpoint")
        }

        Database.getInstance().putRedmine(redmineEndpoint, apiKey)
        return RepositoryRedmine(RedmineManagerFactory.createWithApiKey(
                redmineEndpoint, apiKey
        ))
    }
}