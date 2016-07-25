<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript" src="jquery-latest.js"></script> 
<script type="text/javascript" src="jquery.tablesorter.js"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Jobs and Whatnot</title>

</head>
<body>

<h1>${message}</h1>

<!-- <form action ="welcome.html"> -->
<!-- <input type="checkbox" name="sort" value="false">Sort by City -->
<!-- <input type="submit" value="Sort"> -->
<!-- </form> -->



<!-- <h2>Jobs from Indeed</h2> -->

<!-- 	<table> -->
<!-- 	<thead>  -->
<!-- <tr>  -->
<!--     <th>JobTitle</th>  -->
<!--     <th>Company</th>  -->
<!--     <th>Location</th>  -->
<!--     <th>Bookmark?</th>  -->
<!-- </tr>  -->
<!-- </thead> -->
<%-- 	    <c:forEach items="${indeedArray}" var="job"> --%>

<!-- 	        <tr> -->

<%-- 	            <td><a href="<c:out value="${job.url}"/>" target="_blank"><c:out value="${job.jobTitle}"/></a></td> --%>
<%-- 	            <td><c:out value="${job.company}"/></td> --%>
<%-- 	            <td><c:out value="${job.location}"/></td> --%>
<%-- 	            <td><button><a href='bookmarkJob.html?url="${job.url}"'>Bookmark Job</a></button></td> --%>

<!-- 	        </tr> -->
<%-- 	    </c:forEach> --%>
<!-- 	</table> -->

	
	
	<h2>Jobs from Dice</h2>
	
		<table>
			<thead> 
<tr> 
    <th>JobTitle</th> 
    <th>Company</th> 
    <th>Location</th> 
    <th> Search Engine</th>
    <th>Bookmark?</th> 
    
</tr> 
</thead>
		    <c:forEach items="${array}" var="job">
		        <tr>
		            <td><a href="<c:out value="${job.url}"/>" target="_blank"><c:out value="${job.jobTitle}"/></a></td>
		            <td><c:out value="${job.company}"/></td>
		            <td><c:out value="${job.location}"/></td>
		            <td><c:out value="${job.engine}"/></td>
	          	  <td><button><a href='bookmarkJob.html?url="${job.url}"'>Bookmark Job</a></button></td> 

		        </tr>
		    </c:forEach>
		</table>
	
	


</body>
</html>