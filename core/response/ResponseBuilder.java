package core.response;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import core.Request;
import core.Resource;

public class ResponseBuilder{
    Request request;
    Resource resource;

    int statusCode;
    String reasonPhrase;
    Map<String, String> headers = new HashMap<String, String>();
    String body;
    int contentLength;

    public ResponseBuilder(Request request, Resource resource){
        this.request = request;
        this.resource = resource;
    }

    public ResponseBuilder setStatusCode(){
        statusCode = 200;
        return this;
    }

    public ResponseBuilder setReasonPhrase(){
        reasonPhrase = "OK";
        return this;
    }

    public ResponseBuilder setHeaders(){
        headers.put("Content-Type", "image/png");
        headers.put("Content-Length", String.valueOf(contentLength));
        // headers.put("Content-Disposition", "inline");
        return this;
    }

    public ResponseBuilder setBody(){
        try{
            File resolvedFile = new File(resource.getResolvedFilePath());
            byte[] fileByteArray = new byte[(int) resolvedFile.length()];
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(resolvedFile));

            inputStream.read(fileByteArray, 0, fileByteArray.length);
            // body = fileByteArray;
            body = new String(fileByteArray, "UTF-8");
            contentLength = fileByteArray.length;
        }
        catch(FileNotFoundException e){
            System.out.println("ResponseBuilder.java: Requested file does not exist: " + resource.getResolvedFilePath());
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