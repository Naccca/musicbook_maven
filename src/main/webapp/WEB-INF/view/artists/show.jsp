<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored = "false" %>

<!DOCTYPE html>
<html>
<head>
	<title>${artist.getName()} - Artist</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>${artist.getName()}</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<p>Id: ${artist.getId()}</p>
			<p>Username: ${artist.getUsername()}</p>
			<p>Name: ${artist.getName()}</p>
			<p>Bio: ${artist.getBio()}</p>
			<p>Location: ${artist.getLocation()}</p>
			<p>Instruments: ${artist.getInstruments()}</p>
			<p>Created at: ${artist.getCreated_at()}</p>
			<p>Updated at: ${artist.getUpdated_at()}</p>
			<p>
				<a href="${pageContext.request.contextPath}/artists">Back to List</a>
			</p>
			
			<div id="header">
				<h2>My Bands</h2>
			</div>
			<table>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Bio</th>
					<th>Location</th>
					<th>Genres</th>
					<th>Created at</th>
					<th>Updated at</th>
					<th>Action</th>
				</tr>
				<c:forEach var="band" items="${bands}">
					<c:url var="showLink" value="/bands/show">
						<c:param name="bandId" value="${band.id}"></c:param>
					</c:url>
					<c:url var="updateLink" value="/bands/edit">
						<c:param name="bandId" value="${band.id}"></c:param>
					</c:url>
					<tr>
						<td> <a href="${showLink}">${band.getId()}</a> </td>
						<td> ${band.getName()} </td>
						<td> ${band.getBio()} </td>
						<td> ${band.getLocation()} </td>
						<td> ${band.getGenres()} </td>
						<td> ${band.getCreated_at()} </td>
						<td> ${band.getUpdated_at()} </td>
						<td>
							<a href="${updateLink}">Update</a>
							|
							<form action="bands" method="POST">
								<input name="id" value="${band.getId()}" type="hidden" />
								<input type="submit" value="Delete" class="delete" onclick="if(!(confirm('Are you sure you want to delete this band?'))) return false" />
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<div id="header">
				<h2>Bands I'm part of</h2>
			</div>
			<table>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>State ID</th>
					<th>Bio</th>
					<th>Location</th>
					<th>Genres</th>
					<th>Created at</th>
					<th>Updated at</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="membership" items="${memberships}">
					<c:url var="showLink" value="/bands/show">
						<c:param name="bandId" value="${membership.getBand().id}"></c:param>
					</c:url>
					<tr>
						<td> <a href="${showLink}">${membership.getBand().id}</a> </td>
						<td> ${membership.getBand().name} </td>
						<td> ${membership.state_id} </td>
						<td> ${membership.getBand().bio} </td>
						<td> ${membership.getBand().location} </td>
						<td> ${membership.getBand().genres} </td>
						<td> ${membership.getBand().created_at} </td>
						<td> ${membership.getBand().updated_at} </td>
						<td>
							<c:if test="${not empty pageContext.request.userPrincipal}">
								<c:if test="${pageContext.request.userPrincipal.name == artist.getUsername()}">
									<c:if test="${artist.getId() != membership.getBand().getOwner().getId()}">
										<c:url var="deleteMembershipUrl"  value="/memberships/delete" />
										<form:form action="${deleteMembershipUrl}" method="POST">
											<input name="id" value="${membership.getId()}" type="hidden" />
											<input type="submit" value="Leave" class="save"/>
										</form:form>
									</c:if>
								</c:if>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:if test="${not empty pageContext.request.userPrincipal}">
				<c:if test="${pageContext.request.userPrincipal.name == artist.getUsername()}">
					<div id="header">
						<h2>My Invites</h2>
					</div>
					<table>
						<tr>
							<th>Id</th>
							<th>Name</th>
							<th>State ID</th>
							<th>Bio</th>
							<th>Location</th>
							<th>Genres</th>
							<th>Created at</th>
							<th>Updated at</th>
							<th>Actions</th>
						</tr>
						<c:forEach var="invite" items="${invites}">
							<c:url var="showLink" value="/bands/show">
								<c:param name="bandId" value="${invite.getBand().id}"></c:param>
							</c:url>
							<tr>
								<td> <a href="${showLink}">${invite.getBand().id}</a> </td>
								<td> ${invite.getBand().name} </td>
								<td> ${invite.state_id} </td>
								<td> ${invite.getBand().bio} </td>
								<td> ${invite.getBand().location} </td>
								<td> ${invite.getBand().genres} </td>
								<td> ${invite.getBand().created_at} </td>
								<td> ${invite.getBand().updated_at} </td>
								<td>
									<c:url var="acceptMembershipUrl"  value="/memberships/accept" />
									<form:form action="${acceptMembershipUrl}" method="POST">
										<input name="id" value="${invite.getId()}" type="hidden" />
										<input type="submit" value="Accept" class="save"/>
									</form:form> |
									<c:url var="deleteMembershipUrl"  value="/memberships/delete" />
									<form:form action="${deleteMembershipUrl}" method="POST">
										<input name="id" value="${invite.getId()}" type="hidden" />
										<input type="submit" value="Reject" class="save"/>
									</form:form>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>