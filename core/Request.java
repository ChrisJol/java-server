package core;

import java.net.*;
import java.io.*;

public class Request {
    String uri;
    String verb;
    String httpVersion;

    public static void parse(Socket client) throws IOException {
        String line;
        BufferedReader reader = new BufferedReader( new InputStreamReader(client.getInputStream()) );

        while(true) {
            line = reader.readLine();
            String[] httpRequest = line.split(" ");

            for(int i = 0; i < httpRequest.length; i++){
                System.out.println("This is what we're looking for: " + httpRequest[i]);
            }

            // System.out.println("> " + line);

            if(line.contains("END")) {
                break;
            }
        }
    }
}