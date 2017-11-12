package command

import model.Absent
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import repository.Database
import usecase.SendEmailInteractor
import javax.mail.PasswordAuthentication


/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@Command(name = "absent",
        description = arrayOf("Notify all about your absent today"),
        showDefaultValues = true)
class AbsentCommand(val repository: repository.Repository) : Runnable {

    @Option(names = arrayOf("-t", "--time"),
            description = arrayOf("The time at which you will be on the job"),
            required = true)
    val time: String = ""

    @Option(names = arrayOf("-r", "--reason"),
            description = arrayOf("Reason, why you can not work"))
    val reason: String = "учёба"

    @Option(names = arrayOf("-s", "--sources"),
            description = arrayOf("How can members contact with you?"))
    val sources: String = "skype, почта, телефон"

    @Option(names = arrayOf("-c", "--clear"),
            description = arrayOf("Clear you current config for absent: email from, email to, password"))
    val clear: Boolean = false

    @Option(names = arrayOf("-h", "-?", "--help"),
            description = arrayOf("Help"),
            usageHelp = true)
    val help = true

    override fun run() {
        var absent = if (clear) {
            Absent()
        } else {
            repository.getAbsentCreds()
        }
        if (absent == null) absent = Absent()

        while (absent.email.isNullOrBlank()) {
            print("From >> "); absent.email = readLine()
        }

        while (absent.recipient.isNullOrBlank()) {
            print("To >> "); absent.recipient = readLine()
        }

        while (absent.password.isNullOrBlank()) {
            print("Pass >> "); val pass = readPassword()
            absent.password = String(pass)
        }

        val me = repository.getMe()

        val message = Message(
                from = absent.email!!,
                to = absent.recipient!!,
                subject = "${me.lastName}. Опоздание",
                message = "Причина отсутствия: $reason\n" +
                        "Какое время буду отсутствовать: буду на работе после $time\n" +
                        "Каналы связи с вами: $sources"
        )

        println("You really want send message (y/n):")
        message.println()
        val answer = readLine()
        if (answer == "y" || answer == "yes") {
            Database.getInstance().putAbsentCreds(absent)
            SendEmailInteractor(PasswordAuthentication(absent.email, absent.password), message).run()
        } else {
            println("Message not sended")
        }
    }

    private fun readPassword(): CharArray {
        return if (System.console() != null) {
            System.console().readPassword()
        } else {
            readLine()?.toCharArray() ?: charArrayOf()
        }
    }

    data class Message(
            val from: String,
            val to: String,
            val subject: String,
            val message: String
    )

    private fun AbsentCommand.Message.println() {
        println("From: $from")
        println("To: $to")
        println("Subject: $subject")
        println("Message: $message")
    }
}
