package core;

import core.util.ConfigReader;
import core.util.Logger;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Request {
    private String URI;
    private String verb;
    private String httpVersion;
    private Map<String, String> headers = new HashMap<String, String>();
    private String body;

    public String getURI(){
        return URI;
    }
    
    public String getVerb(){
        return verb;
    }

    public Map<String, String> getHeaders(){
        return headers;
    }

    public String getBody(){
        return body;
    }

    public void parse(Socket client) {
        try{
            BufferedReader reader = new BufferedReader( new InputStreamReader(client.getInputStream()) );

            String request = reader.readLine(); //read in HTTP request
            Logger.request = request; //log
            String[] requests = request.split(" ");
            verb = requests[0];
            URI = requests[1];
            httpVersion = requests[2];

            String header = reader.readLine(); //read in headers
            while(header.length() > 0) {
                String[] lineHeaders = header.split(": ");
                headers.put(lineHeaders[0], lineHeaders[1]);
                header = reader.readLine();
            }

            if(headers.containsKey("Content-Length")){ //read in body
                int length = Integer.parseInt(headers.get("Content-Length"));
                char[] bodyBuffer = new char[length];
                reader.read(bodyBuffer, 0, length);
                body = String.valueOf(bodyBuffer);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}