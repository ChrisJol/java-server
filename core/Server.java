package core;

import java.net.*;
import java.io.*;

public class Server {
    public final String configFilePath = "./conf/httpd.conf.txt";
    static ConfigReader configuration;
    
    public void Start() throws IOException {
        configuration = new ConfigReader(configFilePath);
        configuration.load();

        System.out.println("Server running...");
        ServerSocket socket = new ServerSocket( configuration.getDefaultPort() );
        Socket client = null;

        while( true ) {
            client = socket.accept();
            Request request = new Request();
            request.parse(client);
            client.close();
        }
    }
}