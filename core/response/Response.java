package core.response;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import core.util.Logger;

public class Response{
    int statusCode;
    String reasonPhrase;
    Map<String, String> headers = new HashMap<String, String>();
    String body;

    public void send(OutputStream outputStream){
        Logger.statusCode = statusCode;
        Logger.size = body.length();

        PrintWriter printWriter = new PrintWriter(outputStream);

        printWriter.println("HTTP/1.0 " + statusCode + " " + reasonPhrase);

        for(Map.Entry<String, String> entry : headers.entrySet()) { // headers
            printWriter.println(entry.getKey() + ": " + entry.getValue());
        }

        printWriter.println(); // empty line
        if(headers.containsKey("Content-Length")) printWriter.println(body); //body
        printWriter.flush(); //send
        printWriter.close();
    }
}