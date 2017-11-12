package ru.whalemare.usecase

import ru.whalemare.model.Message
import java.util.concurrent.Callable

/**
 * Apply parameters from Message#params to Message#message
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class MessageInteractor(private val message: Message) : Callable<Message> {

    override fun call(): Message {
        var subject = message.subject
        val parsedMessageLines = mutableListOf<String>()

        message.message.forEach { line ->
            var parsed = line
            message.params.forEach { key, value ->
                parsed = parsed.replace("\$$key", value)
            }
            parsedMessageLines.add(parsed)
        }

        message.params.forEach { key, value ->
            subject = subject.replace("\$$key", value)
        }

        return Message(subject, parsedMessageLines, emptyMap())
    }


}