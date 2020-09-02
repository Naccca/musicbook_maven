<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>

<!DOCTYPE html>
<html>
<head>
	<title>${band.getName()} - Band</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>${band.getName()}</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<p>Id: ${band.getId()}</p>
			<p>Name: ${band.getName()}</p>
			<p>Bio: ${band.getBio()}</p>
			<p>Location: ${band.getLocation()}</p>
			<p>Genres: ${band.getGenres()}</p>
			<p>Created at: ${band.getCreated_at()}</p>
			<p>Updated at: ${band.getUpdated_at()}</p>
			<c:url var="ownerLink" value="/artists/show">
				<c:param name="artistId" value="${band.getOwner().getId()}"></c:param>
			</c:url>
			<p>Owner: <a href="${ownerLink}">${band.getOwner().getName()}</a></p>
			<p>
				<a href="${pageContext.request.contextPath}/bands">Back to List</a>
			</p>
		</div>
	</div>
</body>
</html>