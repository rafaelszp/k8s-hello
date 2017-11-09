package szp.rafael.k8s.clock;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;



@Path("/clock")
public class ClockRest {

  @GET
  public Response get(@Context HttpServletRequest req) throws UnknownHostException {
	String ip = Inet4Address.getLocalHost().getHostAddress();
	LocalDateTime now = LocalDateTime.now();
	String info = "Clock from "+ip+". Current date and time is "+now;
	return Response.ok(info).build();
  }

}
