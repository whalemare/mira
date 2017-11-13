package ru.whalemare.command

import com.taskadapter.redmineapi.bean.Issue
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import ru.whalemare.repository.Repository
import ru.whalemare.extension.*
import ru.whalemare.usecase.InteractorBash

/**
 * @since 2017
 * @authorName Anton Vlasov - whalemare
 */
@Command(name = "start",
        description = arrayOf("Start your issues with this command. Automatically change status of issue, create branch and etc."),
        showDefaultValues = true)
class StartCommand(val repository: Repository) : Runnable {

    @Option(names = arrayOf("-i", "-id", "--id"),
            description = arrayOf("Id of issue, that will be started"),
            required = true)
    val id: Int = Int.MIN_VALUE

    @Option(names = arrayOf("-m", "-me", "--me"),
            description = arrayOf("Assign issue to you, if it`s already not assigned"))
    val assignedToMe: Boolean = true

    @Option(names = arrayOf("-c", "--create"),
            description = arrayOf("Create and checkout to issue branch or not"))
    val needBranch: Boolean = true

    @Option(names = arrayOf("-p", "--postfix"),
            description = arrayOf("Add postfix to your branch name. ex: feature/#12345-postfix"))
    val postfix: String = ""

    @Option(names = arrayOf("-h", "-?", "--help"),
            description = arrayOf("Help"),
            usageHelp = true)
    val help = true

    override fun run() {
        val issue = repository.getIssue(id)
        val hashIssue = issue.hashCode()
        println("Start issue")
        issue.println()

        if (issue.isProgress()) {
            println("Issue $id is currently In Progress")
        } else {
            issue.setStatus(IssueExt.IN_PROGRESS)
        }

        if (assignedToMe) {
            val me = repository.getMe()
            if (issue.assigneeId == me.id) {
                println("Issue $id is currently In Progress")
            }
        }

        if (needBranch) {
            val branchName = makeBranchName(issue)
            val command = "git checkout -b $branchName"
            InteractorBash(command).run()
        }

        if (hashIssue != issue.hashCode()) repository.updateIssue(issue)
        println("Issue ${issue.id} successfully started")
    }

    private fun makeBranchName(issue: Issue): String {
        val tracker = issue.tracker.small()
        val postfix = if (postfix.isNotBlank()) {
            postfix.replace(" ", "-", true)
        } else {
            ""
        }

        return if (postfix.isNotBlank()) {
            "$tracker/#${issue.id}-$postfix"
        } else {
            "$tracker/#${issue.id}"
        }
    }
}