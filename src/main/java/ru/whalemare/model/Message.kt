package ru.whalemare.model

import com.google.gson.annotations.SerializedName

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */

data class Message(
        @SerializedName("subject")
        var subject: String = "",
        @SerializedName("message")
        var message: List<String> = listOf(),
        @SerializedName("params")
        var params: MutableMap<String, String> = mutableMapOf()
)