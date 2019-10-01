package core;

import java.io.*;

public class Resource{
    String URI;
    ConfigReader configuration;

    Resource(String uri){
        this.URI = uri;
        configuration = ConfigReader.getInstance();
    }

    public String getResolvedFilePath(){
        URI = isScript() ? configuration.getAlias(URI) : absolutePath();
        File resolvedFile = new File( URI );

        if( resolvedFile.isDirectory() ) URI += "/" + configuration.getDirectoryIndex();

        return URI;
    }

    private String absolutePath(){
        return  configuration.getDocRoot() + URI;
    }

    private boolean isScript(){
        return configuration.getAlias(URI) != null;
    }
}