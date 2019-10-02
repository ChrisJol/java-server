package core;

import java.io.File;

public class Resource{
    String URI;
    ConfigReader configuration;

    Resource(Request request){
        this.URI = request.URI;
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
        return configuration.getScriptAlias(URI) != null;
    }
}