package net.glitch.starapi;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class RankManager {

    private final Map<String, Rank> playerRanks;
    private Map<String, Rank> ranks;

    public RankManager() {
        this.playerRanks = new HashMap<>();
        ranks = new HashMap<>();
    }

    public void setPlayerRank(Player player, Rank rank) {
        playerRanks.put(player.getName(), rank);
    }

    public Rank getPlayerRank(Player player) {
        return playerRanks.getOrDefault(player.getUniqueId(), getDefaultRank());
    }

    public Rank getDefaultRank() {
        return ranks.get("Default");
    }

    public Rank getRankByName(String rankName) {
        Rank rank = playerRanks.get(rankName);

        if (rank != null) {
            return rank;
        } else {
            return getDefaultRank();
        }
    }


}
