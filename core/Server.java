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
            Request request = new Request();
            request.parse(client);

            Htpassword authCheck = new Htpassword(configuration.getAccessFile());
            authCheck.isAuthorized(request.headers.get("Authorization"));

            Resource resource = new Resource(request);

            Operations op = new Operations();
            op.PUT("/Users/chris/Desktop/fall-2019-web-server-bernardo_jol/public_html/blah.html");

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