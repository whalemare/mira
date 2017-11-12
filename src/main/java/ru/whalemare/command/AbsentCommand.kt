package ru.whalemare.command

import picocli.CommandLine
import picocli.CommandLine.Option
import ru.whalemare.model.Absent
import ru.whalemare.model.Message
import ru.whalemare.repository.Repository
import ru.whalemare.usecase.MessageInteractor
import ru.whalemare.usecase.SendEmailInteractor

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
@CommandLine.Command(name = "absent",
        description = arrayOf("Notify all about your absent today"),
        showDefaultValues = true)
class AbsentCommand(val repository: Repository) : Runnable {

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
        val message: Message = askMessage()
        val absent: Absent = askAbsent()

        val parsedMessage: Message = MessageInteractor(message).call()
        val approveSend: Boolean = askSendMessage(parsedMessage)

        if (approveSend) {
            saveData(message, absent)
            sendMessage(absent, parsedMessage)
        } else {
            println("Message not sended. All input data not saved.")
        }
    }

    protected fun saveData(message: Message, absent: Absent) {
        val credsPath = repository.putAbsentCreds(absent)
        val messagePath = repository.putAbsentMessage(message)
        println("I saved your credentials into $credsPath")
        println("I saved your email template into $messagePath")
    }

    protected fun askSendMessage(parsedMessage: Message): Boolean {
        println("You really want send message (y/n):")
        parsedMessage.println()
        val answer = readLine()
        return answer == "y" || answer == "yes"
    }

    protected fun askMessage(): Message {
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

        return message
    }

    protected fun askAbsent(): Absent {
        val absent = repository.getAbsentCreds()

        if (absent.email.isNullOrBlank() || absent.password.isNullOrBlank() || absent.email.isNullOrBlank()) {
            println("I not have credentials for your email, please enter: ")
        }

        while (absent.email.isNullOrBlank()) {
            print("From >> "); absent.email = readLine()
        }

        while (absent.recipient.isNullOrBlank()) {
            print("To >> "); absent.recipient = readLine()
        }

        while (absent.password.isNullOrBlank()) {
            print("Pass >> ");
            val pass = readPassword()
            absent.password = String(pass)
        }

        return absent
    }

    protected fun readPassword(): CharArray {
        return if (System.console() != null) {
            System.console().readPassword()
        } else {
            readLine()?.toCharArray() ?: charArrayOf()
        }
    }

    protected fun sendMessage(absent: Absent, message: Message) {
        SendEmailInteractor(absent, message).run()
    }

    private fun Message.println() {
        println("Subject: $subject")
        println("Message:")
        message.forEach {
            println(it)
        }
    }

}