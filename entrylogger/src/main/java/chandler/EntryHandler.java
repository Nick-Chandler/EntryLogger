package chandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EntryHandler implements Runnable {

  // Defines an EntryHandler which is used to process incoming byte data from the ESP-32 controlled ultrasonic sensor

  Socket s;
  Connection con;

  // requires a client socket along with a valid database connection
  public EntryHandler(Socket s, Connection con) {
    this.s = s;
    this.con = con;
  }
  // incoming data processing
  public void run(){
    
    try {
      InputStreamReader isr = new InputStreamReader(s.getInputStream());
      if (!isr.ready()) {
        return;
      }
        
      BufferedReader br = new BufferedReader(isr);
      String str = br.readLine();
      System.out.println("Recieved Message: " + str);
      logData();
    }
    catch (IOException | SQLException e) {
      System.out.println("Error reading sensor data");
    }
  }

  // Stores time and date of all passes
  private void logData() throws SQLException {

    String sql = "insert into logger (pass_Time, pass_Date) values (current_time(),current_date())";
    Statement statement = con.prepareStatement(sql);
    statement.executeUpdate(sql);
    System.out.println("ENTRY LOGGED");
  } 
}
