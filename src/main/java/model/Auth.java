package model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Anton Vlasov - whalemare
 * @since 2017
 */
public class Auth {

    @SerializedName("redmine")
    public String redmine = null;

    @SerializedName("key")
    public String key = null;

}
