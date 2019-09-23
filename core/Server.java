package core;

import java.net.*;
import java.io.*;

public class Server {
    public static final int DEFAULT_PORT = 8080;

    public void Start() throws IOException {
        ServerSocket socket = new ServerSocket( DEFAULT_PORT );
        Socket client = null;

        while( true ) {
            System.out.println("Server running...");
            client = socket.accept();
            Request.parse(client);
            client.close();
        }
    }
}