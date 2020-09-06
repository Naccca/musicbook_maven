<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored = "false" %>

<!DOCTYPE html>
<html>
<head>
	<title>${band.getName()} - Band</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>${band.getName()}</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<p>Id: ${band.getId()}</p>
			<p>Name: ${band.getName()}</p>
			<p>Bio: ${band.getBio()}</p>
			<p>Location: ${band.getLocation()}</p>
			<p>Genres: ${band.getGenres()}</p>
			<p>Created at: ${band.getCreated_at()}</p>
			<p>Updated at: ${band.getUpdated_at()}</p>
			<c:url var="ownerLink" value="/artists/show">
				<c:param name="artistId" value="${band.getOwner().getId()}"></c:param>
			</c:url>
			<p>Owner: <a href="${ownerLink}">${band.getOwner().getName()}</a></p>
			<p>
				<a href="${pageContext.request.contextPath}/bands">Back to List</a>
			</p>
			
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
					<th>State id </th>
				</tr>
				<c:forEach var="membership" items="${memberships}">
					<c:url var="showUrl" value="/artists/show">
						<c:param name="artistId" value="${membership.getArtist().getId()}"></c:param>
					</c:url>
					<tr>
						<td> <a href="${showUrl}">${membership.getArtist().getId()}</a> </td>
						<td> ${membership.getArtist().getUsername()} </td>
						<td> ${membership.getArtist().getName()} </td>
						<td> ${membership.getArtist().getBio()} </td>
						<td> ${membership.getArtist().getLocation()} </td>
						<td> ${membership.getArtist().getInstruments()} </td>
						<td> ${membership.getArtist().getCreated_at()} </td>
						<td> ${membership.getArtist().getUpdated_at()} </td>
						<td> ${membership.getState_id()} </td>
					</tr>
				</c:forEach>
			</table>
			
			<c:if test="${not empty pageContext.request.userPrincipal}">
				<c:if test="${pageContext.request.userPrincipal.name == band.getOwner().getUsername()}">
					<c:url var="createMembershipUrl"  value="/memberships/create" />
					<form:form action="${createMembershipUrl}" modelAttribute="createMembershipForm" method="POST">
						<form:hidden path="band_id" />
						<table>
							<tbody>
								<tr>
									<td><label>Artist Name:</label></td>
									<td><form:input path="artist_name"/></td>
									<td><form:errors path="artist_name" cssClass="error" /></td>
								</tr>
							</tbody>
						</table>
					</form:form>
				</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>