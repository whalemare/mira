package ru.whalemare.usecase

import ru.whalemare.model.Message
import java.util.concurrent.Callable

/**
 * Apply parameters from Message#params to Message#message
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class MessageInteractor(val message: Message) : Callable<Message> {

    override fun call(): Message {
        var subject = ""
        val parsedMessageLines = mutableListOf<String>()
        message.params.forEach { (key, value) ->
            subject = subject.replace("\$$key", value)
            message.message.forEach { line ->
                parsedMessageLines.add(line.replace("\$$key", value))
            }
        }


        return Message(subject, parsedMessageLines, emptyMap())
    }


}