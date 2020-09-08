<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav class="navbar is-dark" role="navigation" aria-label="main navigation">
	<c:url var="homeUrl" value="/" />
	<div class="container is-widescreen">
		<div class="navbar-brand">
			<a class="navbar-item" href="${homeUrl}" style="font-weight: bold;">
				<span class="icon"> <i class="material-icons">music_note</i></span> musicbook
			</a>
			<a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="nav-menu">
				<span></span>
				<span></span>
				<span></span>
			</a>
		</div>
		<div id="nav-menu" class="navbar-menu">
			<div class="navbar-start">
				<a href="${homeUrl}" class="navbar-item">Home</a>
				<c:if test="${not empty pageContext.request.userPrincipal}">
					<c:url var="myProfileUrl" value="/artists/show">
						<c:param name="artistId" value="1"></c:param>
					</c:url>
					<c:url var="newBandUrl" value="/bands/new" />
	    			<a href="${myProfileUrl}" class="navbar-item">My profile</a>
					<a href="${newBandUrl}" class="navbar-item">Create band</a>
				</c:if>
				<c:url var="artistsUrl" value="/artists" />
				<c:url var="bandsUrl" value="/bands" />
				<a href="${artistsUrl}" class="navbar-item">Artists</a>
				<a href="${bandsUrl}" class="navbar-item">Bands</a>
			</div>
			<div class="navbar-end">
				<div class="navbar-item">
					<div class="field is-grouped">
						<c:choose>
							<c:when test="${not empty pageContext.request.userPrincipal}">
								<c:url var="logoutUrl" value="/logout" />
								<p>
									<a href="${logoutUrl}" class="button is-info">Logout</a>
								</p>
							</c:when>
							<c:otherwise>
								<c:url var="signupUrl" value="/artists/new" />
								<c:url var="loginUrl" value="/login" />
								<p class="control">
									<a href="${signupUrl}" class="button is-success">Sign up</a>
								</p>
								<p class="control">
									<a href="${loginUrl}" class="button is-info">Login</a>
								</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
</nav>