package ru.whalemare.command

import picocli.CommandLine.Command
import picocli.CommandLine.Option
import ru.whalemare.extension.println
import ru.whalemare.repository.Repository

/**
 * @since 2017
 * @authorName Anton Vlasov - whalemare
 */
@Command(name = "commit",
        description = arrayOf("Commit your time: to issues, with favorite lists"))
class CommitCommand(val repository: Repository) : Runnable {

    @Option(names = arrayOf("-i", "--id", "-id"),
            description = arrayOf("Issue id of task for commit time"))
    var id: Int? = null

    @Option(names = arrayOf("-as", "--alias"),
            description = arrayOf("You can use alias from favorite lists, to determinate task"))
    var alias: String? = null

    // todo: need change to int
    @Option(names = arrayOf("-h", "--hours"),
            description = arrayOf("Set hours to time for commit"))
    var hours: String = "0"

    // todo: need change to int
    @Option(names = arrayOf("-m", "--minutes"),
            description = arrayOf("Set minutes to time for commit"))
    var minutes: String = "0"

    @Option(names = arrayOf(
            "-ms", "--message",
            "-c", "--comment"
    ), split = " ", arity = "0..*", description = arrayOf("Set message to time for commit"))
    var message: MutableList<String> = mutableListOf()

    @Option(names = arrayOf("-?", "--help"),
            description = arrayOf("Help"),
            usageHelp = true)
    val help = true

    override fun run() {
        val countHours = if (hours.isNotBlank()) { // todo: need change to int
            hours.toInt()
        } else {
            0
        }

        val countMinutes = if (minutes.isNotBlank()) { // todo: need change to int
            minutes.toInt()
        } else {
            0
        }

        if (id == null) {
            if (alias == null) {
                throw IllegalArgumentException("You must set id of issue or alias from favorite list")
            } else {
                val favorites = repository.getFavoriteIssues()
                val issue = favorites.firstOrNull { it.alias == alias }
                if (issue == null) {
                    println("Not found issue with alias = $alias. Favorites list: ")
                    favorites.println()
                } else {
                    commit(issue.id, countHours, countMinutes, message.joinToString(separator = " "))
                }
            }
        } else {
            commit(id!!, countHours, countMinutes, message.joinToString(separator = " "))
        }
    }

    protected fun commit(issueId: Int, hours: Int, minutes: Int, message: String) {
        repository.commitTime(issueId, hours, minutes, message)
    }
}