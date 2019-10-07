package core.response;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import core.Request;
import core.Resource;
import core.util.ConfigReader;
import core.util.MimeReader;
import core.response.*;

public class ResponseFactory{
    Request request;
    Resource resource;
    ConfigReader configuration;
    MimeReader mimeTypes;

    int statusCode;
    String reasonPhrase;
    Map<String, String> headers = new HashMap<String, String>();
    String body;
    int contentLength;

    public ResponseFactory(Request request, Resource resource){
        this.request = request;
        this.resource = resource;
        configuration = ConfigReader.getInstance();
        mimeTypes = MimeReader.getInstance();

        headers.put("Date", new Date().toString());
        headers.put("Server", "Bernardo_Jol");
    }

    private void PUT(){
        body = request.getBody();

        try{
            File newFile = new File(configuration.getDocRoot() + request.getURI());

            if(newFile.createNewFile()){
                FileOutputStream outputStream = new FileOutputStream(newFile);
                byte[] strToBytes = body.getBytes();
                outputStream.write(strToBytes);
                outputStream.close();

                statusCode = 201;
                reasonPhrase = "Created";
            }
            else{
                statusCode = 403;
                reasonPhrase = "Forbidden";
            }
        }
        catch(IOException e){
            System.out.println("Operations.java: File not created");
        }
    }

    private void DELETE(){
        File fileToDelete = new File(configuration.getDocRoot() + request.getURI());
        if(fileToDelete.delete()){
            statusCode = 204;
            reasonPhrase = "No Content";
        }
        else {
            statusCode = 404;
            reasonPhrase = "Not Found";
        }
    }

    private void POST(){
        try{
            File resolvedFile = new File(resource.getURI());
            byte[] fileByteArray = new byte[(int) resolvedFile.length()];
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(resolvedFile));

            inputStream.read(fileByteArray, 0, fileByteArray.length);
            body = new String(fileByteArray, "UTF-8");

            String[] fileNameArray = resolvedFile.getName().split("\\.");

            headers.put("Content-Type", mimeTypes.getMimeType(fileNameArray[1]));
            headers.put("Content-Length", String.valueOf(fileByteArray.length));

            statusCode = 200;
            reasonPhrase = "OK";

            inputStream.close();
        }
        catch(FileNotFoundException e){
            System.out.println("ResponseBuilder.java: Requested file does not exist: " + resource.getURI());

            statusCode = 404;
            reasonPhrase = "Not Found";
        }
        catch(IOException e){
            e.printStackTrace();

            statusCode = 404;
            reasonPhrase = "Not Found";
        }
    }

    public Response build(){
        switch(request.getVerb()){
            case "PUT": PUT(); break;
            case "DELETE": DELETE(); break;
            case "POST": POST(); break;
        }

        Response response = new Response();
        response.statusCode = statusCode;
        response.reasonPhrase = reasonPhrase;
        response.headers = headers;
        response.body = body;

        return response;
    }
}