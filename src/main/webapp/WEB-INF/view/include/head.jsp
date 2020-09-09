<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<head>
	<meta charset="UTF-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Welcome to Musicbook</title>
	
	<c:url var="bulmaUrl" value="/webjars/bulma/0.8.0/css/bulma.min.css" />
	<c:url var="materialIconsUrl" value="/webjars/material-icons/0.3.1/css/material-icons.min.css" />
	<c:url var="screenUrl" value="/resources/css/screen.css" />
	<link rel="stylesheet" href="${bulmaUrl}">
	<link rel="stylesheet" href="${materialIconsUrl}">
	<link rel="stylesheet" href="${screenUrl}" />
</head>