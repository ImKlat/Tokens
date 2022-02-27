package optimal.services.org.utils;

/*
 * Created by ImKlat
 * Project: Tokens-Hooker-MySQL
 * Date: 25/2/2022 @ 16:12
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLUtil {

 private Connection connection;
 private String hostname;
 private int port;
 private String database;
 private String username;
 private String password;

 public SQLUtil(String hostname, int port, String database, String username, String password) {
  this.hostname = hostname;
  this.port = port;
  this.database = database;
  this.username = username;
  this.password = password;

  try {
   synchronized (this) {
    if(connection != null && !connection.isClosed()) {
     CC.log("&cError ocurred with MySQL");
     return;
    }
    Class.forName("com.mysql.jdbc.Driver");
    this.connection = (DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.username, this.password));

    CC.log("&aSuccesfully, The MySQL is now connected.");
   }
  }catch(SQLException e) {
   e.printStackTrace();
  }catch(ClassNotFoundException e) {
   e.printStackTrace();
  }
 }

 public Connection getConnection() {
  return connection;
 }
}
