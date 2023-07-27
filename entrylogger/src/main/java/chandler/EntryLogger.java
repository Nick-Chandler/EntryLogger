package chandler;

import java.net.Socket;
import java.sql.Connection;
import java.time.LocalTime;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class EntryLogger {

  static Connection con;

  // EntryLogger Driver 
  public static void main(String[] args) throws Exception {

    // Create Server Obj
    EntryServer server = new EntryServer();

    // Establish Database Connection
    con = server.connectDB();
    while(con == null){
      System.out.println("Database Configuration in progress...");
      System.out.println("This could take up to 90 seconds...");
      Thread.sleep(15000);
      con = server.connectDB();
    }

    // Listen For Client (Microcontroller) Connection
    DailyUpdator updator = new DailyUpdator(con);
    Socket clientSocket = server.listen();
    EntryHandler handler = new EntryHandler(clientSocket, con);

    // Listening for Data from Sensor
    ScheduledFuture<?> sensorListener = server.scheduler.scheduleAtFixedRate(handler,1000,250,TimeUnit.MILLISECONDS);

    // Log Daily Statistics
    ScheduledFuture<?> dailyScheduler = server.scheduler.scheduleAtFixedRate(updator,(24*60*60 - LocalTime.now().toSecondOfDay())/60 - 5,24*60,TimeUnit.MINUTES);
  }
}
