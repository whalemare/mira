package ru.whalemare.model

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */

data class Message(
        val subject: String, //$lastname. Отсутствие
        val message: List<String> = listOf(),
        val params: Map<String, String> = emptyMap()
)