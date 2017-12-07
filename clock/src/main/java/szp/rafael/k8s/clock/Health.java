package szp.rafael.k8s.clock;

import szp.rafael.k8s.status.Status;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;


@Path("/health")
public class Health {

  public static short WEAKEN=0;


  @GET
  public Response get() throws ExecutionException, InterruptedException {
    if(WEAKEN ==1){
      return Response.serverError().header("WEAKEN", WEAKEN).build();
    }

    return Response.ok().build();
  }

  @GET
  @Path("/status")
  @Produces(value = {MediaType.APPLICATION_JSON})
  public Response status() throws ExecutionException, InterruptedException {
    return Response.ok(Status.get().toString()).build();
  }

  @GET
  @Path("/weaken/{healthy:[0-1]}")
  public Response weaken(@PathParam("healthy")Integer healthy){
    WEAKEN =healthy.shortValue();
    return Response.ok().build();
  }
}
