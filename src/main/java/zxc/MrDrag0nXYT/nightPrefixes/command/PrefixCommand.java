package zxc.MrDrag0nXYT.nightPrefixes.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zxc.MrDrag0nXYT.nightPrefixes.NightPrefixes;
import zxc.MrDrag0nXYT.nightPrefixes.util.Utilities;
import zxc.MrDrag0nXYT.nightPrefixes.util.config.Config;
import zxc.MrDrag0nXYT.nightPrefixes.util.database.DatabaseManager;
import zxc.MrDrag0nXYT.nightPrefixes.util.database.DatabaseWorker;
import zxc.MrDrag0nXYT.nightPrefixes.util.exception.UserBlockedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefixCommand implements CommandExecutor, TabCompleter {

    private final NightPrefixes plugin;
    private final Config config;
    private final DatabaseManager databaseManager;

    public PrefixCommand(NightPrefixes plugin, Config config, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.config = config;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        YamlConfiguration yamlConfiguration = config.getConfig();

        if (strings.length == 0) {
            for (String string : yamlConfiguration.getStringList("messages.command.usage")) {
                commandSender.sendMessage(Utilities.setColor(string));
            }
            return true;
        }

        switch (strings[0].toLowerCase()) {
            case "set" -> {
                if (commandSender.hasPermission("nightprefixes.player.prefix.reset")) {

                    String[] messageArray = Arrays.copyOfRange(strings, 1, strings.length);
                    String prefix = String.join(" ", messageArray);

                    if (prefix.length() <= yamlConfiguration.getInt("max-length", 32)) {
                        if (commandSender instanceof Player player && strings.length > 1) {

                            try (Connection connection = databaseManager.getConnection()) {
                                DatabaseWorker databaseWorker = databaseManager.getDatabaseWorker();
                                databaseWorker.setPrefix(connection, player.getName(), prefix);

                                NightPrefixes.prefixesCache.put(player.getName(), prefix);

                                for (String string : yamlConfiguration.getStringList("messages.command.set")) {
                                    commandSender.sendMessage(
                                            Utilities.setColor(
                                                    string
                                                            .replace("%prefix%", prefix)
                                            )
                                    );
                                }

                            } catch (SQLException e) {
                                plugin.getLogger().severe(String.valueOf(e));

                            } catch (UserBlockedException e) {
                                for (String string : yamlConfiguration.getStringList("messages.command.set-blocked")) {
                                    commandSender.sendMessage(Utilities.setColor(string));
                                }
                            }

                        }

                    } else {
                        for (String string : yamlConfiguration.getStringList("messages.command.too-long")) {
                            commandSender.sendMessage(
                                    Utilities.setColor(
                                            string
                                                    .replace("%prefix_max_length%", String.valueOf(yamlConfiguration.getInt("max-length", 32)))
                                    )
                            );
                        }
                    }

                } else {
                    for (String string : yamlConfiguration.getStringList("messages.no-permission")) {
                        commandSender.sendMessage(Utilities.setColor(string));
                    }
                }
            }

            case "reset" -> {
                if (commandSender.hasPermission("nightprefixes.player.prefix.reset")) {
                    if (commandSender instanceof Player player) {
                        try (Connection connection = databaseManager.getConnection()) {
                            DatabaseWorker databaseWorker = databaseManager.getDatabaseWorker();
                            databaseWorker.setPrefix(connection, player.getName(), null);

                            NightPrefixes.prefixesCache.remove(player.getName());

                            for (String string : yamlConfiguration.getStringList("messages.command.reset")) {
                                commandSender.sendMessage(Utilities.setColor(string));
                            }

                        } catch (SQLException e) {
                            plugin.getLogger().severe(String.valueOf(e));

                        } catch (UserBlockedException e) {
                            for (String string : yamlConfiguration.getStringList("messages.command.set-blocked")) {
                                commandSender.sendMessage(Utilities.setColor(string));
                            }
                        }
                    }

                } else {
                    for (String string : yamlConfiguration.getStringList("messages.no-permission")) {
                        commandSender.sendMessage(Utilities.setColor(string));
                    }
                }
            }

            case "admin" -> {

                if (strings.length > 1) {
                    switch (strings[1].toLowerCase()) {

                        case "reset" -> {
                            if (commandSender.hasPermission("nightprefixes.admin.prefix.reset")) {

                                try (Connection connection = databaseManager.getConnection()) {
                                    DatabaseWorker databaseWorker = databaseManager.getDatabaseWorker();
                                    databaseWorker.forceSetPrefix(connection, strings[2], null);

                                    NightPrefixes.prefixesCache.remove(strings[2]);

                                    for (String string : yamlConfiguration.getStringList("messages.command.admin-reset")) {
                                        commandSender.sendMessage(Utilities.setColor(
                                                string
                                                        .replace("%player%", strings[2])
                                        ));
                                    }

                                } catch (SQLException e) {
                                    plugin.getLogger().severe(String.valueOf(e));
                                }

                            } else {
                                for (String string : yamlConfiguration.getStringList("messages.no-permission")) {
                                    commandSender.sendMessage(Utilities.setColor(string));
                                }
                            }
                        }

                        case "reload" -> {
                            if (commandSender.hasPermission("nightprefixes.admin.reload")) {

                                plugin.reload();
                                for (String string : yamlConfiguration.getStringList("messages.command.reload.success")) {
                                    commandSender.sendMessage(Utilities.setColor(string));
                                }

                            } else {
                                for (String string : yamlConfiguration.getStringList("messages.no-permission")) {
                                    commandSender.sendMessage(Utilities.setColor(string));
                                }
                            }
                        }

                        case "ban" -> {
                            if (commandSender.hasPermission("nightprefixes.admin.ban")) {

                                if (strings.length > 2) {
                                    try (Connection connection = databaseManager.getConnection()) {
                                        DatabaseWorker databaseWorker = databaseManager.getDatabaseWorker();

                                        databaseWorker.forceSetPrefix(connection, strings[2], null);
                                        NightPrefixes.prefixesCache.remove(strings[2]);
                                        databaseWorker.setBlockStatus(connection, strings[2], true);

                                        for (String string : yamlConfiguration.getStringList("messages.command.blocked")) {
                                            commandSender.sendMessage(Utilities.setColor(
                                                    string
                                                            .replace("%player%", strings[2])
                                            ));
                                        }

                                    } catch (SQLException e) {
                                        plugin.getLogger().severe(String.valueOf(e));
                                    }
                                }

                            } else {
                                for (String string : yamlConfiguration.getStringList("messages.no-permission")) {
                                    commandSender.sendMessage(Utilities.setColor(string));
                                }
                            }
                        }

                        case "unban" -> {
                            if (commandSender.hasPermission("nightprefixes.admin.unban")) {

                                if (strings.length > 2) {
                                    try (Connection connection = databaseManager.getConnection()) {
                                        DatabaseWorker databaseWorker = databaseManager.getDatabaseWorker();
                                        databaseWorker.setBlockStatus(connection, strings[2], false);

                                        for (String string : yamlConfiguration.getStringList("messages.command.unblocked")) {
                                            commandSender.sendMessage(Utilities.setColor(
                                                    string
                                                            .replace("%player%", strings[2])
                                            ));
                                        }

                                    } catch (SQLException e) {
                                        plugin.getLogger().severe(String.valueOf(e));
                                    }
                                }

                            } else {
                                for (String string : yamlConfiguration.getStringList("messages.no-permission")) {
                                    commandSender.sendMessage(Utilities.setColor(string));
                                }
                            }
                        }

                        default -> {
                            for (String string : yamlConfiguration.getStringList("messages.command.usage")) {
                                commandSender.sendMessage(Utilities.setColor(string));
                            }
                        }

                    }
                }
            }

            default -> {
                for (String string : yamlConfiguration.getStringList("messages.command.usage")) {
                    commandSender.sendMessage(Utilities.setColor(string));
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 1) {
            List<String> completions = new ArrayList<>(List.of("set", "reset"));

            if (commandSender.hasPermission("nightprefixes.admin.reload") || commandSender.hasPermission("nightprefixes.admin.prefix.reset"))
                completions.add("admin");

            return completions;

        } else if (strings.length == 2 && strings[0].equalsIgnoreCase("admin")) {
            List<String> completions = new ArrayList<>();

            if (commandSender.hasPermission("nightprefixes.admin.reload"))
                completions.add("reload");

            if (commandSender.hasPermission("nightprefixes.admin.prefix.reset"))
                completions.add("reset");

            if (commandSender.hasPermission("nightprefixes.admin.ban"))
                completions.add("ban");

            if (commandSender.hasPermission("nightprefixes.admin.unban"))
                completions.add("unban");

            return completions;

        } else if (
                strings.length == 3 &&
                        commandSender.hasPermission("nightprefixes.admin.prefix.reset") &&
                        strings[0].equalsIgnoreCase("admin") &&
                        strings[1].equalsIgnoreCase("reset")
        ) {
            return null;

        } else if (
                strings.length == 3 &&
                        commandSender.hasPermission("nightprefixes.admin.ban") &&
                        strings[0].equalsIgnoreCase("admin") &&
                        strings[1].equalsIgnoreCase("ban")
        ) {
            return null;

        } else if (
                strings.length == 3 &&
                        commandSender.hasPermission("nightprefixes.admin.unban") &&
                        strings[0].equalsIgnoreCase("admin") &&
                        strings[1].equalsIgnoreCase("unban")
        ) {
            return null;
        }

        return List.of();
    }
}
