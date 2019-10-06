package core;

import core.util.ConfigReader;
import core.util.AuthReader;
import core.util.MimeReader;
import core.response.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

public class Server {
    public final String configFilePath = "./conf/httpd.conf.txt";
    
    public void Start() throws IOException {
        ConfigReader configuration = ConfigReader.getInstance();
        ServerSocket socket = new ServerSocket( configuration.getDefaultPort() );
        System.out.println("Server running...");

        while( true ) {
            Socket client = socket.accept();

            //parse request
            Request request = new Request();
            request.parse(client);

            //check authorization
            Htpassword authCheck = new Htpassword(configuration.getAccessFile());
            authCheck.isAuthorized(request.getHeaders().get("Authorization"));

            //create resource
            Resource resource = new Resource(request);

            //send response
            Response response = new ResponseFactory(request, resource).build();
            response.send(client.getOutputStream());

            client.close();
        }
    }
}