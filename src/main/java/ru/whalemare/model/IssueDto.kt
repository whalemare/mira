package ru.whalemare.model

import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Tracker
import java.util.*

/**
 * @since 2017
 * @authorName Anton Vlasov - whalemare
 */
data class IssueDto(
        val id: Int = Integer.MIN_VALUE,
        val projectId: Int = Integer.MIN_VALUE,
        val projectName: String = "",
        val tracker: Tracker? = null,
        val subject: String? = null,
        val startDate: Date? = null,
        val createdDate: Date? = null,
        val updatedDate: Date? = null,
        val spentHours: Float? = null,
        val estimatedHours: Float? = null,
        val assignedId: Int? = null,
        val assignedName: String? = null,
        val authorId: Int? = null,
        val authorName: String? = null,
        val alias: String = ""
) {
    companion object {
        fun from(issue: Issue, alias: String = ""): IssueDto {
            return IssueDto(
                    id = issue.id,
                    projectId = issue.projectId,
                    projectName = issue.projectName,
                    tracker = issue.tracker,
                    subject = issue.subject,
                    startDate = issue.startDate,
                    createdDate = issue.createdOn,
                    updatedDate = issue.updatedOn,
                    spentHours = issue.spentHours,
                    estimatedHours = issue.estimatedHours,
                    assignedId = issue.assigneeId,
                    assignedName = issue.assigneeName,
                    authorName = issue.authorName,
                    authorId = issue.authorId,
                    alias = alias
            )
        }
    }

}