<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="com.Shanklish.Controller.FinalController"%>  
<%@ page import="com.Shanklish.Controller.User"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Users</title>

  <link href="jquery-ui/jquery-ui.css" rel="stylesheet">

    <script type="text/javascript" src="jquery.min.js"></script>
    <!-- <script src="jquery-ui/external/jquery/jquery.js"></script> -->
    <script src="jquery-ui/jquery-ui.js"></script>


</head>
<body>
<h1>Our Users Include:</h1>

<table>
<tbody>
<%  
List<User> users = FinalController.getAllUsers(); 

for (User u: users) {
	out.println ("<tr><td>" + "</td><td>" +
		u.getPassword() + "</td><td>" + u.getEmail() 
		+ "</td><td>" + "</td></tr>");
}
%>  
</tbody>
</table>


</body>
</html>