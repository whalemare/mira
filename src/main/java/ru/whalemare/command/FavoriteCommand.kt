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

    @Option(names = arrayOf("-d", "--delete"),
            description = arrayOf("Add selected issue to list of favorites (alias)"))
    var issueIdD: Int? = null

    override fun run() {
        issueIdD?.let { delete(repository.getIssue(it)) }
    }

    fun delete(issue: Issue) {

    }

    @Command(name = "create")
    class Create(val repository: Repository): Runnable {

        @Option(names = arrayOf("-c", "--create"),
                required = true,
                description = arrayOf("Create new alias to selected issue"))
        var issueId: Int? = null

        @Option(names = arrayOf("-as", "--alias"))
        var alias: String? = null

        override fun run() {
            if (issueId == null) {
                println("You must set issue id")
            } else {
                val issue = repository.getIssue(issueId!!)
                val favorites = mutableListOf(*repository.getFavoriteIssues().toTypedArray())
                favorites.removeIf { it.id == issue.id }
                val issueDto = IssueDto.from(issue, alias ?: "")
                favorites.add(issueDto)
                repository.setFavoriteIssue(favorites.toList())
                println("Successfully add issue:")
                issueDto.println()
            }
        }
    }

    @Command(name = "read",
            description = arrayOf("Read issues from favorite list"))
    class Read(val repository: Repository) : Runnable {

        @Option(names = arrayOf("-r", "--read"),
                arity = "0..1",
                description = arrayOf("Add selected issue to list of favorites (alias)"))
        var issueId: Int? = null

        @Option(names = arrayOf("-a", "--all"),
                description = arrayOf("Show all available info"))
        var showAll: Boolean = false

        override fun run() {
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
    }

    @Command(name = "update",
            description = arrayOf("Update issues from favorite list"))
    class Update(val repository: Repository) : Runnable {
        @Option(names = arrayOf("-u", "--update"),
                description = arrayOf("Add selected issue to list of favorites (alias)"))
        var issueId: Int? = null

        @Option(names = arrayOf("-a", "--all"),
                description = arrayOf("Update all issues from list to actual redmine "))
        var all: Boolean = false

        override fun run() {
            TODO("Unsupported Operation")
        }
    }
}
