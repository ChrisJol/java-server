package htmlpackage;
import java.net.*;
import java.io.*;


public class Server {
    public static final int DEFAULT_PORT = 8080;

    public void Start() throws IOException {
        ServerSocket socket = new ServerSocket( DEFAULT_PORT );
        Socket client = null;
        //System.out.println("Starts program");
        while( true ) {
            client = socket.accept();
            //ParseHTTP.outputRequest(client);
            client.close();
        }
    }
}