package ru.whalemare.repository

import com.taskadapter.redmineapi.Params
import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Project
import com.taskadapter.redmineapi.bean.User
import com.taskadapter.redmineapi.internal.ResultsWrapper
import ru.whalemare.model.Absent
import ru.whalemare.model.Message

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

    fun getAbsentCreds(): Absent

    fun getAbsentMessage(): Message

    fun putAbsentCreds(absent: Absent): String

    fun putAbsentMessage(message: Message): String

    fun putFavoriteIssue(issue: Issue)
}