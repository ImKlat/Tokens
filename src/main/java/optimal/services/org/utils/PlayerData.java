package optimal.services.org.utils;

/*
 * Created by ImKlat
 * Project: Tokens-Hooker-MySQL
 * Date: 25/2/2022 @ 16:56
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerData {

    public static boolean hasPlayedBefore(Connection connection, UUID uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Player WHERE (UUID=?)");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
    public static void makePlayer(Connection connection, UUID uuid, String name) {
        try {
            if (!hasPlayedBefore(connection, uuid)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Player VALUE (?,?,?)");
            statement.setString(1, uuid.toString());
            statement.setString(2, name);
            statement.setInt(3, 0);
            statement.executeUpdate();
            }
        } catch (SQLException e) {
        }
    }
    public static int getTokens(Connection connection, UUID uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Player WHERE (UUID=?)");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int tokens = resultSet.getInt("Tokens");
                return tokens;
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public static int giveTokens(Connection connection, UUID uuid, int amount) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Player SET Tokens=? WHERE (UUID=?)");
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
        }
        return 0;
    }
}
