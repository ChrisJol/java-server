package core;

import core.response.*;
import java.net.*;
import java.io.*;

public class Server {
    public final String configFilePath = "./conf/httpd.conf.txt";
    
    public void Start() throws IOException {
        Request request = new Request();
        ConfigReader configuration = ConfigReader.getInstance();
        ServerSocket socket = new ServerSocket( configuration.getDefaultPort() );
        System.out.println("Server running...");

        while( true ) {
            Socket client = socket.accept();
            request.parse(client);
            Response response = new Response();
            response.send(client.getOutputStream());
            client.close();
        }
    }
}