<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>

<!DOCTYPE html>
<html>
<head>
	<title>Musicbook</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Musicbook</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<c:if test="${not empty pageContext.request.userPrincipal}">
    			Welcome <c:out value="${pageContext.request.userPrincipal.name}" />
    			<br />
			</c:if>
			<a href="${pageContext.request.contextPath}/artists">Artists</a>
			<br />
			<a href="${pageContext.request.contextPath}/bands">Bands</a>
			<br />
			<c:choose>
				<c:when test="${not empty pageContext.request.userPrincipal}">
					<a href="${pageContext.request.contextPath}/logout">Logout</a>
					<br />
				</c:when>
				<c:otherwise>
					<a href="${pageContext.request.contextPath}/artists/new">Sign up</a>
					<br />
					<a href="${pageContext.request.contextPath}/login">Login</a>
					<br />
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>

</html>