package core;

import java.net.*;
import java.io.*;

public class Server {
    public final String configFilePath = "./conf/httpd.conf.txt";
    static ConfigReader configuration;
    Request request;
    ServerSocket socket;
    Socket client;
    
    public void Start() throws IOException {
        request = new Request();
        configuration = ConfigReader.getInstance();
        socket = new ServerSocket( configuration.getDefaultPort() );
        System.out.println("Server running...");

        while( true ) {
            client = socket.accept();
            request.parse(client);
            client.close();
        }
    }
}