package command

import com.jakewharton.fliptables.FlipTableConverters
import com.taskadapter.redmineapi.bean.Project
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Repository
import java.util.concurrent.Callable

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "project",
        description = arrayOf("Manage your projects: show all, filterRest"))
class ProjectCommand(val repository: Repository) : Callable<Unit> {
    @Option(names = arrayOf("-h", "-?", "--help"),
            description = arrayOf("Help"),
            usageHelp = true)
    val help = true

    @Option(names = arrayOf("-a", "--all"),
            description = arrayOf("Print all available projects for your account"))
    var all: Boolean = false

    @Option(names = arrayOf("-f", "--filterRest"),
            description = arrayOf("Filter projects by id, name and link (short-name)"))
    var query: String = ""

    override fun call() {
        if (all) {
            val projects = getProjects()
            println("Your projects:")
            print(projects)
            return
        }

        if (query.isNotEmpty()) {
            val projects = filterBy(query)
            println("Filtered query = $query")
            print(projects)
            return
        }
    }

    private fun filterBy(query: String): List<Project> {
        return getProjects().filter {
            it.name.contains(query) || it.id.toString().contains(query) || it.identifier.contains(query)
        }
    }

    private fun print(projects: List<Project>) {
        val triples = projects.map {
            Triple(it.id.toString(), it.name.toString(), it.identifier.toString())
        }

        val headers = arrayOf("id", "name", "link")
        val data = Array(triples.size) {
            arrayOf(triples[it].first, triples[it].second, triples[it].third)
        }

        println(FlipTableConverters.fromObjects(headers, data))
    }

    private fun getProjects(): List<Project> {
        return repository.getProjects()
    }

}