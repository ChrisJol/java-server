package core;

public class Resource{
    String URI;

    Resource(String uri){
        this.URI = uri;
        // System.out.println(docRoot);
    }

    public String absolutePath(){
        return URI;
    }

    public boolean isScript(){
        return false;
    }

    public boolean isProtected(){
        return false;
    }
}