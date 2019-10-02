package core.response;

public class ResponseBuilder{
    Request request;
    Resource resource;

    int statusCode;
    String reasonPhrase;
    Map<String, String> headers = new HashMap<String, String>();
    String body;

    ResponseBuilder(Request request, Resource resource){
        this.request = request;
        this.resource = resource;
    }

    public ResponseBuilder setStatusCode(){

    }

    public ResponseBuilder setReasonPhrase(){

    }

    public ResponseBuilder setHeaders(){

    }

    public ResponseBuilder setBody(){

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