package usecase

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Execute a bash command. We can handle complex bash commands including
 * multiple executions (; | && ||), quotes, expansions ($), escapes (\), e.g.:
 * "cd /abc/def; mv ghi 'older ghi '$(whoami)"
 * @param command for execute with bash
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
class InteractorBash(private val command: String) : Runnable {

    override fun run() {
        println(command)
        val r = Runtime.getRuntime()
        // Use bash -c so we can handle things like multi commands separated by ; and
        // things like quotes, $, |, and \. My tests show that command comes as
        // one argument to bash, so we do not need to quote it to make it one thing.
        // Also, exec may object if it does not have an executable file as the first thing,
        // so having bash here makes it happy provided bash is installed and in path.
        val commands = arrayOf("bash", "-c", command)
        try {
            val p = r.exec(commands)

            p.waitFor()
            val b = BufferedReader(InputStreamReader(p.inputStream))
            var line: String? = b.readLine()
            while (line != null) {
                println(line)
                line = b.readLine()
            }

            b.close()
        } catch (e: Exception) {
            System.err.println("Failed to execute bash with command: " + command)
            e.printStackTrace()
        }
    }

}