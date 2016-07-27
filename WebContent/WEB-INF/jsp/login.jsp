<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!-- Spring Form Tag Library -->    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
</head>
<body>
<h1>Log In</h1>
<h1>${message}</h1>
	<form:form action="verifyPassword.html" method="GET">
		<table>
			<tbody>
				<tr>
					<td><form:label path="email" name="email">Email:</form:label></td>
					<td><form:input path="email"></form:input></td>
					<td><form:errors path="email" cssClass="error"/></td>
				</tr>
				<tr>
					<td><form:label type="password" path="password" name ="password">Password:</form:label></td>
					<td><form:input path="password" type="password"></form:input></td>
					<td><form:errors path="password"  type="password" cssClass="error"/></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Submit"></td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>