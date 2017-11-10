package command

import AbsentEmail
import picocli.CommandLine.Command
import picocli.CommandLine.Option

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "absent",
        description = arrayOf("Notify all about your absent today"))
class AbsentCommand(val repository: repository.Repository) : Runnable {

    @Option(names = arrayOf("-t", "--time"),
            description = arrayOf("The time at which you will be on the job"),
            required = true)
    val timeShowUp: String = ""

    override fun run() {
        val me = repository.getMe()
        AbsentEmail(me, timeShowUp).run()
    }

}