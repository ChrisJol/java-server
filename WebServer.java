import htmlpackage.Server;
import java.net.*;
import java.io.*;

public class WebServer {
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        server.Start();
    }
}
