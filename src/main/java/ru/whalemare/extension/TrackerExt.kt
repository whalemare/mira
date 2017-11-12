package ru.whalemare.extension

import com.taskadapter.redmineapi.bean.Tracker
import com.taskadapter.redmineapi.bean.TrackerFactory

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
object TrackerExt {
    val TASK = TrackerFactory.create(3, "Task")
    val BUG = TrackerFactory.create(1, "Bug")
    val INVESTIGATION_TASK = TrackerFactory.create(12, "Investigation task")
    val ESTIMATION_REQUEST = TrackerFactory.create(8, "Estimation request")
    val FEATURE = TrackerFactory.create(2, "Feature")
    val CHANGE = TrackerFactory.create(5, "Change request")
    val QUESTION = TrackerFactory.create(4, "Question")
    val IDEA = TrackerFactory.create(6, "Idea")
    val EPIC = TrackerFactory.create(7, "Epic")
    val SYSADMIN = TrackerFactory.create(9, "Sysadmin request")
    val ESTIMATION = TrackerFactory.create(10, "Estimation task")
    val INVESTIGATION_REQUEST = TrackerFactory.create(11, "Investigation request")
    val PROJECT = TrackerFactory.create(13, "Project portfolio")
    val ACTIVITY = TrackerFactory.create(14, "Activity Request")
}

/**
 * Returned abbreviation of tracker name for create branch name
 * <br>
 * TrackerExt.TASK.small() = "task"
 * TrackerExt.TASK.small() = "task"
 */
fun Tracker.small(): String {
    val converted = name.toLowerCase()
    val spacePosition = converted.indexOfFirst { it == ' ' }
    if (spacePosition >= 0) {
        return converted.substring(0, spacePosition)
    }
    return converted
}

/**
 * @return <b>true</b> if this tracker issue canonically support commit time
 */
fun Tracker.isCanCommitTime(): Boolean {
    return id == TrackerExt.TASK.id
            || id == TrackerExt.BUG.id
}