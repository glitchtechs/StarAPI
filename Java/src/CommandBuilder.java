package net.glitchtechs.starapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommandBuilder {
    private final JavaPlugin plugin;
    private String commandName;
    private final List<String> aliases;
    private int cooldown;
    private String permission;
    private String[] argumentNames;
    private Function<String[], String> argumentMapper;
    private String[] messages;

    private CommandBuilder(JavaPlugin plugin) {
        this.plugin = plugin;
        this.aliases = new ArrayList<>();
    }

    public static CommandBuilder create(JavaPlugin plugin, String commandName) {
        return new CommandBuilder(plugin).setCommandName(commandName);
    }

    public CommandBuilder setCooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public CommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder setArguments(String... argumentNames) {
        this.argumentNames = argumentNames;
        return this;
    }

    public CommandBuilder setArgumentMapper(Function<String[], String> argumentMapper) {
        this.argumentMapper = argumentMapper;
        return this;
    }

    public CommandBuilder setMessages(String... messages) {
        this.messages = messages;
        return this;
    }

    public CommandBuilder addAliases(String... aliases) {
        for (String alias : aliases) {
            this.aliases.add(alias);
        }
        return this;
    }

    public void build() {
        plugin.getCommand(commandName).setExecutor(createExecutor());

        for (String alias : aliases) {
            plugin.getCommand(alias).setExecutor(createExecutor());
        }
    }

    private CommandExecutor createExecutor() {
        return new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if (permission != null && !sender.hasPermission(permission)) {
                    sender.sendMessage(messages[1]);
                    return true;
                }

                if (cooldown > 0 && sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasMetadata("cooldown_" + commandName)) {
                        long lastExecutionTime = player.getMetadata("cooldown_" + commandName).get(0).asLong();
                        long currentTime = System.currentTimeMillis();
                        long timeDifference = currentTime - lastExecutionTime;
                        if (timeDifference < cooldown * 1000) {
                            long remainingCooldown = cooldown - timeDifference / 1000;
                            sender.sendMessage(messages[0].replace("<time>", String.valueOf(remainingCooldown)));
                            return true;
                        }
                    }
                    player.setMetadata("cooldown_" + commandName, new FixedMetadataValue(plugin, System.currentTimeMillis()));
                }

                String result = argumentMapper.apply(args);
                sender.sendMessage(result.replace("<sender>", sender.getName()));
                return true;
            }
        };
    }

    private CommandBuilder setCommandName(String commandName) {
        this.commandName = commandName;
        return this;
    }
}
