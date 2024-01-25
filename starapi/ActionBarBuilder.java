package net.glitch.starapi;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActionBarBuilder {

    private final JavaPlugin plugin;
    private final Map<UUID, Integer> actionBars;

    private String message;

    public ActionBarBuilder(JavaPlugin plugin) {
        this.plugin = plugin;
        this.actionBars = new HashMap<>();
    }

    public ActionBarBuilder message(String message) {
        this.message = MessageUtils.hex(message);
        return this;
    }

    public void send(Player player) {
        int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            actionBars.remove(player.getUniqueId());
        });

        actionBars.put(player.getUniqueId(), taskId);
    }

    public void clear(Player player) {
        Integer taskId = actionBars.remove(player.getUniqueId());
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }
}