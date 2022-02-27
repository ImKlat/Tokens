package optimal.services.org.utils;

/*
 * Created by ImKlat
 * Project: Tokens-Hooker-MySQL
 * Date: 25/2/2022 @ 16:12
 */

import optimal.services.org.Main;
import optimal.services.org.utils.config.ConfigFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLUtil {

 private ConfigFile mainConfig = Main.getInstance().getMainConfig();

 private Connection connection;
 String Chostname = mainConfig.getString("MySQL.HOSTNAME");
 int Cport = mainConfig.getInt("MySQL.PORT");
 String Cdatabase = mainConfig.getString("MySQL.DATABASE");
 String Cusername = mainConfig.getString("MySQL.USERNAME");
 String Cpassword = mainConfig.getString("MySQL.PASSWORD");

 public SQLUtil() {
  try {
   synchronized (this) {
    if(connection != null && !connection.isClosed()) {
     CC.log("&cError ocurred with MySQL");
     return;
    }
    Class.forName("com.mysql.jdbc.Driver");
    this.connection = (DriverManager.getConnection("jdbc:mysql://" + this.Chostname + ":" + this.Cport + "/" + this.Cdatabase, this.Cusername, this.Cpassword));

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
