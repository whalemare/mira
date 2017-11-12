package ru.whalemare.repository

import com.google.gson.Gson
import ru.whalemare.model.Absent
import ru.whalemare.model.Auth
import ru.whalemare.model.Message
import java.io.File

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class Database private constructor() {
    companion object {
        const val MAP_REDMINE = "redmine.json"
        const val FILE_EMAIL = "email.json"
        const val FILE_ABSENT_MESSAGE = "absent.json"
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

    val gson = Gson()

    fun putRedmine(endpoint: String, apiKey: String) {
        if (endpoint.isBlank() && apiKey.isBlank()) {
            val file = File(MAP_REDMINE)
            file.delete()
        } else {
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
    }

    fun getRedmine(): Auth? {
        val file = File(MAP_REDMINE).apply {
            createNewFile()
        }
        val auth: Auth? = gson.fromJson(file.readText(), Auth::class.java)
        return auth
    }

    fun getAbsentCreds(): Absent? {
        val file = File(FILE_EMAIL).apply {
            createNewFile()
        }
        val absentCreds: Absent? = gson.fromJson(file.readText(), Absent::class.java)
        return absentCreds
    }

    fun putAbsentCreds(absent: Absent): String {
        val file = File(FILE_EMAIL).apply {
            createNewFile()
        }
        val json = gson.toJson(absent, Absent::class.java)
        file.writeText(json)
        return file.path
    }

    fun getAbsentMessage(): Message? {
        val file = File(FILE_ABSENT_MESSAGE).apply {
            createNewFile()
        }
        val message: Message? = gson.fromJson(file.readText(), Message::class.java)
        return message
    }

    fun putAbsentMessage(message: Message): String {
        val file = File(FILE_ABSENT_MESSAGE).apply {
            createNewFile()
        }
        val json = gson.toJson(message, Message::class.java)
        file.writeText(json)
        return file.path
    }
}