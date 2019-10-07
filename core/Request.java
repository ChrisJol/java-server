package core;

import core.util.ConfigReader;
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
    ConfigReader configuration;

    public Request(){
        configuration = ConfigReader.getInstance();
    }

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