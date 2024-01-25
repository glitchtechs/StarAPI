package net.glitch.starapi;

import java.util.Map;

public class Rank {

    private final String rankName;
    private final Map<String, Boolean> permissions;
    private final String prefix;

    public Rank(String rankName, Map<String, Boolean> permissions, String prefix) {
        this.rankName = rankName;
        this.permissions = permissions;
        this.prefix = prefix;
    }

    public String getRankName() {
        return rankName;
    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public String getPrefix() {
        return prefix;
    }
}
