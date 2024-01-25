package net.glitch.starapi;

import java.util.HashMap;
import java.util.Map;

public class RankBuilder {

    private final String rankName;
    private final Map<String, Boolean> permissions;
    private String prefix;

    public RankBuilder(String rankName) {
        this.rankName = rankName;
        this.permissions = new HashMap<>();
        this.prefix = "";
    }

    public RankBuilder addPermission(String permission, boolean value) {
        permissions.put(permission, value);
        return this;
    }

    public RankBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public Rank build() {
        return new Rank(rankName, permissions, prefix);
    }
}
