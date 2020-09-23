<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<jsp:include page="../include/head.jsp" />
<c:url var="backgroundUrl" value="/resources/images/grey_background.jpg" />
<body style="background: url(${backgroundUrl});">
	<jsp:include page="../include/nav.jsp" />
	<section class="section">
		<div class="container has-text-centered">
			<h2 class="title">Account created</h2>
			<p>Please verify your email address. Verification email has been sent.</p>
		</div>
	</section>
	<jsp:include page="../include/footer.jsp" />
</body>
</html>