package model

import com.google.gson.annotations.SerializedName

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class Auth {

    @SerializedName("redmine")
    var redmine: String? = null

    @SerializedName("key")
    var key: String? = null

}