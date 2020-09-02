<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored = "false" %>

<!DOCTYPE html>
<html>
<head>
	<title>Save Band</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/add-artist-style.css" />
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Band</h2>
		</div>
	</div>
	<div id="container">
		<h3>Save Band</h3>
		<form:form action="saveBand" modelAttribute="band" method="POST">
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
						<td><input type="submit" value="Save" class="save"/></td>
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