package zxc.MrDrag0nXYT.nightPrefixes.util.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.YamlConfiguration;
import zxc.MrDrag0nXYT.nightPrefixes.NightPrefixes;
import zxc.MrDrag0nXYT.nightPrefixes.util.config.Config;
import zxc.MrDrag0nXYT.nightPrefixes.util.database.impl.SQLiteDatabaseWorker;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private final NightPrefixes plugin;
    private HikariDataSource dataSource;
    private DatabaseWorker databaseWorker;

    public DatabaseManager(NightPrefixes plugin, Config config) {
        this.plugin = plugin;
        YamlConfiguration pluginConfig = config.getConfig();

        updateConnection(pluginConfig);
    }

    public void updateConnection(YamlConfiguration pluginConfig) {
        closeConnection();

        HikariConfig hikariConfig = new HikariConfig();

        switch (pluginConfig.getString("database.type", "sqlite").toLowerCase()) {
            default -> {
                hikariConfig.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + File.separator + "database.db");
                databaseWorker = new SQLiteDatabaseWorker();
            }
        }

        dataSource = new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public DatabaseWorker getDatabaseWorker() {
        return databaseWorker;
    }

    public void closeConnection() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

}
