<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="jquery-3.1.0.min.js"></script>
<script src="notify.js"></script>
<script src="bootstrapJS/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery-latest.js"></script>
<script type="text/javascript" src="jquery.tablesorter.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Need a Job?</title>
<script>
function addBullet( addUrl,  addTitle) 
{
	  var ul = document.getElementById("BMlist");
 	  var li = document.createElement("li");
 	  var newlink = document.createElement("a");
 	  var linktext = document.createTextNode(addTitle);
 	  newlink.appendChild(linktext);
 	  newlink.href=addUrl;
 	  
 	  li.appendChild(newlink);
 	  ul.appendChild(li);
	  
	  return true;
	}
</script>
</head>
<body>

<div class="container">
  <div class="dropdown">
    <button class="btn btn-default dropdown-toggle" type="button" id="menu1" data-toggle="dropdown">Saved Jobs
    <span class="caret"></span></button>
    <ul id="BMlist" class="dropdown-menu" role="menu" aria-labelledby="menu1">
    
    <c:forEach items="${bookmarkArray}" var="job">
      <li><a href="${job.url}" target="_blank">${job.jobTitle}</a></li>
      </c:forEach>
      
    </ul>
  </div>
</div>

<iframe name="resultframe" width="1" height="1" ></iframe>

	<h1>${message}</h1>
	
	

	<table id="myTable" class="tablesorter">
		<thead>
			<tr>
				<th>JobTitle</th>
				<th>Company</th>
				<th>Location</th>
				<th>Search Engine</th>
				<th>Bookmark</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${array}" var="job">
				<tr>
					<td><a href="${job.url}" target="_blank"><c:out value="${job.jobTitle}" /></a></td>
					<td><c:out value="${job.company}" /></td>
					<td><c:out value="${job.location}" /></td>
					<td><c:out value="${job.engine}" /></td>
					<td><button><a href='bookmarkJob.html?url=${job.url}&title=${job.jobTitle}' target="resultframe" onclick="return addBullet('${job.url}', '${job.jobTitle}')">Bookmark Job</a></button></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>


	<script>
		$(document).ready(function() {
			$("#myTable").tablesorter();
		});
	</script>

</body>
</html>