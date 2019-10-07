package core;

import core.util.ConfigReader;
import java.io.File;

public class Resource{
    String URI;
    ConfigReader configuration;

    public Resource(Request request){
        this.URI = request.getURI();
        configuration = ConfigReader.getInstance();
        this.getResolvedFilePath();
    }

    public boolean isScript(){
        return configuration.getScriptAlias(URI) != null;
    }

    public boolean isAlias(){
        return configuration.getAlias(URI) != null;
    }

    private String getResolvedFilePath(){
        if(isScript()){
            URI = configuration.getScriptAlias(URI);
        }
        else if(isAlias()){
            URI = configuration.getAlias(URI);
        }
        else {
            URI = absolutePath();
        }

        File resolvedFile = new File( URI );

        if( resolvedFile.isDirectory() ) URI += "/" + configuration.getDirectoryIndex();

        return URI;
    }

    private String absolutePath(){
        String path = configuration.getDocRoot();
        String[] roots = URI.split("/");

        for(int i = 1; i < roots.length; i++){
            path += "/" + roots[i];
        }

        return path;
    }

    public String getURI(){
        return URI;
    }
}