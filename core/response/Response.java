package core.response;

import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;

public class Response{
    int statusCode;
    String reasonPhrase;
    Map<String, String> headers = new HashMap<String, String>();
    String body;

    public void send(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(outputStream);

        printWriter.println("HTTP/1.0 " + statusCode + " " + reasonPhrase);

        for(Map.Entry<String, String> entry : headers.entrySet()) { // headers
            printWriter.println(entry.getKey() + ": " + entry.getValue());
        }

        printWriter.println(); // empty line
        printWriter.println(body); //body
        printWriter.flush(); //send
        printWriter.close();
    }
}