package repository

import Constant
import com.taskadapter.redmineapi.Include
import com.taskadapter.redmineapi.Params
import com.taskadapter.redmineapi.RedmineManagerFactory
import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Project
import com.taskadapter.redmineapi.bean.TimeEntryFactory
import com.taskadapter.redmineapi.bean.User
import com.taskadapter.redmineapi.internal.ResultsWrapper

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class RepositoryRedmine : Repository {

    val cache: MutableMap<String, Any> = mutableMapOf<String, Any>()

    companion object {
        val redmine = RedmineManagerFactory.createWithApiKey(Constant.redmine, Constant.apiKey)!!
    }

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

}