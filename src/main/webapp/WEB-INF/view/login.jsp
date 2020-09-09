<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<jsp:include page="include/head.jsp" />
<c:url var="backgroundUrl" value="/resources/images/grey_background.jpg" />
<body style="background: url(${backgroundUrl});">
	<jsp:include page="include/nav.jsp" />
	<section class="section">
		<div class="container has-text-centered">
			<h3 class="title is-3">Login</h3>
			<div class="columns is-centered">
				<div class="column is-5 is-4-desktop">
					<form action='<spring:url value="/loginAction"/>' method="POST">
						<div class="field">
							<div class="control">
								Username:
								<input class="input" type="text" name="username" />
							</div>
						</div>
						
						<div class="field">
							<div class="control">
								Password:
								<input class="input" type="password" name="password" />
							</div>
						</div>
						
						<br>
						<input class="button is-success"  type="submit" value="Submit" />
					</form>
					<br>
					<br>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="include/footer.jsp" />
</body>
</html>