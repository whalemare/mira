package repository

import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Project

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
interface Repository {

    fun getProjects(): List<Project>

    fun getIssue(id: Int): Issue

}