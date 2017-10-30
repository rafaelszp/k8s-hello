<%@ page import="java.net.Inet4Address" %><%--
  Created by IntelliJ IDEA.
  User: rafael
  Date: 8/18/17
  Time: 10:32 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Hello K8s</title>
  </head>
  <body>

      Hello k8s from <%= Inet4Address.getLocalHost().getHostName() %>

  </body>
</html>
