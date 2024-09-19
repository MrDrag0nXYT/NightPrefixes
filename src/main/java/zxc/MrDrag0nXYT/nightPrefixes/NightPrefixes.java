package zxc.MrDrag0nXYT.nightPrefixes;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import zxc.MrDrag0nXYT.nightPrefixes.command.PrefixCommand;
import zxc.MrDrag0nXYT.nightPrefixes.util.PlaceholderHook;
import zxc.MrDrag0nXYT.nightPrefixes.util.Utilities;
import zxc.MrDrag0nXYT.nightPrefixes.util.config.Config;
import zxc.MrDrag0nXYT.nightPrefixes.util.database.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class NightPrefixes extends JavaPlugin {

    public static Map<String, String> prefixesCache = new HashMap<String, String>();

    private Config mainConfig;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        mainConfig = new Config(this);
        databaseManager = new DatabaseManager(this, mainConfig);
        DatabaseWorker databaseWorker = databaseManager.getDatabaseWorker();

        try (Connection connection = databaseManager.getConnection()) {
            databaseWorker.initTable(connection);
        } catch (SQLException e) {
            getLogger().severe(String.valueOf(e));
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderHook(this, mainConfig).register();
        }

        if (mainConfig.getConfig().getBoolean("enable-metrics", true)) {
            Metrics metrics = new Metrics(this, 23404);
        }

        getCommand("nightprefixes").setExecutor(new PrefixCommand(this, mainConfig, databaseManager));

        sendTitle(true);
    }

    @Override
    public void onDisable() {
        databaseManager.closeConnection();
        sendTitle(false);
    }

    public void reload() {
        mainConfig.reload();
        databaseManager.updateConnection(mainConfig.getConfig());
    }

    private void sendTitle(boolean isEnable) {
        String isEnableMessage = isEnable ? "<#ace1af>Plugin successfully loaded!" : "<#d45079>Plugin successfully unloaded!";

        ConsoleCommandSender sender = getServer().getConsoleSender();

        sender.sendMessage(Utilities.setColor(" "));
        sender.sendMessage(Utilities.setColor(" <#a880ff>█▄░█ █ █▀▀ █░█ ▀█▀ █▀█ █▀█ █▀▀ █▀▀ █ ▀▄▀ █▀▀ █▀</#a880ff>    <#696969>|</#696969>    <#fcfcfc>Version: <#a880ff>" + this.getDescription().getVersion() + "</#a880ff>"));
        sender.sendMessage(Utilities.setColor(" <#a880ff>█░▀█ █ █▄█ █▀█ ░█░ █▀▀ █▀▄ ██▄ █▀░ █ █░█ ██▄ ▄█</#a880ff>    <#696969>|</#696969>    <#fcfcfc>Author: <#a880ff>MrDrag0nXYT (https://drakoshaslv.ru)</#a880ff>"));
        sender.sendMessage(Utilities.setColor(" "));
        sender.sendMessage(Utilities.setColor(" " + isEnableMessage));
        sender.sendMessage(Utilities.setColor(" "));
    }
}
