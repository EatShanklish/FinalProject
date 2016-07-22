<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Jobs and Whatnot</title>

</head>
<body>

<h1>${message}</h1>

<form action ="welcome.html">
<input type="checkbox" name="sort" value="false">Sort by City
<input type="submit" value="Sort">
</form>


<div>
<h2>Jobs from Indeed</h2>
	
	<table>
	    <c:forEach items="${indeedArray}" var="job">
	        <tr>
	            <td><a href="<c:out value="${job.url}"/>" target="_blank"><c:out value="${job.jobTitle}"/></a><br></td>
	        </tr>
	        <tr>
	            <td><c:out value="${job.company}"/><br></td>
	        </tr>
	        <tr>
	            <td><c:out value="${job.location}"/><br></td>
	        </tr>
	        <tr>
	            <td><form><input type="submit" value="Bookmark Job"></form> <br></td> <!-- Need to include action="" which points to the right controller method which will save to DB -->
	        </tr>
	        <tr>
	            <td>--------------------------------------------<br></td>
	        </tr>
	        <tr>
	            <td><form><input type="submit" value="Bookmark Job"></form> <br></td>
	        </tr>
	    </c:forEach>
	</table>
	
	<h2>Jobs from USAJOBS</h2>
	
	<table>
	    <c:forEach items="${usaArray}" var="job">
	        <tr>
	            <td><a href="<c:out value="${job.url}"/>" target="_blank"><c:out value="${job.jobTitle}"/></a><br></td>
	        </tr>
	        <tr>
	            <td><c:out value="${job.company}"/><br></td>
	        </tr>
	        <tr>
	            <td><c:out value="${job.location}"/><br></td>
	        </tr>
	        <tr>
	            <td>--------------------------------------------<br></td>
	        </tr>
	    </c:forEach>
	</table>
	
	<h2>Jobs from Dice</h2>
		<table>
		    <c:forEach items="${array}" var="job">
		        <tr>
		            <td><a href="<c:out value="${job.url}"/>" target="_blank"><c:out value="${job.jobTitle}"/></a><br></td>
		        </tr>
		        <tr>
		            <td><c:out value="${job.company}"/><br></td>
		        </tr>
		        <tr>
		            <td><c:out value="${job.location}"/><br></td>
		        </tr>
		        <tr>
		            <td>--------------------------------------------<br></td>
		        </tr>
		    </c:forEach>
		</table>
	
	
</div>

</body>
</html>