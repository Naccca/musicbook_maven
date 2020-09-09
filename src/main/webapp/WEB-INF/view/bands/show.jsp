<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<jsp:include page="../include/head.jsp" />
<c:url var="backgroundUrl" value="/resources/images/grey_background.jpg" />
<body style="background: url(${backgroundUrl});">
	<jsp:include page="../include/nav.jsp" />
	<section class="section">
		<div class="container has-text-centered">
			<h2 class="title">${band.name}</h2>
			<nav class="level">
				<div class="level-item has-text-centered"></div>
				<c:if test="${not empty pageContext.request.userPrincipal}">
					<c:if test="${pageContext.request.userPrincipal.name == band.owner.username}">
						<div class="level-right">
							<p class="level-item">
								<c:url var="bandEditUrl" value="/bands/edit">
									<c:param name="bandId" value="${band.id}"></c:param>
								</c:url>
								<a href="${bandEditUrl}" class="button is-info">Update</a>
							</p>
							<p class="level-item">
								<c:url var="bandDeleteUrl" value="/bands/delete" />
								<form method="POST" action="${bandDeleteUrl}">
									<input name="id" value="${band.id}" style="display: none" />
									<input class="button is-danger" type="submit" value="Delete" onclick="if(!(confirm('Are you sure you want to delete this band?'))) return false" />
								</form>
							</p>
						</div>
					</c:if>
				</c:if>
			</nav>
			<div class="block">
				<c:choose>
					<c:when test="${band.has_image}">
						<c:url var="imageBigUrl" value="/images/bands/${band.id}_big.jpg" />
						<img src="${imageBigUrl}" class="is-16by9" alt="" />
					</c:when>
					<c:otherwise>
						<c:url var="placeholderBigUrl" value="/resources/images/placeholder_big.jpg" />
						<img src="${placeholderBigUrl}" class="is-16by9" alt="" />
					</c:otherwise>
				</c:choose>
				<c:if test="${not empty pageContext.request.userPrincipal}">
					<c:if test="${pageContext.request.userPrincipal.name == band.owner.username}">
						<div id="file-uploader" class="file has-name is-centered">
							<c:url var="uploadUrl"  value="/bands/upload" />
							<form action="${uploadUrl}" method="POST" enctype="multipart/form-data">
								<input name="bandId" value="${band.id}" type="hidden" />
								<label class="file-label">
									<input class="file-input" type="file" name="file" id="fileToUpload" />
									<span class="file-cta">
										<span class="file-icon"><i class="material-icons">file_upload</i></span>
										<span class="file-label">Choose a file…</span>
									</span>
									<span class="file-name">No file uploaded</span>
									<span><input class="button is-success" type="submit" value="Upload" name="submit"></span>
								</label>
							</form>
						</div>
      				</c:if>
      			</c:if>
			</div>
		</div>
	</section>

	<section class="section">
		<div class="container has-text-centered">
			<div class="columns is-multiline">
				<div class="column is-6 is-4-desktop">
					<span class="icon"><i class="material-icons">music_note</i></span>
					<h4 class="title is-spaced is-4">Genres</h4>
					<div class="subtitle is-5">
						<ul class="comma-list">${band.genres}</ul>
					</div>
				</div>
				<div class="column is-6 is-4-desktop">
					<span class="icon"><i class="material-icons">public</i></span>
					<h4 class="title is-spaced is-4">Location</h4>
					<p class="subtitle is-5">${band.location}</p>
				</div>
			<div class="column is-6 is-4-desktop">
				<span class="icon"><i class="material-icons">account_circle</i></span>
				<h4 class="title is-spaced is-4">Member since</h4>
				<p class="subtitle is-5"><fmt:formatDate value="${band.created_at}" type="date" pattern="d/M/yyyy"/></p>
			</div> 
		</div>
	</div>
	</section>
	
	<section class="section">
		<div class="container has-text-centered">
			<h4 class="title is-4">Biography</h4>
			<div class="box"><p>${band.bio}</p></div>
		</div>
	</section>
	
	<section class="section">
		<div class="container has-text-centered">
			<h4 class="title is-4">Members</h4>
			<p class="subtitle is-5">We are the guys that made this whole thing possible</p>
			<br>
			<div class="columns is-centered is-multiline">
				<c:url var="placeholderSmallUrl" value="/resources/images/placeholder_small.jpg" />
				<c:forEach var="membership" items="${memberships}">
					<c:url var="artistImageUrl" value="/images/artists/${membership.artist.id}_small.jpg" />
					<c:url var="artistShowUrl" value="/artists/show">
						<c:param name="artistId" value="${membership.artist.id}"></c:param>
					</c:url>
					<div class="column is-3">
						<div class="level">
							<div class="level-item">
								<figure class="image is-128x128">
									<c:choose>
										<c:when test="${membership.artist.has_image}">
											<a href="${artistShowUrl}">
												<img class="is-rounded" src="${artistImageUrl}" alt="">
											</a>
										</c:when>
										<c:otherwise>
											<a href="${artistShowUrl}">
												<img class="is-rounded" src="${placeholderSmallUrl}" alt="">
											</a>
										</c:otherwise>
									</c:choose>
								</figure>
							</div>
						</div>
						<h5 class="title is-5">
							<c:if test="${band.owner.id == membership.artist.id}">
								<i class="material-icons" style="color: #fdd835">star</i>
							</c:if>
							<a href="${artistShowUrl}">${membership.artist.name}</a>
						</h5>
						<div class="subtitle is-6">
							<ul class="comma-list">${membership.artist.instruments}</ul>
						</div>
						<br />
						<c:if test="${not empty pageContext.request.userPrincipal}">
							<c:if test="${pageContext.request.userPrincipal.name == band.owner.username}">
								<c:if test="${band.owner.id != membership.artist.id}">
									<c:url var="deleteMembershipUrl"  value="/memberships/delete" />
									<form class="field" method="POST" action="${deleteMembershipUrl}">
										<input name="id" value="${membership.id}" style="display: none" />
										<input type="submit" class="button is-danger" value="Kick" onclick="if(!(confirm('Are you sure?'))) return false" />
									</form>
								</c:if>
							</c:if>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
	
	<c:if test="${not empty pageContext.request.userPrincipal}">
		<c:if test="${pageContext.request.userPrincipal.name == band.owner.username}">
			<section class="section">
				<div class="container has-text-centered">
					<h4 class="title is-4">Invites</h4>
					<br>
					<div class="columns is-centered is-multiline">
						<c:forEach var="invite" items="${invites}">
							<c:url var="inviteShowUrl" value="/artists/show">
								<c:param name="artistId" value="${invite.artist.id}"></c:param>
							</c:url>
							<c:url var="deleteMembershipUrl"  value="/memberships/delete" />
							<div class="column">
								<span class="icon"><i class="material-icons">face</i></span>
								<div class="level">
									<div class="level-item">
										<a class="link" href="${inviteShowUrl}">${invite.artist.name}</a>
									</div>
								</div>
								<form:form action="${deleteMembershipUrl}" method="POST">
									<input name="id" value="${invite.id}" style="display: none" />
									<input type="submit" value="Cancel" class="button is-danger" onclick="if(!(confirm('Are you sure?'))) return false" />
								</form:form>
							</div>
						</c:forEach>
					</div>
				</div>
			</section>
		</c:if>
	</c:if>
	
	<c:if test="${not empty pageContext.request.userPrincipal}">
		<c:if test="${pageContext.request.userPrincipal.name == band.owner.username}">
			<section class="section">
				<div class="container has-text-centered">
					<div class="columns is-centered">
						<div class="column is-5 is-4-desktop">
							<c:url var="createMembershipUrl"  value="/memberships/create" />
							<form:form class="field" method="POST" action="${createMembershipUrl}" modelAttribute="createMembershipForm" >
								<form:hidden path="band_id" />
								<div class="control">
									<form:input path="artist_name" placeholder="Artist" cssClass="input" />
								</div>
								<br />
								<div class="field">
									<input type="submit" class="button is-success" value="Submit"/>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</section>
			<script type="text/javascript">
				(function(){
					const fileInput = document.querySelector('#file-uploader input[type=file]');
					fileInput.onchange = () => {
						if (fileInput.files.length > 0) {
							const fileName = document.querySelector('#file-uploader .file-name');
							fileName.textContent = fileInput.files[0].name;
						}
					}
				})();
			</script>
		</c:if>
	</c:if>
	<jsp:include page="../include/footer.jsp" />
</body>
</html>
