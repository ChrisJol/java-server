package core;

import java.io.*;

public class Resource{
    String URI;
    ConfigReader configuration;
    File resolvedFile;

    Resource(String uri){
        this.URI = uri;
        configuration = ConfigReader.getInstance();
    }

    private String load(){
        if(isScript()){
            URI = configuration.getAlias(URI);
        } else{
            URI = absolutePath();
        }

        resolvedFile = new File(URI);
        
        if(resolvedFile.isFile()){
            return URI;
        } else{
            return URI + configuration.getDirectoryIndex();
        }
    }

    public String absolutePath(){
        return  configuration.getDocRoot() + URI;
    }

    private boolean isScript(){
        return configuration.getAlias(URI) != null;
    }

    public boolean isProtected(){
        return false;
    }
}