package core;

import java.net.*;
import java.io.*;
import java.util.*;
// import java.io.PrintWriter;

public class Request {
    String URI;
    String verb;
    String httpVersion;
    Map<String, String> headers = new HashMap<String, String>();
    StringBuilder body;

    public void parse(Socket client) {
        try{
            BufferedReader reader = new BufferedReader( new InputStreamReader(client.getInputStream()) );

            String[] request = reader.readLine().split(" "); //read in HTTP request
            verb = request[0];
            URI = request[1];
            httpVersion = request[2];

            String header = reader.readLine(); //read in headers
            while(header.length() > 0) {
                String[] lineHeader = header.split(": ");
                headers.put(lineHeader[0], lineHeader[1]);
                header = reader.readLine();
            }

            if(headers.get("Content-Length") != null){ //read in body
                String requestBody = reader.readLine();
                while (requestBody != null) {
                    body.append(requestBody).append("\r\n");
                    requestBody = reader.readLine();
                }
            }

            sendResponse(client); //this is a temporary method
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void sendResponse(Socket client) throws IOException{
        OutputStream outputStream = client.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        printWriter.println("HTTP/1.0 200 OK"); // version status_code reason_phrase
        printWriter.println("Content-Length: 12"); //headers ...
        printWriter.println(""); // blank line to indicate body is next (if content-length exists)
        printWriter.println("body content"); //body
        printWriter.flush(); //send
        
    }
}