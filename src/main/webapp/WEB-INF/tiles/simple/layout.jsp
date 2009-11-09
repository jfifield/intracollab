<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><spring:message code="application.title"/></title>
	<t:base/>
	<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
	<tiles:insertAttribute name="content"/>
</body>
</html>
