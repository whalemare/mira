package ru.whalemare.repository

import com.taskadapter.redmineapi.Include
import com.taskadapter.redmineapi.Params
import com.taskadapter.redmineapi.RedmineManager
import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Project
import com.taskadapter.redmineapi.bean.TimeEntryFactory
import com.taskadapter.redmineapi.bean.User
import com.taskadapter.redmineapi.internal.ResultsWrapper
import ru.whalemare.model.Absent
import ru.whalemare.model.IssueDto
import ru.whalemare.model.Message

/**
 * @since 2017
 * @authorName Anton Vlasov - whalemare
 */
class RepositoryRedmine(private val redmine: RedmineManager) : Repository {

    val cache: MutableMap<String, Any> = mutableMapOf()

    override fun updateIssue(issue: Issue) {
        redmine.issueManager.update(issue)
    }

    override fun getProjects(): List<Project> {
        return redmine.projectManager.projects
    }

    override fun getIssue(id: Int): Issue {
        return redmine.issueManager.getIssueById(id, Include.children)
    }

    override fun getIssues(params: Params): ResultsWrapper<Issue> {
        return redmine.issueManager.getIssues(params)
    }

    override fun getIssues(map: Map<String, String>): ResultsWrapper<Issue> {
        return redmine.issueManager.getIssues(map)
    }


    override fun getMe(): User {
        val key = "ME"
        if (cache[key] == null) {
            cache[key] = redmine.userManager.currentUser
        }

        return cache[key] as User
    }

    override fun commitTime(issueId: Int, hours: Int, minutes: Int) {
        val entry = TimeEntryFactory.create(issueId).apply {
            this.issueId = issueId
            this.hours = (hours.toFloat() + (minutes.toFloat() / 60.toFloat()))
        }

        redmine.timeEntryManager.createTimeEntry(entry)
    }

    override fun getAbsentCreds(): Absent {
        var absent = Database.getInstance().getAbsentCreds()
        if (absent == null) {
            absent = Absent()
        }
        return absent
    }

    override fun getAbsentMessage(): Message {
        var message = Database.getInstance().getAbsentMessage()
        if (message == null) {
            message = Message()
        }
        return message
    }

    override fun putAbsentCreds(absent: Absent): String {
        return Database.getInstance().putAbsentCreds(absent)
    }

    override fun putAbsentMessage(message: Message): String {
        return Database.getInstance().putAbsentMessage(message)
    }

    override fun putFavoriteIssue(issue: Issue) {
        Database.getInstance().putFavoriteIssue(issue)
    }

    override fun setFavoriteIssue(issues: List<IssueDto>) {
        Database.getInstance().setFavoriteIssue(issues)
    }

    override fun getFavoriteIssues(): List<IssueDto> {
        return Database.getInstance().getFavoriteIssues()
    }
}