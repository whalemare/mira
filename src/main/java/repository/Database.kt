package repository

import com.google.gson.Gson
import model.Auth
import java.io.File

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class Database private constructor() {
    companion object {
        const val MAP_REDMINE = "redmine.json"
//        const val KEY_ENDPOINT = "endpoint"
//        const val KEY_API = "keyapi"
        private var database: Database? = null

        fun getInstance(): Database {
            if (database == null) {
                database = Database()
            }

            return database!!
        }
    }

//    init {
//        LoganSquare.registerTypeConverter(Auth::class.java, AuthTypeConverter())
//    }

    fun putRedmine(endpoint: String, apiKey: String) {
        val file = File(MAP_REDMINE).apply {
            createNewFile()
        }
        val auth = Auth().apply {
            this.redmine = endpoint
            this.key = apiKey
        }
        val json = Gson().toJson(auth)
        file.writeText(json)
    }

    fun getRedmine(): Auth? {
        val file = File(MAP_REDMINE).apply {
            createNewFile()
        }
        val auth: Auth? = Gson().fromJson(file.readText(), Auth::class.java)
        return auth
    }
}