package ru.whalemare.extension

import com.jakewharton.fliptables.FlipTable
import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.IssueStatus
import com.taskadapter.redmineapi.bean.IssueStatusFactory
import ru.whalemare.extension.IssueExt.IN_PROGRESS

/**
 * @since 2017
 * @authorName Anton Vlasov - whalemare
 */

object IssueExt {
    val NEW = IssueStatusFactory.create(1, "New")!!
    val IN_PROGRESS = IssueStatusFactory.create(2, "In Progress")!!
    val IN_REVIEW = IssueStatusFactory.create(18, "In review")!!
    val RESOLVED = IssueStatusFactory.create(3, "Resolved")!!
    val READY_TO_TEST = IssueStatusFactory.create(11, "Ready to test")!!
    val IN_QA = IssueStatusFactory.create(12, "In QA")!!
    val REOPENED = IssueStatusFactory.create(13, "Reopened")!!
    val CLOSED = IssueStatusFactory.create(5, "Closed")!!
    val DUPLICATE = IssueStatusFactory.create(8, "Duplicate")!!
    val REJECTED = IssueStatusFactory.create(6, "Rejected")!!
    val NEED_FEEDBACK = IssueStatusFactory.create(7, "Need feedback")!!
    val TESTING = IssueStatusFactory.create(4, "Testing")!!
    val CANT_REPRODUCE = IssueStatusFactory.create(10, "Can't reproduce")!!
    val ASSIGN = IssueStatusFactory.create(9, "Assign")!!
    val ESTIMATED = IssueStatusFactory.create(14, "Estimated")!!
    val SENT_TO_CUSTOMER = IssueStatusFactory.create(15, "Sent to Customer")!!
    val LOST = IssueStatusFactory.create(16, "Lost")!!
    val WON = IssueStatusFactory.create(17, "Won")!!
    val INVESTIGATED = IssueStatusFactory.create(19, "Investigated")!!
    val ACCEPTED = IssueStatusFactory.create(20, "Accepted")!!
    val ACCEPTANCE = IssueStatusFactory.create(21, "Acceptance")!!
    val WARRANTY = IssueStatusFactory.create(22, "Warranty")!!
    val SUPPORT = IssueStatusFactory.create(24, "Support")!!
    val FINISHED = IssueStatusFactory.create(23, "Finished")!!
    val PAUSED = IssueStatusFactory.create(25, "Paused")!!
}

/**
 * See IssueExt class. Be sure, that this status supported by your Redmine
 * @sample IssueExt.IN_PROGRESS
 * @sample IssueExt.NEW
 * @sample IssueExt.REJECTED
 * @sample IssueExt.PAUSED
 */
fun Issue.setStatus(status: IssueStatus) {
    statusId = status.id
    statusName = status.name
}

fun Issue.setProgress() {
    setStatus(IN_PROGRESS)
}

fun Issue.isProgress(): Boolean {
    return statusId == 2 || statusName == "In setProgress"
}

fun Issue?.println(showAll: Boolean = true) {
    if (this == null) {
        println("Issue not found")
        return
    }

    listOf(this).println(showAll)
}

fun List<Issue>?.println(showAll: Boolean = true) {
    if (this == null || isEmpty()) {
        println("List of issue is empty")
        return
    }

    val headers = if (showAll) {
        arrayOf("id", "subject", "authorName", "assigned", "status", "spent", "estimated", "created", "closed")
    } else {
        arrayOf("id", "subject", "assigned", "status", "spent")
    }

    val content = if (showAll) {
        Array(size) {
            val issue = this[it]
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
        Array(size) {
            val issue = this[it]
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