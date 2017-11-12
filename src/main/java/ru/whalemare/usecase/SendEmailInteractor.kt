package ru.whalemare.usecase

import ru.whalemare.model.Absent
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class SendEmailInteractor(
        val absent: Absent,
        val message: ru.whalemare.model.Message
) : Runnable {

    override fun run() {
        val props = Properties()
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.host", "smtp.gmail.com")
        props.put("mail.smtp.port", "587")

        val session = Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(absent.email, absent.password)
                    }
                })

        try {
            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(InternetAddress(absent.email))
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(absent.recipient))
            mimeMessage.subject = message.subject
            mimeMessage.setText(message.message.joinToString(separator = "\n"))
            Transport.send(mimeMessage)

            println("Message of absent successfully send!")
        } catch (e: MessagingException) {
            println("Can`t send message, please toggle \'less secure apps\' in your gmail account: ")
            println("https://myaccount.google.com/lesssecureapps")
            throw RuntimeException(e)
        }

    }
}