package command

import com.jakewharton.fliptables.FlipTable
import com.taskadapter.redmineapi.bean.Issue
import picocli.CommandLine
import picocli.CommandLine.Option
import repository.Repository

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@CommandLine.Command(
        name = "issue",
        description = arrayOf("Manage your issues: commit time, see assigned issues, etc."),
        footer = arrayOf("mira (c) 2017"))
class IssueCommand(val repository: Repository) : Runnable {

    @Option(
            names = arrayOf("-i", "--id", "-id"),
            description = arrayOf("Index of search issue")
    )
    var id: Int = Int.MIN_VALUE

    @Option(names = arrayOf("-a", "--all"), description = arrayOf("Show all available info"))
    var showAll: Boolean = false

    override fun run() {
        if (id > Int.MIN_VALUE) {
            val issue = repository.getIssue(id)
            print(issue)
            return
        }
    }

    fun print(issue: Issue) {
        if (showAll) {
            val headers = arrayOf("id", "project", "assigned", "status", "spent")
            val content = Array(1) {
                return@Array arrayOf(issue.id.toString(),
                        issue.projectName ?: "?",
                        issue.assigneeName ?: "?",
                        issue.statusName ?: "?",
                        issue.spentHours.toString()
                )
            }
            println(FlipTable.of(headers, content))
        } else {
            val headers = arrayOf("id", "project", "assigned", "status", "spent")
            val content = Array(1) {
                return@Array arrayOf(issue.id.toString(),
                        issue.projectName ?: "?",
                        issue.assigneeName ?: "?",
                        issue.statusName ?: "?",
                        issue.spentHours.toString()
                )
            }
            println(FlipTable.of(headers, content))
        }

    }
}