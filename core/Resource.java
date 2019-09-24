package core;

public class Resource{
    String URI;
    ConfigReader configuration;

    Resource(String uri, ConfigReader configuration){
        this.URI = uri;
        this.configuration = configuration;
    }

    public String absolutePath(){
        return configuration.getDocRoot() + URI;
    }

    public boolean isScript(){
        
    }

    public boolean isProtected(){

    }
}