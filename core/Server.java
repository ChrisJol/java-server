package core;

import java.net.*;
import java.io.*;

public class Server {
    public static final int DEFAULT_PORT = 8080;

    public void Start() throws IOException {
        System.out.println("Server running...");
        ServerSocket socket = new ServerSocket( DEFAULT_PORT );
        Socket client = null;

        while( true ) {
            client = socket.accept();
            Request.parse(client);
            client.close();
        }
    }
}