<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
	<title>List of Artist</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Artists</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<input type="button" value="Add Artist" 
				   onclick="window.location.href='artists/new'; return false;"
				   class="add-button" />
			<c:url var="searchUrl"  value="/artists/search" />
			<form:form action="${searchUrl}" method="GET">
				<input name="search" placeholder="Search..." />
			</form:form>
			<table>
				<tr>
					<th>Id</th>
					<th>Username</th>
					<th>Name</th>
					<th>Bio</th>
					<th>Location</th>
					<th>Instruments</th>
					<th>Created at</th>
					<th>Updated at</th>
					<th>Action</th>
				</tr>
				<c:forEach var="tempArtist" items="${artists}">
					<c:url var="showUrl" value="/artists/show">
						<c:param name="artistId" value="${tempArtist.id}"></c:param>
					</c:url>
					<c:url var="updateUrl" value="/artists/edit">
						<c:param name="artistId" value="${tempArtist.id}"></c:param>
					</c:url>
					<tr>
						<td> <a href="${showUrl}">${tempArtist.getId()}</a> </td>
						<td> ${tempArtist.getUsername()} </td>
						<td> ${tempArtist.getName()} </td>
						<td> ${tempArtist.getBio()} </td>
						<td> ${tempArtist.getLocation()} </td>
						<td> ${tempArtist.getInstruments()} </td>
						<td> ${tempArtist.getCreated_at()} </td>
						<td> ${tempArtist.getUpdated_at()} </td>
						<td>
							<a href="${updateUrl}">Update</a>
							|
							<c:url var="deleteUrl"  value="/artists/delete" />
							<form action="${deleteUrl}" method="POST">
								<input name="id" value="${tempArtist.getId()}" type="hidden" />
								<input type="submit" value="Delete" class="delete" onclick="if(!(confirm('Are you sure you want to delete this artist?'))) return false" />
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>

</html>