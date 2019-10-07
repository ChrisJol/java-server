package core.response;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import core.Request;
import core.Resource;
import core.Htpassword;
import core.util.*;
import core.response.*;

public class ResponseFactory{
    Request request;
    Resource resource;
    ConfigReader configuration;
    MimeReader mimeTypes;
    int statusCode;
    int contentLength;
    String reasonPhrase;
    String body;
    Map<String, String> headers = new HashMap<String, String>();

    public ResponseFactory(Request request, Resource resource){
        this.request = request;
        this.resource = resource;
        configuration = ConfigReader.getInstance();
        mimeTypes = MimeReader.getInstance();

        String date = new SimpleDateFormat("EEE, d MMM yyy HH:mm:ss z").format(new Date());
        headers.put("Date", date);
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
            statusCode = 400;
            reasonPhrase = "Bad Request";
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
            statusCode = 404;
            reasonPhrase = "Not Found";
        }
        catch(IOException e){
            statusCode = 400;
            reasonPhrase = "Bad Request";
        }
    }

    private void GET(){
        File file = new File(resource.getURI());

        String lastModifiedHeader = request.getHeaders().get("Last-Modified");
        String lastModifiedFile = new SimpleDateFormat("EEE, d MMM yyy HH:mm:ss z").format(new Date(file.lastModified()));

        if(lastModifiedFile.equals(lastModifiedHeader)){
            headers.put("Last-Modified", lastModifiedFile);
            statusCode = 304;
            reasonPhrase = "Not Modified";
        }
        else{
            POST();
        }
    }

    private void HEAD(){
        File resolvedFile = new File(resource.getURI());
        String[] fileNameArray = resolvedFile.getName().split("\\.");
        headers.put("Content-Type", mimeTypes.getMimeType(fileNameArray[1]));
        headers.put("Content-Length", String.valueOf(resolvedFile.length()));

        statusCode = 200;
        reasonPhrase = "OK";
    }

    public Response build(){
        //check authorization
        Htpassword authCheck = new Htpassword(resource.getURI());
        if(authCheck.isAuthorized(request.getHeaders().get("Authorization"))){
            switch(request.getVerb()){
                case "PUT": PUT(); break;
                case "DELETE": DELETE(); break;
                case "POST": POST(); break;
                case "GET": GET(); break;
                case "HEAD": HEAD(); break;
            }
        }
        else{
            statusCode = 401;
            reasonPhrase = "Unauthorized";
        }

        Response response = new Response();
        response.statusCode = statusCode;
        response.reasonPhrase = reasonPhrase;
        response.headers = headers;
        response.body = body;

        return response;
    }
}