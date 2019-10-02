package core;

import core.response.*;
import java.net.*;
import java.io.*;

public class Server {
    public final String configFilePath = "./conf/httpd.conf.txt";
    
    public void Start() throws IOException {
        ConfigReader configuration = ConfigReader.getInstance();
        ServerSocket socket = new ServerSocket( configuration.getDefaultPort() );
        System.out.println("Server running...");

        while( true ) {
            Socket client = socket.accept();
            Request request = new Request();
            request.parse(client);
            Resource resource = new Resource(request);
            Response response = new ResponseBuilder(request, resource)
                .setStatusCode()
                .setReasonPhrase()
                .setBody()
                .setHeaders()
                .build();
            response.send(client.getOutputStream());
            client.close();
        }
    }
}