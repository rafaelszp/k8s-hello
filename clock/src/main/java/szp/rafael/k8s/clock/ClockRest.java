package szp.rafael.k8s.clock;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Properties;


@Path("/clock")
public class ClockRest {

  public static short ENABLED=1;

  @GET
  public Response get(@Context HttpServletRequest req) throws UnknownHostException {
    if(ENABLED==1){
      String version = "x.y.z";
	  try {
		InputStream file = ClockRest.class.getResourceAsStream("/app.properties");
		System.out.println(file);
		Properties props = new Properties();
		props.load(file);
		version = props.get("project.version").toString();
	  } catch (IOException e) {
		e.printStackTrace();
	  }
	  String ip = Inet4Address.getLocalHost().getHostAddress();
	  LocalDateTime now = LocalDateTime.now();
	  String info = "Clock v"+version+" from "+ip+". Current date and time is "+now;
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
