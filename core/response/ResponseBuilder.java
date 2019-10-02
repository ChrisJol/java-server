package core.response;

import java.util.*;
import java.io.*;
import core.*;

public class ResponseBuilder{
    Request request;
    Resource resource;

    int statusCode;
    String reasonPhrase;
    Map<String, String> headers = new HashMap<String, String>();
    String body;

    public ResponseBuilder(Request request, Resource resource){
        this.request = request;
        this.resource = resource;
    }

    public ResponseBuilder setStatusCode(){
        return this;
    }

    public ResponseBuilder setReasonPhrase(){
        return this;
    }

    public ResponseBuilder setHeaders(){
        return this;
    }

    public ResponseBuilder setBody(){
        try{
            File resolvedFile = new File(resource.getResolvedFilePath());
            byte[] fileByteArray = new byte[(int) resolvedFile.length()];
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(resolvedFile));

            inputStream.read(fileByteArray, 0, fileByteArray.length);
            body = new String(fileByteArray, "UTF-8");
        }
        catch(FileNotFoundException e){
            System.out.println("Requested file does not exist: Response.java: 23");
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return this;
    }

    public Response build(){
        Response response = new Response();
        response.statusCode = statusCode;
        response.reasonPhrase = reasonPhrase;
        response.headers = headers;
        response.body = body;
        
        return response;
    }
}