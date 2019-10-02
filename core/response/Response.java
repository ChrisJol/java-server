package core.response;

import java.io.*;
import java.util.*;

public class Response{
    int statusCode;
    String reasonPhrase;
    Map<String, String> headers = new HashMap<String, String>();
    String body;

    public void send(OutputStream outputStream){
        // try{
            PrintWriter printWriter = new PrintWriter(outputStream);

            printWriter.println("HTTP/1.0 " + statusCode + " " + reasonPhrase); // version status_code reason_phrase

            for(Map.Entry<String, String> entry : headers.entrySet()) { // headers
                printWriter.println(entry.getKey() + ": " + entry.getValue());
            }

            printWriter.println(); // empty line
            printWriter.println(body); //body

            printWriter.flush(); //send
            printWriter.close();
        // }
        // catch(FileNotFoundException e){
        //     System.out.println("Requested file does not exist: Response.java: 23");
        // }
        // catch(IOException e){
        //     e.printStackTrace();
        // }
    }
}