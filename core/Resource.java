package core;

import java.io.*;

public class Resource{
    String URI;
    ConfigReader configuration;
    boolean isScript = false;
    String resolvedFilePath;

    Resource(Request request){
        this.URI = request.URI;
        configuration = ConfigReader.getInstance();
        isScript();
        resolvedFilePath = getResolvedFilePath();
    }

    public String getResolvedFilePath(){
        URI = isScript ? configuration.getAlias(URI) : absolutePath();
        File resolvedFile = new File( URI );

        if( resolvedFile.isDirectory() ) URI += "/" + configuration.getDirectoryIndex();

        return URI;
    }

    private String absolutePath(){
        return  configuration.getDocRoot() + URI;
    }

    private void isScript(){
        if(configuration.getScriptAlias(URI) != null){
            isScript = true;
        }
    }
}