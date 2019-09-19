package public_html;

import java.net.*;
import java.io.*;

public class ParseHTTP {

    public static void outputRequest(Socket client) throws IOException {
        String line;
        System.out.println("got this far");
        BufferedReader reader = new BufferedReader( new InputStreamReader(client.getInputStream()) );

        while(true) {
            line = reader.readLine();
            System.out.println("> " + line);

            if(line.contains("END")) {
                break;
            }
        }
//        outputLineBreak();
    }
}