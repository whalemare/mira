package ru.whalemare.command

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

    override fun run() {
        // command has`t options
    }

    @Command(name = "create",
            description = arrayOf("Add issues to favorite list"))
    class Create(val repository: Repository) : Runnable {

        @Option(names = arrayOf("-i", "--id"),
                required = true,
                description = arrayOf("Select issue by id"))
        var issueId: Int? = null

        @Option(names = arrayOf("-as", "--alias"),
                description = arrayOf("Create new alias to selected issue"))
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
                issueDto.println(false)
            }
        }
    }

    @Command(name = "read",
            description = arrayOf("Read issues from favorite list"))
    class Read(val repository: Repository) : Runnable {

        @Option(names = arrayOf("-i", "--id"),
                arity = "0..1",
                description = arrayOf("Select issue by id"))
        var issueId: Int? = null

        @Option(names = arrayOf("-a", "--all"),
                description = arrayOf("Show all available info"))
        var showAll: Boolean = false

        override fun run() {
            if (issueId == null) {
                if (showAll) {
                    val issues = repository.getFavoriteIssues()
                    issues.println(false)
                } else {
                    println("You must set issue id to --read option")
                }
            } else {
                val issue = repository.getFavoriteIssues().firstOrNull { it.id == issueId }
                if (issue == null) {
                    println("Issue with id #$issueId not found in favorites list")
                    // TODO: add availability for adding issue to favorite list
                } else {
                    issue.println(false)
                }
            }
        }
    }

    @Command(name = "update",
            description = arrayOf("Update issues from favorite list"))
    class Update(val repository: Repository) : Runnable {
        @Option(names = arrayOf("-i", "--id"),
                description = arrayOf("Select issue by id"))
        var issueId: Int? = null

        @Option(names = arrayOf("-a", "--all"),
                description = arrayOf("Update all issues from list to actual redmine "))
        var all: Boolean = false

        override fun run() {
            TODO("Unsupported Operation")
        }
    }

    @Command(name = "delete",
            description = arrayOf("Delete issues from favorite list"))
    class Delete(val repository: Repository) : Runnable {
        @Option(names = arrayOf("-i", "--id"),
                arity = "1..*",
                split = ",",
                description = arrayOf("Select issue by id"))
        var issueIds: Array<Int> = emptyArray()

        override fun run() {
            if (issueIds.isEmpty()) {
                println("You must set issue id")
            } else {
                val favorites = repository.getFavoriteIssues()
                val delta = mutableListOf<IssueDto>()
                val filtered = favorites.filter {
                    val answer = !issueIds.contains(it.id)
                    if (!answer) delta.add(it)
                    answer
                }
                repository.setFavoriteIssue(filtered)

                println("Successfully removed: ")
                delta.println()
            }
        }
    }
}
