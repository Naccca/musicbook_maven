<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored = "false" %>

<!DOCTYPE html>
<html>
<head>
	<title>Update profile</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/add-artist-style.css" />
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Update profile</h2>
		</div>
	</div>
	<div id="container">
		<form:form action="update" modelAttribute="artist" method="POST">
			<form:hidden path="id" />
			<table>
				<tbody>
					<tr><td><form:errors path="username" cssClass="error" /></td></tr>
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
						<td><label>Instruments:</label></td>
						<td><form:input path="instruments"/></td>
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
			<a href="${pageContext.request.contextPath}/artists">Back to List</a>
		</p>
	</div>
</body>

</html>