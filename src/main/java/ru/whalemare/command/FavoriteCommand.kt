package ru.whalemare.command

import com.taskadapter.redmineapi.bean.Issue
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import ru.whalemare.extension.println
import ru.whalemare.model.IssueDto
import ru.whalemare.repository.Repository

/**
 * @since 2017
 * @authorName Anton Vlasov - whalemare
 */
@Command(name = "favorite",
        description = arrayOf("Add issues to favorite list"))
class FavoriteCommand(val repository: Repository) : Runnable {

    @Option(names = arrayOf("-c", "--create"),
            description = arrayOf("Add selected issue to list of favorites (alias)"))
    var issueIdC: Int? = null

    @Option(names = arrayOf("-r", "--read"),
            arity = "0..1",
            description = arrayOf("Add selected issue to list of favorites (alias)"))
    var issueIdR: Int? = null

    @Option(names = arrayOf("-u", "--update"),
            description = arrayOf("Add selected issue to list of favorites (alias)"))
    var issueIdU: Int? = null

    @Option(names = arrayOf("-d", "--delete"),
            description = arrayOf("Add selected issue to list of favorites (alias)"))
    var issueIdD: Int? = null

    @Option(names = arrayOf("-a", "--all"),
            description = arrayOf("Show all available info"))
    var showAll: Boolean = false

    override fun run() {
        issueIdC?.let { create(repository.getIssue(it)) }

        read(issueIdR)

        issueIdU?.let { update(repository.getIssue(it)) }

        issueIdD?.let { delete(repository.getIssue(it)) }
    }

    fun create(issue: Issue) {
        val issues = mutableListOf(*repository.getFavoriteIssues().toTypedArray())
        issues.removeIf { it.id == issue.id }
        issues.add(IssueDto.from(issue))
        repository.setFavoriteIssue(issues.toList())
    }

    fun read(issueId: Int?) {
        if (issueId == null) {
            if (showAll) {
                val issues = repository.getFavoriteIssues()
                issues.println(showAll)
            } else {
                println("You must set issue id to --read option")

            }
        } else {
            val issue = repository.getFavoriteIssues().firstOrNull { it.id == issueId }
            if (issue == null) {
                println("Issue with id #$issueId not found in favorites list")
                // TODO: add availability for adding issue to favorite list
            } else {
                issue.println(showAll)
            }
        }
    }

    fun update(issue: Issue) {

    }

    fun delete(issue: Issue) {

    }
}
