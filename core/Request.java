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

            //this.verb = httpRequest[0];

            requests(httpRequest);
                                
         }
    }

    private static void requests(String[] param) {
        switch (param) {
            case "GET":
                System.out.println("This is the GET request");
                System.out.println("Retrieve data from: " + param[1]);
                break;
            case "HEAD":
                System.out.println("This is the HEAD request");
                break;
            case "POST":
                System.out.println("This is the POST request");
                break;
            case "PUT":
                System.out.println("This is the PUT request");
                break;
            case "DELETE":
                System.out.println("This is the DELETE request");
                break;                                                  
            default:
                break;
        }

    }
}