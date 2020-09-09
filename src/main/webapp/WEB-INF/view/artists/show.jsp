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
			<h2 class="title">${artist.name}</h2>
			<nav class="level">
				<div class="level-item has-text-centered"></div>
				<c:if test="${not empty pageContext.request.userPrincipal}">
					<c:if test="${pageContext.request.userPrincipal.name == artist.getUsername()}">
						<div class="level-right">
							<p class="level-item">
								<c:url var="artistEditUrl" value="/artists/edit">
									<c:param name="artistId" value="${artist.id}"></c:param>
								</c:url>
								<a href="${artistEditUrl}" class="button is-info">Update</a>
							</p>
							<p class="level-item">
								<c:url var="artistDeleteUrl" value="/artists/delete" />
								<form method="POST" action="${artistDeleteUrl}">
									<input name="id" value="${artist.id}" style="display: none" />
									<input class="button is-danger" type="submit" value="Delete" onclick="if(!(confirm('Are you sure you want to delete this artist?'))) return false" />
								</form>
							</p>
						</div>
					</c:if>
				</c:if>
			</nav>
			<div class="block">
				<c:choose>
					<c:when test="${artist.has_image}">
						<c:url var="imageBigUrl" value="/images/artists/${artist.id}_big.jpg" />
						<img src="${imageBigUrl}" class="is-16by9" alt="" />
					</c:when>
					<c:otherwise>
						<c:url var="placeholderBigUrl" value="/resources/images/placeholder_big.jpg" />
						<img src="${placeholderBigUrl}" class="is-16by9" alt="" />
					</c:otherwise>
				</c:choose>
				<c:if test="${not empty pageContext.request.userPrincipal}">
					<c:if test="${pageContext.request.userPrincipal.name == artist.getUsername()}">
						<div id="file-uploader" class="file has-name is-centered">
							<c:url var="uploadUrl"  value="/artists/upload" />
							<form action="${uploadUrl}" method="POST" enctype="multipart/form-data">
								<input name="artistId" value="${artist.id}" type="hidden" />
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
					<h4 class="title is-spaced is-4">Instruments</h4>
					<div class="subtitle is-5">
						<ul class="comma-list">${artist.instruments}</ul>
					</div>
				</div>
				<div class="column is-6 is-4-desktop">
					<span class="icon"><i class="material-icons">public</i></span>
					<h4 class="title is-spaced is-4">Location</h4>
					<p class="subtitle is-5">${artist.location}</p>
				</div>
				<div class="column is-6 is-4-desktop">
					<span class="icon"><i class="material-icons">account_circle</i></span>
					<h4 class="title is-spaced is-4">Member since</h4>
					<p class="subtitle is-5"><fmt:formatDate value="${artist.created_at}" type="date" pattern="d/M/yyyy"/></p>
				</div> 
			</div>
		</div>
	</section>
	
	<section class="section">
		<div class="container has-text-centered">
			<h4 class="title is-4">About Me</h4>
			<div class="box">
				<p>${artist.bio}</p>
			</div>
		</div>
	</section>

	<section class="section">
		<div class="container has-text-centered">
			<h4 class="title is-4">Bands</h4>
			<p class="subtitle is-5">Proud member of:</p>
			<br>
			<div class="columns is-centered is-multiline">
				<c:url var="placeholderSmallUrl" value="/resources/images/placeholder_small.jpg" />
				<c:forEach var="membership" items="${memberships}">
					<c:url var="bandImageUrl" value="/images/bands/${membership.band.id}_small.jpg" />
					<c:url var="bandShowUrl" value="/bands/show">
						<c:param name="bandId" value="${membership.band.id}"></c:param>
					</c:url>
					<div class="column is-3">
						<div class="level">
							<div class="level-item">
								<figure class="image is-128x128">
									<c:choose>
										<c:when test="${membership.band.has_image}">
											<a href="${bandShowUrl}">
												<img class="is-rounded" src="${bandImageUrl}" alt="">
											</a>
										</c:when>
										<c:otherwise>
											<a href="${bandShowUrl}">
												<img class="is-rounded" src="${placeholderSmallUrl}" alt="">
											</a>
										</c:otherwise>
									</c:choose>
								</figure>
							</div>
						</div>
						<h5 class="title is-5">
							<c:if test="${artist.id == membership.band.owner.id}">
								<i class="material-icons" style="color: #fdd835">star</i>
							</c:if>	
							<a href="${bandShowUrl}">${membership.band.name}</a>
						</h5>
						<div class="subtitle is-6">
							<ul class="comma-list">${membership.band.genres}</ul>
						</div>
						<br>
						<c:if test="${not empty pageContext.request.userPrincipal}">
							<c:if test="${pageContext.request.userPrincipal.name == artist.getUsername()}">
								<c:url var="deleteMembershipUrl"  value="/memberships/delete" />
								<form class="field" method="POST" action="${deleteMembershipUrl}">
									<input name="id" value="${membership.id}" style="display: none" />
									<input type="submit" class="button is-danger" value="Leave" onclick="if(!(confirm('Are you sure?'))) return false" />
								</form>
							</c:if>
						</c:if>	
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
	
	<c:if test="${not empty pageContext.request.userPrincipal}">
		<c:if test="${pageContext.request.userPrincipal.name == artist.getUsername()}">
			<section class="section">
				<div class="container has-text-centered">
					<h2 class="title">Invites</h2>
					<br>
					<div class="columns is-centered is-multiline">
						<c:forEach var="invite" items="${invites}">
							<c:url var="inviteShowUrl" value="/bands/show">
								<c:param name="bandId" value="${invite.band.id}"></c:param>
							</c:url>
							<c:url var="acceptMembershipUrl"  value="/memberships/accept" />
							<div class="column">
								<span class="icon"><i class="material-icons">face</i></span>
								<div class="level">
									<div class="level-item">
										<a href="${inviteShowUrl}">${invite.band.name}</a>
									</div>
								</div>
								<form:form action="${acceptMembershipUrl}" method="POST">
									<input name="id" value="${invite.id}" style="display: none" />
									<input type="submit" value="Accept" class="button is-success"/>
								</form:form>
							</div>
						</c:forEach>	
					</div>
				</div>
			</section>
		</c:if>
	</c:if>
		
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
	<jsp:include page="../include/footer.jsp" />
</body>
</html>
