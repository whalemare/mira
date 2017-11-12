//package ru.whalemare.usecase
//
//import ru.whalemare.command.AbsentCommand
//import java.util.*
//import javax.mail.*
//import javax.mail.internet.InternetAddress
//import javax.mail.internet.MimeMessage
//
//
///**
// * @since 2017
// * @author Anton Vlasov - whalemare
// */
//class SendEmailInteractor(
//        val auth: PasswordAuthentication,
//        val message: AbsentCommand.Message
//) : Runnable {
//
//    override fun run() {
//        val props = Properties()
//        props.put("mail.smtp.auth", "true")
//        props.put("mail.smtp.starttls.enable", "true")
//        props.put("mail.smtp.host", "smtp.gmail.com")
//        props.put("mail.smtp.port", "587")
//
//        val session = Session.getInstance(props,
//                object : javax.mail.Authenticator() {
//                    override fun getPasswordAuthentication(): PasswordAuthentication {
//                        return auth
//                    }
//                })
//
//        try {
//            val mimeMessage = MimeMessage(session)
//            mimeMessage.setFrom(InternetAddress(message.from))
//            mimeMessage.setRecipients(Message.RecipientType.TO,
////                    InternetAddress.parse("absence@magora-systems.com"))
//                    InternetAddress.parse(message.to))
////                    InternetAddress.parse("vbezsms@gmail.com"))
//            mimeMessage.subject = message.subject
//            mimeMessage.setText(message.message)
//            Transport.send(mimeMessage)
//
//            println("Message of absent successfully send!")
//        } catch (e: MessagingException) {
//            println("Can`t send message, please toggle \'less secure apps\' in your gmail account: ")
//            println("https://myaccount.google.com/lesssecureapps")
//            throw RuntimeException(e)
//        }
//
//    }
//}