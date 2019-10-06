package core;

import core.util.ConfigReader;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

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
                String[] lineHeaders = header.split(": ");
                headers.put(lineHeaders[0], lineHeaders[1]);
                header = reader.readLine();
            }

            if(headers.get("Content-Length") != null){ //read in body
                String requestBody = reader.readLine();
                while (requestBody != null) {
                    body.append(requestBody).append("\r\n");
                    requestBody = reader.readLine();
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public String executeRequest(String request){
        //TODO: make into switch statement, possibly move into another class
        if(request.equals("PUT")){
            System.out.println("PUT was accessed");
            return configuration.getDocRoot()+URI;
        } else if(request.equals("GET")){
            System.out.println("GET was accessed");
        } else if(request.equals("DELETE")){
            System.out.println("DELETE was accessed");
            return configuration.getDocRoot()+URI;
        }
        else {
            return "";
        }
        return "";
    }
}