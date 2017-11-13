package ru.whalemare.model

import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Tracker
import java.util.*

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
data class IssueDto(
        val id: Int = Integer.MIN_VALUE,
        val projectId: Int = Integer.MIN_VALUE,
        val projectName: String = "",
        val tracker: Tracker? = null,
        val subject: String = "",
        val startDate: Date? = null,
        val createdDate: Date? = null,
        val updatedDate: Date? = null,
        val spentHours: Float = Float.MIN_VALUE,
        val estimatedHours: Float = Float.MIN_VALUE,
        val assignedId: Int = Integer.MIN_VALUE,
        val assignedName: String = ""
) {
    companion object {
        fun from(issue: Issue): IssueDto {
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
                    assignedName = issue.assigneeName
            )
        }
    }

}