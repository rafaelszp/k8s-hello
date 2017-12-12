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
  public static short SLOW_REQUEST_ENABLED=0;

  @GET
  public Response get(@Context HttpServletRequest req) throws Exception {
    if(ENABLED==1){
      String version = "x.y.z";
	  try {
		InputStream file = ClockRest.class.getResourceAsStream("/app.properties");
		Properties props = new Properties();
		props.load(file);
		version = props.get("project.version").toString();
	  } catch (IOException e) {
		e.printStackTrace();
	  }
	  String ip = Inet4Address.getLocalHost().getHostAddress();
	  LocalDateTime now = LocalDateTime.now();
	  String info = "Clock v"+version+" from "+ip+". Current date and time is "+now;
	  if(SLOW_REQUEST_ENABLED==1){
  		//Pause for 8mins
  		Thread.sleep(8*60_000);
  	  }
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

  @GET
  @Path("/slowrequest/enable/{enable:[0-1]}")
  public Response enableDisableLongRequest(@PathParam("enable")Integer enable){
  	SLOW_REQUEST_ENABLED=enable.shortValue();
  	return Response.ok().build();	
  }

}
