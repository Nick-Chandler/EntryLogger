package chandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class EntryServer {

  // Defines an EntryServer which awaits a client connection then logs a new entry whenever signaled by client device


  ServerSocket ss;
  final ScheduledExecutorService scheduler;

  public EntryServer() throws Exception {
    this.ss = setupServer();
    this.scheduler = Executors.newScheduledThreadPool(2);
  }

  // basic server configuration
  public ServerSocket setupServer() throws Exception{
    System.out.println("Server Starting...");
    ServerSocket ss = new ServerSocket(9999);
    System.out.println("Server Started Successfully");
    return ss;
  }

  // Database Connection
  public Connection connectDB() throws Exception {
    String url = "jdbc:mysql://172.31.0.2:3306/entryloggerdb";

    try {
      Connection con = DriverManager.getConnection(url, "root", "root");
      System.out.println("Database Connected");
      return con;
    } catch (SQLException e) {
  
    }
    return null;
  }

  // Waits for client socket connection
  public Socket listen() throws IOException {
    System.out.println("Waiting for Client Connection...");
    Socket s = this.ss.accept();
    System.out.println("Client Connected Successfully");
    return s;
  }

}