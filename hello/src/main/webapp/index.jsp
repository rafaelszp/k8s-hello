<%@ page import="java.net.Inet4Address" %>
<%@ page import="com.mashape.unirest.http.Unirest" %>
<%@ page import="com.mashape.unirest.http.HttpResponse" %>
<%@ page import="szp.rafael.k8s.SessionInfo" %><%--
  Created by IntelliJ IDEA.
  User: rafael<github.com/rafaelszp>
  Date: 8/18/17
  Time: 10:32 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p>Hello k8s from <%= Inet4Address.getLocalHost().getHostName() %> : <%=Inet4Address.getLocalHost().getHostAddress()%></p>
<p>Running from <%= System.getenv("NODE_NAME") %> node and on <%=System.getProperty("POD_NAMESPACE","N/A") %> namespace</p>
<p>
<%
    String linkerdURL = System.getenv("LOOKUP_URL");
    if(linkerdURL ==null|| linkerdURL.trim().length()==0){
      linkerdURL = "http://localhost:9090";
    }else{
      linkerdURL = "http://"+ linkerdURL +":80";
    }
    String clockURL = linkerdURL +"/clock-service/api/clock";

    HttpResponse<String> resp = Unirest.get(clockURL).header("Host","k8s.hello").asString();
    out.print(resp.getBody());

    SessionInfo.handleSession(session);
    out.print(String.format("\n<p>requestCount: %s </p> \n<p>Session createdAt: %s</p>",
            session.getAttribute("requestCount"),session.getAttribute("createdAt")));

%>
</p>
