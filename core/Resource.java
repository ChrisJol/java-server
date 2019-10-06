package core;

import core.util.ConfigReader;
import java.io.File;

public class Resource{
    String URI;
    ConfigReader configuration;

    Resource(Request request){
        this.URI = request.getURI();
        configuration = ConfigReader.getInstance();
        this.getResolvedFilePath();
    }

    private String getResolvedFilePath(){
        URI = isScript() ? configuration.getAlias(URI) : absolutePath();
        File resolvedFile = new File( URI );

        if( resolvedFile.isDirectory() ) URI += "/" + configuration.getDirectoryIndex();

        return URI;
    }

    private String absolutePath(){
        return  configuration.getDocRoot() + URI;
    }

    private boolean isScript(){
        return configuration.getScriptAlias(URI) != null;
    }

    public String getURI(){
        return URI;
    }
}