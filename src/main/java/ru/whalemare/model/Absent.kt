package ru.whalemare.model

import com.google.gson.annotations.SerializedName

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class Absent {
    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("to")
    var recipient: String? = null
}