package core.util;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class MimeReader{
    private static MimeReader single_instance = null; //singleton instance
    File mimeTypeFile;
    Map<String, String> mimeTypes = new HashMap<String, String>();

    private MimeReader(){
        mimeTypeFile = new File("./conf/mime.types.txt");
        this.load();
    }

    public static MimeReader getInstance(){
        if(single_instance == null){
            single_instance = new MimeReader();
        }

        return single_instance;
    }

    private void load(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(mimeTypeFile));
            String type = reader.readLine();

            while(type != null) {
                if(!type.isEmpty() && Character.compare(type.charAt(0), '#') != 0){
                    String[] types = type.split("\t", 2);

                    String[] brokenTypes = types[0].split("/");

                    if(types.length == 1){
                        mimeTypes.put(brokenTypes[1], types[0]);
                    } 
                    else if(types.length > 1){
                        types[1].replace("\t", "");
                        String[] extensions = brokenTypes[1].split(" ");
                        for(int i = 0; i < extensions.length; i++){
                            mimeTypes.put(extensions[i], types[0]);
                        }
                    }
                }

                type = reader.readLine();
            }
            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found in classpath");
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getMimeType(String extension){
        return mimeTypes.get(extension);
    }
}