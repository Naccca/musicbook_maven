<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
	<title>Update Band</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/add-artist-style.css" />
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Update Band</h2>
		</div>
	</div>
	<div id="container">
		<form:form action="update" modelAttribute="band" method="POST">
			<form:hidden path="id" />
			<table>
				<tbody>
					<tr>
						<td><label>Name:</label></td>
						<td><form:input path="name"/></td>
						<td><form:errors path="name" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>Bio:</label></td>
						<td><form:input path="bio"/></td>
					</tr>
					<tr>
						<td><label>Location:</label></td>
						<td><form:input path="location"/></td>
					</tr>
					<tr>
						<td><label>Genres:</label></td>
						<td><form:input path="genres"/></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Update" class="save"/></td>
					</tr>
				</tbody>
			</table>
		</form:form>
		<div style="clear; both;"></div>
		<p>
			<a href="${pageContext.request.contextPath}/bands">Back to List</a>
		</p>
	</div>
</body>

</html>