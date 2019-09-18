import java.net.*;
import java.io.*;

public class Server {
    public static final int DEFAULT_PORT = 8080;
    ServerSocket socket = new ServerSocket( DEFAULT_PORT );
    Socket client = null;

    public void Start() {
        while( true ) {
            client = socket.accept();
            client.close();
        }
    }
}