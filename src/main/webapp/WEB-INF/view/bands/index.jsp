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
			<h3 class="title is-3">Bands</h3>
			<div class="columns">
				<div class="column is-3">
					<c:url var="searchUrl"  value="/bands/search" />
					<form:form action="${searchUrl}" method="GET">
						<div class="field">
							<p class="control has-icons-left">
								<input class="input is-rounded" name="search" type="text" placeholder="Search..." />
								<span class="icon is-left">
									<i class="material-icons">search</i>
								</span>
							</p>
						</div>
					</form:form>
				</div>
			</div>
			<div class="columns is-multiline">
				<c:url var="placeholderBigUrl" value="/resources/images/placeholder_big.jpg" />
				<c:forEach var="band" items="${bands}">
					<div class="column is-4">
						<div class="card">
							<div class="card-image">
								<figure class="image is-4by3">
									<c:choose>
										<c:when test="${band.has_image}">
											<img src="<c:url value="/images/bands/${band.id}_big.jpg"/>" />
										</c:when>
										<c:otherwise>
											<img src="${placeholderBigUrl}" />
										</c:otherwise>
									</c:choose>
								</figure>
							</div>
							<div class="card-content">
								<div class="media">
									<div class="media-content">
										<p class="title is-4">
											<c:url var="showUrl" value="/bands/show">
												<c:param name="bandId" value="${band.id}"></c:param>
											</c:url>
											<a href="${showUrl}">${band.name}</a>
										</p>
										<p class="subtitle is-6">${band.location}</p>
									</div>
								</div>
							</div>
							<div class="card-content">
								<time>Member since: <fmt:formatDate value="${band.created_at}" type="date" pattern="d/M/yyyy"/></time>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
	<jsp:include page="../include/footer.jsp" />
</body>
</html>
