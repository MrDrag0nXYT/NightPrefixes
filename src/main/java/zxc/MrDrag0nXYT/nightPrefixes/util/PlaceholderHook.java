package zxc.MrDrag0nXYT.nightPrefixes.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import zxc.MrDrag0nXYT.nightPrefixes.NightPrefixes;
import zxc.MrDrag0nXYT.nightPrefixes.util.config.Config;

public class PlaceholderHook extends PlaceholderExpansion {

    private final NightPrefixes plugin;
    private Config config;

    public PlaceholderHook(NightPrefixes plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nightprefixes";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        String nullPrefix = config.getConfig().getString("default-prefix", null);

        if (identifier.equalsIgnoreCase("prefix")) {
            if (player != null) {
                return NightPrefixes.prefixesCache.getOrDefault(player.getName(), nullPrefix);
            } else {
                return nullPrefix;
            }
        }

        return null;
    }

}
