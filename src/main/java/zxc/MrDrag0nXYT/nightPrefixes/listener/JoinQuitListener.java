package zxc.MrDrag0nXYT.nightPrefixes.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import zxc.MrDrag0nXYT.nightPrefixes.NightPrefixes;
import zxc.MrDrag0nXYT.nightPrefixes.util.database.DatabaseManager;
import zxc.MrDrag0nXYT.nightPrefixes.util.database.DatabaseWorker;

import java.sql.Connection;
import java.sql.SQLException;

public class JoinQuitListener implements Listener {

    private DatabaseManager databaseManager;

    public JoinQuitListener(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try (Connection connection = databaseManager.getConnection()) {
            DatabaseWorker databaseWorker = databaseManager.getDatabaseWorker();
            String prefix = databaseWorker.getPrefix(connection, player.getName());

            NightPrefixes.prefixesCache.put(player.getName(), prefix);

        } catch (SQLException e) {
            Bukkit.getLogger().severe(String.valueOf(e));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        NightPrefixes.prefixesCache.remove(player.getName());
    }

}
