package command

import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Repository

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "commit",
        description = arrayOf("Commit your time. To issues, with favorite lists"))
class CommitCommand(val repository: Repository) : Runnable {

    @Option(names = arrayOf("-i", "--id", "-id"),
            description = arrayOf("Issue id of task for commit time"),
            required = true)
    var id: Int = Integer.MIN_VALUE

    @Option(names = arrayOf("-h", "--hours"),
            description = arrayOf("Set hours to time for commit"))
    var hours: String = ""

    @Option(names = arrayOf("-m", "--minutes"),
            description = arrayOf("Set minutes to time for commit"))
    var minutes: String = ""

    override fun run() {
        val countHours = if (hours.isNotBlank()) {
            hours.toInt()
        } else {
            0
        }

        val countMinutes = if (minutes.isNotBlank()) {
            minutes.toInt()
        } else {
            0
        }

        commit(id, countHours, countMinutes)
    }

    protected fun commit(issueId: Int, hours: Int, minutes: Int) {
        repository.commitTime(issueId, hours, minutes)
    }
}