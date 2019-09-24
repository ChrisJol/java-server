package core;

import java.net.*;
import java.io.*;
// import java.io.PrintWriter;

public class Request {
    String uri;
    String verb;
    String httpVersion;

    public void parse(Socket client) {
        String line;

        try{
            BufferedReader reader = new BufferedReader( new InputStreamReader(client.getInputStream()) );

            while(true) {
                line = reader.readLine();
                // String[] httpRequest = line.split(" ");
                System.out.println(line);

                sendResponse(client); //this is a temporary method
            }
        }catch(IOException e){
            System.out.println("IOException, most likely the request was read in and readLine() threw and error when it reached the end of the request, we should try to find a solution for this");
            // System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }

    private void sendResponse(Socket client) throws IOException{
        OutputStream outputStream = client.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        printWriter.println("HTTP/1.0 200 OK");
        printWriter.println("Content-Length: 12");
        printWriter.println("");
        printWriter.println("body content");
        printWriter.flush();
        
    }
}