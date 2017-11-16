package szp.rafael.k8s.clock;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;



@Path("/clock")
public class ClockRest {

  public static short ENABLED=1;

  @GET
  public Response get(@Context HttpServletRequest req) throws UnknownHostException {
    if(ENABLED==1){
	  String ip = Inet4Address.getLocalHost().getHostAddress();
	  LocalDateTime now = LocalDateTime.now();
	  String info = "Clock from "+ip+". Current date and time is "+now;
	  return Response.ok(info).build();
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("ENABLED",ENABLED).build();
  }

  @GET
  @Path("/enable/{enable: [0-1]}")
  public Response enableDisable(@PathParam("enable")Integer enable){
    ENABLED=enable.shortValue();
    return Response.ok().build();
  }

}
