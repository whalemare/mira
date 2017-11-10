import com.taskadapter.redmineapi.bean.User
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class AbsentEmail(
        private val user: User,
        private val time: String
) : Runnable {

    override fun run() {
        val username = "vlasov@magora-systems.com"
        val password = Credentials.password

        val props = Properties()
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.host", "smtp.gmail.com")
        props.put("mail.smtp.port", "587")

        val session = Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(username, password)
                    }
                })

        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(username))
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("absence@magora-systems.com"))
//                    InternetAddress.parse("vbezsms@gmail.com"))
            message.subject = "${user.lastName}. Опоздание"
            message.setText(
                    "Причина отсутствия: учеба\n" +
                            "Какое время буду отсутствовать: буду на работе после $time\n" +
                            "Каналы связи с вами: skype, почта, телефон"
            )

            Transport.send(message)

            println("Done")
        } catch (e: MessagingException) {
            println("Can`t send message, please toggle \'less secure apps\' in your gmail account: ")
            println("https://myaccount.google.com/lesssecureapps")
            throw RuntimeException(e)
        }

    }
}