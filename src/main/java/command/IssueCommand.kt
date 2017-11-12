package command

import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.internal.ResultsWrapper
import extension.println
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Repository

/**
 * @rest http://www.redmine.org/projects/redmine/wiki/Rest_Issues
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "issue",
        description = arrayOf("Manage your issues: see assigned issues, see your issues, filterRest."))
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

    @Option(names = arrayOf("-me", "--me"),
            description = arrayOf("Filter issues by assigned to me"))
    var assignedMe: Boolean = false

    @Option(names = arrayOf("-f", "--filter"),
            description = arrayOf("Filter issues by project name", "\n"))
    var projectName: String = ""

    @Option(names = arrayOf("-fr", "--filter-rest"),
            description = arrayOf("Filter issues with specific REST params",
                    "See all available commands on http://www.redmine.org/projects/redmine/wiki/Rest_Issues"))
    var filterRest: MutableMap<String, String> = mutableMapOf()

    @Option(names = arrayOf("-h", "--help"),
            description = arrayOf("Help"),
            usageHelp = true)
    val help = true

    override fun run() {
        if (id > Int.MIN_VALUE) {
            val issue = repository.getIssue(id)
            if (print) issue.println(showAll)
            return
        }

        if (assignedMe) {
            val me = repository.getMe()
            filterRest.put("assigned_to_id", me.id.toString())
        }

        if (projectName.isNotBlank()) {
            val projectId = getProjectId(projectName)
            if (projectId != null) {
                filterRest.put("project_id", projectId)
            } else {
                System.err.println("Can`t filter by project name, show all")
            }
        }

        val issues = getFilteredIssues()
        if (print) {
            println("Show: ${issues.resultsNumber}, Found: ${issues.totalFoundOnServer}")
            issues.results.println(showAll)
        }
    }

    private fun getProjectId(projectName: String): String? {
        val projects = repository.getProjects()
        return projects.firstOrNull { it.name.contains(projectName, true) }?.id?.toString()
    }

    private fun getFilteredIssues(): ResultsWrapper<Issue> {
        return repository.getIssues(filterRest)
    }
}