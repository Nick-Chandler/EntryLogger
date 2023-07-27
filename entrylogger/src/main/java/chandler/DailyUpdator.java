package chandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DailyUpdator implements Runnable{

  // Defines the DailyUpdator class which is used to log daily number of passes the sensor has detected
  // DailyUpdator Objects take in a database connection and can be run to log daily number of passes the sensor has detected

  // NOTE:
  // The database name, tablename, username, and password has been intentionally left out since this project is shared publicly
  // code written in all capitals () must be replaced with own database information (ex. PASSWORD)
  // See Setup.txt for full information in order to create a compatible database

  Connection con;

  // requires a valid database connection
  public DailyUpdator(Connection con) {
    this.con = con;
  }

  
  public void run() {
    int numPasses;

    try {

      // Retrieves and counts entries from the current date
      String sqlGet = String.format("select * from entryloggerdb.logger where Pass_Date=%s", "current_date()");
      Statement stmt1 = con.createStatement();
      ResultSet rs = stmt1.executeQuery(sqlGet);

      numPasses = 0;
      while(rs.next()) {
        numPasses++;
      }

      // Inserts statistics into the dailylog table
      String sqlPut = String.format("insert into dailylog (Date_Recorded, Passes) values (%s,%s)", "current_date()",String.valueOf(numPasses));
      PreparedStatement stmt2 = con.prepareStatement(sqlPut);
      stmt2.executeUpdate(sqlPut);
      System.out.println("DAILY STATISTICS LOGGED");

    } catch (SQLException e) {
      e.printStackTrace();
    }
}
}
