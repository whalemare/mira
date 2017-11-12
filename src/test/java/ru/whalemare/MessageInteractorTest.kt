package ru.whalemare

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import ru.whalemare.model.Message
import ru.whalemare.usecase.MessageInteractor

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class MessageInteractorTest {

    lateinit var messageInteractor: MessageInteractor
    lateinit var inputMessage: Message

    @Before
    fun setup() {
        inputMessage = Message(
                subject = "\$lastname. Отсутствие",
                message = listOf(
                        "Причина отсутствия: \$reason",
                        "Какое время буду отсутствовать: буду на работе после \$time",
                        "Каналы связи с вами: \$source"
                ),
                params = mapOf(
                        "lastname" to "Власов",
                        "reason" to "учёба",
                        "time" to "12:00",
                        "source" to "telegram: whalemare, slack: whalemare, phone: (смотри в intranet)"
                )
        )
        messageInteractor = MessageInteractor(inputMessage)
    }

    @Test
    fun `should correct parse subject`() {
        val outputMessage = messageInteractor.call()
        assertEquals(outputMessage.subject, "Власов. Отсутствие")
    }

    @Test
    fun `should correct count lines input and output`() {
        val outputMessage = messageInteractor.call()
        assertEquals(inputMessage.message.size, outputMessage.message.size)
    }

    @Test
    fun `should correct parse message`() {
        val outputMessage = messageInteractor.call()
        assertEquals("Причина отсутствия: учёба", outputMessage.message[0])
        assertEquals("Какое время буду отсутствовать: буду на работе после 12:00", outputMessage.message[1])
        assertEquals("Каналы связи с вами: telegram: whalemare, slack: whalemare, phone: (смотри в intranet)", outputMessage.message[2])
    }
}