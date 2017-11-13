package ru.whalemare.extension

import com.jakewharton.fliptables.FlipTable
import ru.whalemare.model.IssueDto

/**
 * @since 2017
 * @authorName Anton Vlasov - whalemare
 */
fun IssueDto?.println(showAll: Boolean = true) {
    if (this == null) {
        println("Issue not found")
        return
    }

    listOf(this).println(showAll)
}

fun List<IssueDto>?.println(showAll: Boolean = true) {
    if (this == null || isEmpty()) {
        println("List of issue is empty")
        return
    }

    val headers = if (showAll) {
        arrayOf("id", "alias", "subject", "authorName", "assigned", "status", "spent", "estimated", "created")
    } else {
        arrayOf("id", "alias", "subject", "assigned", "status", "spent")
    }

    val content = if (showAll) {
        Array(size) {
            val issue = this[it]
            return@Array arrayOf(
                    issue.id.toString(),
                    issue.alias,
                    issue.subject,
                    issue.authorName,
                    issue.assignedName,
                    issue.tracker?.name ?: "?",
                    issue.spentHours.toString(),
                    issue.estimatedHours.toString(),
                    issue.createdDate?.toString() ?: "?"
            )
        }
    } else {
        Array(size) {
            val issue = this[it]
            return@Array arrayOf(
                    issue.id.toString(),
                    issue.alias,
                    issue.subject,
                    issue.assignedName,
                    issue.tracker?.name ?: "?",
                    issue.spentHours.toString()
            )
        }
    }

    println(FlipTable.of(headers, content))

}