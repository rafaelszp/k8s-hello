package szp.rafael.k8s;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(value = "/session-info")
public class SessionInfo extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	HttpSession session = request.getSession();
	handleSession(session);
	response.getWriter().write("<p>Session CreatedAt: "+session.getAttribute("createdAt"));
	response.getWriter().write("<p>Session requestCount: "+session.getAttribute("requestCount"));

  }

  public static void handleSession(HttpSession session){
	if(session.getAttribute("requestCount")==null){
	  session.setAttribute("createdAt",LocalDateTime.now());
	  session.setAttribute("requestCount",1L);
	}else{
	  Long requestCount = (Long) session.getAttribute("requestCount");
	  ++requestCount;
	  session.setAttribute("requestCount",requestCount);
	}
  }
}
