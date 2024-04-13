package net.glitchtechs.starapi.MessageUtils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class AutoResponses {
    private final Map<String, String> responses = new HashMap<>();
    private Sound sound;
    private float volume = 1.0f;
    private float pitch = 1.0f;
    private String title;
    private String subtitle;

    public AutoResponses autoResponses(String trigger, String response) {
        responses.put(trigger.toLowerCase(), response);
        return this;
    }

    public AutoResponses playSound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        return this;
    }

    public AutoResponses sendTitle(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        return this;
    }

    public void build(Player player, String message) {
        for (Map.Entry<String, String> entry : responses.entrySet()) {
            if (message.toLowerCase().contains(entry.getKey())) {
                player.sendMessage(entry.getValue());

                if (sound != null) {
                    player.playSound(player.getLocation(), sound, volume, pitch);
                }

                if (title != null && subtitle != null) {
                    player.sendTitle(title, subtitle, 10, 70, 20);
                }
                break;
            }
        }
    }
}
