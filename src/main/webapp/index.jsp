<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/view/include/head.jsp" />
<c:url var="backgroundUrl" value="/resources/images/grey_background.jpg" />
<body style="background: url(${backgroundUrl});">
	<jsp:include page="/WEB-INF/view/include/nav.jsp" />
	<section class="hero">
		<div class="hero-body">
			<div class="container has-text-centered">
				<h1 class="title is-spaced is-1">musicbook</h1>
				<h4 class="title is-4">Find musicians and bands and together create some awesome music</h4>
				<div class="buttons is-centered">
					<c:url var="signupUrl" value="/artists/new" />
					<c:url var="loginUrl" value="/login" />
					<a class="control button is-success" href="${signupUrl}">Sign up for free</a>
					<a class="control button is-info" href="${loginUrl}">Login</a>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="columns is-desktop is-vcentered">
				<div class="column is-6-desktop">
					<c:url var="titleImageUrl" value="/resources/images/musicbook.jpeg" />
					<img src="${titleImageUrl}" alt="">
				</div>
				<div class="column is-6-desktop">
					<h2 class="title is-spaced">Musicbook is place where talents meet</h2>
					<div class="content is-medium">
						<ul>
							<li>Simply create an account with all the fun music information that you would like to share with the world.</li>
							<li>You can create a band profile of all genres and types.</li>
							<li>As a creator of a band, you can send invites to musicians who you would like to be part of your crew.</li>
						</ul>
					</div>
					<h5 class="title is-5">Search and find the ones who share your vision</h5>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="/WEB-INF/view/include/footer.jsp" />
</body>

</html>