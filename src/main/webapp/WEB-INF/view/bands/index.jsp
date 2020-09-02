<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>

<!DOCTYPE html>
<html>
<head>
	<title>List of Bands</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Bands</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<input type="button" value="Add Band" 
				   onclick="window.location.href='bands/new'; return false;"
				   class="add-button" />
			<table>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Bio</th>
					<th>Location</th>
					<th>Genres</th>
					<th>Created at</th>
					<th>Updated at</th>
					<th>Owner</th>
					<th>Action</th>
				</tr>
				<c:forEach var="tempBand" items="${bands}">
					<c:url var="showLink" value="/bands/show">
						<c:param name="bandId" value="${tempBand.id}"></c:param>
					</c:url>
					<c:url var="updateLink" value="/bands/edit">
						<c:param name="bandId" value="${tempBand.id}"></c:param>
					</c:url>
					<tr>
						<td> <a href="${showLink}">${tempBand.getId()}</a> </td>
						<td> ${tempBand.getName()} </td>
						<td> ${tempBand.getBio()} </td>
						<td> ${tempBand.getLocation()} </td>
						<td> ${tempBand.getGenres()} </td>
						<td> ${tempBand.getCreated_at()} </td>
						<td> ${tempBand.getUpdated_at()} </td>
						<td> ${tempBand.getOwner().getName()} </td>
						<td>
							<a href="${updateLink}">Update</a>
							|
							<form action="bands/delete" method="POST">
								<input name="id" value="${tempBand.getId()}" type="hidden" />
								<input type="submit" value="Delete" class="delete" onclick="if(!(confirm('Are you sure you want to delete this band?'))) return false" />
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>

</html>