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
            Resource resource = new Resource(request);
            ResponseBuilder responseBuilder = new ResponseBuilder(request, resource);
            Response response = responseBuilder.build();
            response.send(client.getOutputStream());
            client.close();
        }
    }
}