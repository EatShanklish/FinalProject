<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign Up</title>
</head>
<body>
<h1>Sign Up</h1>

	<form:form action="create.html" method="GET">
		<table>
			<tbody>
				<tr>
					<td><form:label path="email" name ="email" >Username:</form:label></td>
					<td><form:input path="email"></form:input></td>
					<td><form:errors path="email" cssClass="error"/></td>
				</tr>
				<tr>
					<td><form:label path="password" name="password">Password:</form:label></td>
					<td><form:input path="password"></form:input></td>
					<td><form:errors path="password" cssClass="error"/></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Submit"></td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>