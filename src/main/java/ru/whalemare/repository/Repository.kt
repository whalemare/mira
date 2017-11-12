package repository

import com.taskadapter.redmineapi.Params
import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Project
import com.taskadapter.redmineapi.bean.User
import com.taskadapter.redmineapi.internal.ResultsWrapper
import model.Absent

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
interface Repository {

    fun getProjects(): List<Project>

    fun getIssue(id: Int): Issue

    fun getIssues(params: Params): ResultsWrapper<Issue>

    fun getMe(): User

    fun getIssues(map: Map<String, String>): ResultsWrapper<Issue>

    fun commitTime(issueId: Int, hours: Int, minutes: Int)

    fun updateIssue(issue: Issue)

    fun getAbsentCreds(): Absent?
}