package repository

import Setup
import com.taskadapter.redmineapi.Include
import com.taskadapter.redmineapi.Params
import com.taskadapter.redmineapi.RedmineManagerFactory
import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Project
import com.taskadapter.redmineapi.bean.User
import com.taskadapter.redmineapi.internal.ResultsWrapper

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class RepositoryRedmine: Repository {
    val redmine = RedmineManagerFactory.createWithApiKey(Setup.redmine, Setup.apiKey)!!

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
        return redmine.userManager.currentUser
    }


}