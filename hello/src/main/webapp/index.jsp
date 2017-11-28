<%@ page import="java.net.Inet4Address" %>
<%@ page import="com.mashape.unirest.http.Unirest" %>
<%@ page import="com.mashape.unirest.http.HttpResponse" %><%--
  Created by IntelliJ IDEA.
  User: rafael
  Date: 8/18/17
  Time: 10:32 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p>Hello k8s from <%= Inet4Address.getLocalHost().getHostName() %> : <%=Inet4Address.getLocalHost().getHostAddress()%></p>
<p>
<%
    String node = System.getenv("NODE_NAME");
    if(node==null||node.trim().length()==0){
      node = "http://localhost:9090";
    }else{
      node = "http://"+node+":80";
    }
    String clockURL = node+"/clock-service/api/clock";
    String sessionInfoURL = node+"/clock-service/session-info";
    HttpResponse<String> resp = Unirest.get(clockURL).header("Host","k8s.hello").asString();
    HttpResponse<String> resp2 = Unirest.get(sessionInfoURL).header("Host","k8s.hello").asString();
    out.print(resp.getBody()+resp2.getBody());

%>
</p>
