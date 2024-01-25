package net.glitch.starapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardBuilder {

    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Map<Integer, String> lines;
    private final JavaPlugin plugin;

    public ScoreboardBuilder(Player player, String displayName, JavaPlugin plugin) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("dummy", "dummy", MessageUtils.hex(displayName));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.lines = new HashMap<>();
        this.plugin = plugin;
    }

    public ScoreboardBuilder addLine(int score, String text) {
        lines.put(score, text);
        return this;
    }


    public void build() {
        refresh();
    }

    private void refresh() {
        objective.getScoreboard().resetScores(player.getName());

        for (Map.Entry<Integer, String> entry : lines.entrySet()) {
            String line = MessageUtils.hex(entry.getValue());
            Score score = objective.getScore(line);
            score.setScore(entry.getKey());
        }

        player.setScoreboard(scoreboard);
    }
}
