package core.response;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import core.Request;
import core.Resource;

public class ResponseFactory{
    Request request;
    Resource resource;

    int statusCode;
    String reasonPhrase;
    Map<String, String> headers = new HashMap<String, String>();
    String body;
    int contentLength;

    public ResponseFactory(Request request, Resource resource){
        this.request = request;
        this.resource = resource;
    }

    private void PUT(String filePath){

        try{
            File newFile = new File(filePath);

            if (newFile.createNewFile()) {
                System.out.println("File created!");
            } else {
                System.out.println("File already exists.");
            }

            FileWriter writer = new FileWriter(newFile.getName());
            // writer.write("I'm writing to a file");
            writer.close();

        //     File newFile = new File(filePath);
        //     if(newFile.createNewFile()){
        //         BufferedWriter fileOut = new BufferedWriter(new FileWriter(newFile.getName()));

        //         return "201";
        //     }
        //     else{
        //         return "500";
        //     }
        }
        catch(IOException e){
            System.out.println("Operations.java: File not created");
        }
    }

    public ResponseFactory setBody(){
        try{
            File resolvedFile = new File(resource.getURI());
            byte[] fileByteArray = new byte[(int) resolvedFile.length()];
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(resolvedFile));

            inputStream.read(fileByteArray, 0, fileByteArray.length);
            body = new String(fileByteArray, "UTF-8");
            contentLength = fileByteArray.length;

            inputStream.close();
        }
        catch(FileNotFoundException e){
            System.out.println("ResponseBuilder.java: Requested file does not exist: " + resource.getURI());
            e.printStackTrace();
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