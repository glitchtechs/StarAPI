package net.glitchtechs.starapi

import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class WebhookBuilder private constructor(private val webhookUrl: String) {
    private var message: String? = null

    companion object {
        fun create(webhookUrl: String): WebhookBuilder {
            return WebhookBuilder(webhookUrl)
        }
    }

    fun addMessage(message: String): WebhookBuilder {
        this.message = message
        return this
    }

    fun build() {
        try {
            val url = URL(webhookUrl)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val payload = "{\"content\":\"$message\"}"

            connection.outputStream.use { outputStream ->
                val input = payload.toByteArray(StandardCharsets.UTF_8)
                outputStream.write(input, 0, input.size)
            }

            val responseCode = connection.responseCode
            println("HTTP Response Code: $responseCode")

            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
