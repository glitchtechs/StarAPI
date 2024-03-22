package net.glitchtechs.starapi

import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Function

class CommandBuilder(private val plugin: JavaPlugin, private val commandName: String) {
    private val aliases: MutableList<String> = ArrayList()
    private var cooldown = 0
    private var permission: String? = null
    private lateinit var argumentNames: Array<String>
    private var argumentMapper: Function<Array<String>, String>? = null
    private lateinit var messages: Array<String>

    fun setCooldown(cooldown: Int): CommandBuilder {
        this.cooldown = cooldown
        return this
    }

    fun setPermission(permission: String?): CommandBuilder {
        this.permission = permission
        return this
    }

    fun setArguments(vararg argumentNames: String): CommandBuilder {
        this.argumentNames = argumentNames as Array<String>
        return this
    }

    fun setArgumentMapper(argumentMapper: Function<Array<String>, String>?): CommandBuilder {
        this.argumentMapper = argumentMapper
        return this
    }

    fun setMessages(vararg messages: String): CommandBuilder {
        this.messages = messages as Array<String>
        return this
    }

    fun addAliases(vararg aliases: String): CommandBuilder {
        this.aliases.addAll(aliases)
        return this
    }

    fun build() {
        val command = plugin.getCommand(commandName)
        if (command != null) {
            command.setExecutor(createExecutor())

            for (alias in aliases) {
                val aliasCommand = plugin.getCommand(alias)
                aliasCommand?.setExecutor(createExecutor())
            }
        } else {
            plugin.logger.warning("Command '$commandName' does not exist.")
        }
    }

    private fun createExecutor(): CommandExecutor {
        return CommandExecutor { sender, command, label, args ->
            if (permission != null && !sender.hasPermission(permission!!)) {
                sender.sendMessage(messages[1])
                return@CommandExecutor true
            }
            if (cooldown > 0 && sender is Player) {
                val player = sender
                if (player.hasMetadata("cooldown_$commandName")) {
                    val lastExecutionTime = player.getMetadata("cooldown_$commandName")[0].asLong()
                    val currentTime = System.currentTimeMillis()
                    val timeDifference = currentTime - lastExecutionTime
                    if (timeDifference < cooldown * 1000) {
                        val remainingCooldown = cooldown - timeDifference / 1000
                        sender.sendMessage(messages[0].replace("<time>", remainingCooldown.toString()))
                        return@CommandExecutor true
                    }
                }
                player.setMetadata("cooldown_$commandName", FixedMetadataValue(plugin, System.currentTimeMillis()))
            }

            val result = argumentMapper?.apply(args)
            if (result != null) {
                sender.sendMessage(result.replace("<sender>", sender.name))
            }
            true
        }
    }
}
