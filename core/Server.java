package core;

import core.util.*;
import core.response.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.Date;

public class Server {
    public final String configFilePath = "./conf/httpd.conf.txt";
    
    public void Start() throws IOException {
        ConfigReader configuration = ConfigReader.getInstance();
        ServerSocket socket = new ServerSocket( configuration.getDefaultPort() );
        System.out.println("Server running...");

        while( true ) {
            Socket client = socket.accept();

            //Start Log
            Logger log = new Logger();

            //parse request
            Request request = new Request();
            request.parse(client);

            //create resource
            Resource resource = new Resource(request);

            //send response
            Response response = new ResponseFactory(request, resource).build();
            response.send(client.getOutputStream());

            Logger.timeOfCompletion = new Date();
            log.log();

            client.close();
        }
    }
}