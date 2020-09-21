<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
			<h3 class="title is-3">Update ${band.name}</h3>
		</div>
		<div class="content">
			<div class="columns">
				<div class="column">
					<form:form action="update" modelAttribute="band" method="POST">
						<form:hidden path="id" />
						
						<div class="field">
							<div class="control">
								Name:
								<form:input path="name" cssClass="input" />
							</div>
						</div>
						<form:errors path="name" element="div" cssClass="notification is-danger" />
						
						<div class="field">
							<div class="control">
								Location:
								<form:input path="location" cssClass="input" />
							</div>
						</div>
						<form:errors path="location" element="div" cssClass="notification is-danger" />
						
						<div class="field">
							<div class="control">
								Genres:
								<form:input path="genres" cssClass="input" placeholder="Separated by a comma" />
							</div>
						</div>
						<form:errors path="genres" element="div" cssClass="notification is-danger" />
						
						<div class="field">
							<div class="control">
								Biography:
								<form:textarea path="bio" cssClass="textarea" />
							</div>
						</div>
						<form:errors path="bio" element="div" cssClass="notification is-danger" />
						
						<br>
						<input class="button is-info"  type="submit" value="Update" />
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="../include/footer.jsp" />
</body>
</html>
