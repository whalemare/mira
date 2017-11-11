package command

import com.jakewharton.fliptables.FlipTable
import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.internal.ResultsWrapper
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Repository

/**
 * @rest http://www.redmine.org/projects/redmine/wiki/Rest_Issues
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "issue",
        description = arrayOf("Manage your issues: see assigned issues, see your issues, filter."))
class IssueCommand(val repository: Repository) : Runnable {

    @Option(names = arrayOf("-i", "--id", "-id"),
            description = arrayOf("Index of search issue. It`s terminate operation"))
    var id: Int = Int.MIN_VALUE

    @Option(names = arrayOf("-p", "--print"),
            description = arrayOf("Print issue data. By default = true"))
    var print: Boolean = true

    @Option(names = arrayOf("-a", "--all"),
            description = arrayOf("Show all available info"))
    var showAll: Boolean = false

    @Option(names = arrayOf("-m", "--me"),
            description = arrayOf("Filter issues by assigned to me"))
    var assignedMe: Boolean = false

    @Option(names = arrayOf("-f", "--filter"),
            description = arrayOf("Filter issues with specific params",
                    "See all available commands on http://www.redmine.org/projects/redmine/wiki/Rest_Issues"))
    var filter: MutableMap<String, String> = mutableMapOf()

    @Option(names = arrayOf("-s", "--start"),
            description = arrayOf("Create and checkout branch, for issue defined by -i=##### operation"))
    var start: Boolean = false

    override fun run() {
        if (id > Int.MIN_VALUE) {
            val issue = repository.getIssue(id)
            if (print) print(issue)
            return
        }

        if (assignedMe) {
            val me = repository.getMe()
            filter.put("assigned_to_id", me.id.toString())
        }

        val issues = getFilteredIssues()
        if (print) {
            println("Show: ${issues.resultsNumber}, Found: ${issues.totalFoundOnServer}")
            print(issues.results)
        }
    }

    private fun getFilteredIssues(): ResultsWrapper<Issue> {
        return repository.getIssues(filter)
    }

    fun print(issue: Issue?) {
        if (issue == null) {
            println("Issue #$id not found")
            return
        }

        print(listOf(issue))
    }

    fun print(list: List<Issue>) {
        if (list.isEmpty()) {
            println("List of issue is empty")
            return
        }

        val headers = if (showAll) {
            arrayOf("id", "subject", "author", "assigned", "status", "spent", "estimated", "created", "closed")
        } else {
            arrayOf("id", "subject", "assigned", "status", "spent")
        }

        val content = if (showAll) {
            Array(list.size) {
                val issue = list[it]
                return@Array arrayOf(
                        issue.id.toString(),
                        issue.subject ?: "?",
                        issue.authorName,
                        issue.assigneeName ?: "?",
                        issue.statusName ?: "?",
                        issue.spentHours?.toString() ?: "?",
                        issue.estimatedHours?.toString() ?: "?",
                        issue.createdOn?.toString() ?: "?",
                        issue.closedOn?.toString() ?: "?"
                )
            }
        } else {
            Array(list.size) {
                val issue = list[it]
                return@Array arrayOf(
                        issue.id.toString(),
                        issue.subject ?: "?",
                        issue.assigneeName ?: "?",
                        issue.statusName ?: "?",
                        issue.spentHours?.toString() ?: "?"
                )
            }
        }

        println(FlipTable.of(headers, content))
    }
}