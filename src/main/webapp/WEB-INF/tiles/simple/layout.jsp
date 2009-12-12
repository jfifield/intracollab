<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<title><spring:message code="application.title"/></title>
	<t:base/>
	<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
	<table id="layout">
		<tr>
			<td id="header">
				<div id="title"><spring:message code="application.title"/></div>
			</td>
		</tr>
		<tr>
			<td id="menubar"></td>
		</tr>
		<tr>
			<td id="shadow"></td>
		</tr>
		<tr>
			<td id="content">
				<tiles:insertAttribute name="content"/>
			</td>
		</tr>
	</table>
</body>
</html>
