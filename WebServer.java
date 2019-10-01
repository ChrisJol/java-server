import core.*;

import java.net.*;
import java.io.*;

public class WebServer {
    public static void main(String[] args){
        try{
            Server server = new Server();
            server.Start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
