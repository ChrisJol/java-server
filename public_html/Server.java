import java.net.*;
import java.io.*;

public class Server {
    public static final int DEFAULT_PORT = 8080;

    public static void Start() {
        ServerSocket socket = new ServerSocket( DEFAULT_PORT );
        Socket client = null;

        while( true ) {
            client = socket.accept();
            outputRequest( client );
            sendResponse( client );
            client.close();
        }
    }
}