package szp.rafael.k8s.clock;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/health")
public class Health {

  public static short WEAKEN=0;

  @GET
  public Response get(){
    if(WEAKEN ==1){
      return Response.serverError().header("WEAKEN", WEAKEN).build();
    }
    return Response.ok().build();
  }

  @GET
  @Path("/weaken/{healthy:[0-1]}")
  public Response weaken(@PathParam("healthy")Integer healthy){
    WEAKEN =healthy.shortValue();
    return Response.ok().build();
  }
}
