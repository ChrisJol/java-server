package core;

import java.net.*;
import java.io.*;

public class Server {
    public final String configFilePath = "./conf/httpd.conf.txt";
    
    public void Start() throws IOException {
        ConfigReader cfReader = new ConfigReader(configFilePath);
        cfReader.load();

        System.out.println("Server running...");
        ServerSocket socket = new ServerSocket( cfReader.getDefaultPort() );
        Socket client = null;

        while( true ) {
            client = socket.accept();
            Request request = new Request();
            request.parse(client);
            client.close();
        }
    }
}