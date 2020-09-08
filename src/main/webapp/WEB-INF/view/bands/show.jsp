<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
			<c:choose>
				<c:when test="${band.has_image}">
					<img src="<c:url value="${request.getContextPath()}/images/bands/${band.id}_big.jpg"/>" />
				</c:when>
				<c:otherwise>
					<img src="<c:url value="/resources/images/placeholder_big.jpg"/>" />
				</c:otherwise>
			</c:choose>
			<c:url var="uploadUrl"  value="/bands/upload" />
			<form:form method="POST" action="${uploadUrl}" enctype="multipart/form-data">
				<input name="bandId" value="${band.getId()}" type="hidden" />
				<table>
					<tr>
						<td><input type="file" name="file" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Submit" /></td>
					</tr>
				</table>
			</form:form>
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
					<th>Actions</th>
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
						<td>
							<c:if test="${not empty pageContext.request.userPrincipal}">
								<c:if test="${pageContext.request.userPrincipal.name == band.getOwner().getUsername()}">
									<c:if test="${band.getOwner().getId() != membership.getArtist().getId()}">
										<c:url var="deleteMembershipUrl"  value="/memberships/delete" />
										<form:form action="${deleteMembershipUrl}" method="POST">
											<input name="id" value="${membership.getId()}" type="hidden" />
											<input type="submit" value="Kick" class="save"/>
										</form:form>
									</c:if>
								</c:if>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:if test="${not empty pageContext.request.userPrincipal}">
				<c:if test="${pageContext.request.userPrincipal.name == band.getOwner().getUsername()}">
					<div id="header">
						<h2>Invites</h2>
					</div>
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
						<c:forEach var="invite" items="${invites}">
							<c:url var="showLink" value="/artists/show">
								<c:param name="artistId" value="${invite.getArtist().id}"></c:param>
							</c:url>
							<tr>
								<td> <a href="${showLink}">${invite.getArtist().id}</a> </td>
								<td> ${invite.getArtist().getUsername()} </td>
								<td> ${invite.getArtist().getName()} </td>
								<td> ${invite.getArtist().getBio()} </td>
								<td> ${invite.getArtist().getLocation()} </td>
								<td> ${invite.getArtist().getInstruments()} </td>
								<td> ${invite.getArtist().getCreated_at()} </td>
								<td> ${invite.getArtist().getUpdated_at()} </td>
								<td> ${invite.getState_id()} </td>
								<td>
									<c:url var="deleteMembershipUrl"  value="/memberships/delete" />
									<form:form action="${deleteMembershipUrl}" method="POST">
										<input name="id" value="${invite.getId()}" type="hidden" />
										<input type="submit" value="Uninvite" class="save"/>
									</form:form>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</c:if>
			
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