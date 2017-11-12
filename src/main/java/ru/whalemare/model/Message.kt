package model

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */

data class Message(
        val subject: String, //$lastname. Отсутствие
        val message: List<String> = listOf(),
        val params: Map<String, String> = emptyMap()
)
//data class Params(
//		val reason: String, //учёба
//		val time: String, //12:00
//		val source: String //telegram: whalemare, slack: whalemare phone: (смотри в intranet)
//)