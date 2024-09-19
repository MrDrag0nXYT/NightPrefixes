package zxc.MrDrag0nXYT.nightPrefixes.util.database;

import zxc.MrDrag0nXYT.nightPrefixes.util.exception.UserBlockedException;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseWorker {
    void initTable(Connection connection) throws SQLException;

    String getPrefix(Connection connection, String username) throws SQLException;
    void setPrefix(Connection connection, String username, String prefix) throws SQLException, UserBlockedException;
    void forceSetPrefix(Connection connection, String username, String prefix) throws SQLException;

    void setBlockStatus(Connection connection, String username, boolean isBlocked) throws SQLException;
}
