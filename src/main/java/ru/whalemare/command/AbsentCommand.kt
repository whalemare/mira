package ru.whalemare.command

import picocli.CommandLine
import picocli.CommandLine.Option
import ru.whalemare.model.Message
import ru.whalemare.repository.Repository
import ru.whalemare.usecase.MessageInteractor

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@CommandLine.Command(name = "absent",
        description = arrayOf("Notify all about your absent today"),
        showDefaultValues = true)
class AbsentCommand(val repository: Repository): Runnable {

    @Option(names = arrayOf("-?", "--help"),
            description = arrayOf("Help"),
            usageHelp = true)
    val help = true

    @Option(names = arrayOf("-t", "--time"),
            description = arrayOf("The time at which you will be on the job"),
            required = true)
    val time: String = ""

    @Option(names = arrayOf("-ln", "--ln", "--lastname"), split = " ",
            description = arrayOf("The time at which you will be on the job"))
    val lastname: String = ""

    @Option(names = arrayOf("-r", "--reason"),
            description = arrayOf("Reason, why you can not work"))
    val reason: String = ""

//    @Option(names = arrayOf("-p", "--params"),
//            description = arrayOf("The option to generate text, that your teammates will be able to contact you.",
//                    "For example usage: -p telegram=whalemare",
//                    "\t will be converted to: telegram: whalemare",
//                    ""))
//    val sources: Map<String, String> = mapOf()

    override fun run() {
        val message = repository.getAbsentMessage()

        if (lastname.isNotBlank()) {
            message.params["lastname"] = lastname
        }

        if (reason.isNotBlank()) {
            message.params["reason"] = reason
        }

        if (time.isNotBlank()) {
            message.params["time"] = time
        }

        val parsedMessage = MessageInteractor(message).call()
        sendMessage(parsedMessage)
    }

    private fun sendMessage(message: Message) {
        println("You really want send message (y/n):")
        message.println()
        val answer = readLine()
        if (answer == "y" || answer == "yes") {
//            Database.getInstance().putAbsentCreds(absent)
            println("sended and saved")
//            SendEmailInteractor(PasswordAuthentication(absent.email, absent.password), message)/*.run()*/
        } else {
            println("Message not sended")
        }
    }

    private fun Message.println() {
        println("Subject: $subject")
        println("Message:")
        message.forEach {
            println(it)
        }
    }

}