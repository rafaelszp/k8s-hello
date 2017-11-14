package szp.rafael.k8s.clock;

import org.jboss.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/high-load")
public class HighLoad extends HttpServlet{

  Logger logger = Logger.getLogger(this.getClass().getName());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.getWriter().write("performing a high load");
	long start = System.nanoTime();
	doSomeLoad();

  }


  public void doSomeLoad()
  {
	for (int i=0; i < 5; i++)
	{
	  new Thread(() -> {
		System.out.println("Thread " +Thread.currentThread().getName() + " started");
		double val=10;
		for (;;)
		{
		  Math.atan(Math.sqrt(Math.pow(val, 10)));
		}
	  }).start();
	}
  }

}
