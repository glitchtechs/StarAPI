package net.glitchtechs.starapi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardBuilder {
    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Map<Integer, String> lines;
    private final JavaPlugin plugin;

    public ScoreboardBuilder(Player player, String displayName, JavaPlugin plugin) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("dummy", "dummy", MessageUtils.hex(displayName));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.lines = new HashMap();
        this.plugin = plugin;
    }

    public ScoreboardBuilder setLine(int score, String text) {
        this.lines.put(score, text);
        return this;
    }

    public void build() {
        this.refresh();
    }

    private void refresh() {
        this.objective.getScoreboard().resetScores(this.player.getName());
        Iterator var1 = this.lines.entrySet().iterator();

        while(var1.hasNext()) {
            Entry<Integer, String> entry = (Entry)var1.next();
            String line = MessageUtils.hex((String)entry.getValue());
            Score score = this.objective.getScore(line);
            score.setScore((Integer)entry.getKey());
        }

        this.player.setScoreboard(this.scoreboard);
    }
}
