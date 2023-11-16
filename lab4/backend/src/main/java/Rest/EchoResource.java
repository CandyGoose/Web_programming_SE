package Rest;

import Rest.AuthFilter.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/echo")
public class EchoResource {
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public String echo(@QueryParam("text") String text) {
        return text.toUpperCase();
    }
}
