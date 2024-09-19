package zxc.MrDrag0nXYT.nightPrefixes.util.database.impl;

import zxc.MrDrag0nXYT.nightPrefixes.util.database.DatabaseWorker;
import zxc.MrDrag0nXYT.nightPrefixes.util.exception.UserBlockedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteDatabaseWorker implements DatabaseWorker {

    @Override
    public void initTable(Connection connection) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS 'prefixes' (
                    'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    'username' TEXT NOT NULL UNIQUE,
                    'prefix' TEXT DEFAULT NULL, 
                    'is_blocked' INTEGER DEFAULT 0 CHECK("is_blocked" >= 0 AND "is_blocked" <= 1)
                );
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }



    @Override
    public String getPrefix(Connection connection, String username) throws SQLException {
        String sql = "SELECT * FROM prefixes WHERE username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("prefix");
        } else {
            return null;
        }
    }

    @Override
    public void setPrefix(Connection connection, String username, String prefix) throws SQLException, UserBlockedException {

        String checkSql = "SELECT is_blocked FROM prefixes WHERE username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(checkSql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int isBlocked = resultSet.getInt("is_blocked");
            if (isBlocked == 1) {
                throw new UserBlockedException();
            }
        }

        String sql = "INSERT INTO prefixes (username, prefix) VALUES (?, ?) ON CONFLICT(username) DO UPDATE SET prefix = excluded.prefix";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, prefix);
        preparedStatement.executeUpdate();

    }

    @Override
    public void forceSetPrefix(Connection connection, String username, String prefix) throws SQLException {
        String sql = "INSERT INTO prefixes (username, prefix) VALUES (?, ?) ON CONFLICT(username) DO UPDATE SET prefix = excluded.prefix";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, prefix);
        preparedStatement.executeUpdate();

    }



    @Override
    public void setBlockStatus(Connection connection, String username, boolean isBlocked) throws SQLException {
        String sql = "UPDATE prefixes SET is_blocked = ? WHERE username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, isBlocked ? 1 : 0);
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
    }

}
