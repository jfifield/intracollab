<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="ic" uri="http://www.programmerplanet.org/intracollab" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="content" type="string">

<h4>${project.name}: Activity</h4>

<c:forEach var="day" items="${days}">
	<div style="font-weight: bold; background-color: #f9fbfd; border: 1px solid #c3bbb6;">
		<fmt:formatDate value="${day}" pattern="MM/dd/yyyy"/>
	</div>
	<ul>
		<c:set var="activityItems" value="${activityItemsByDay[day]}"/>
		<c:forEach var="activityItem" items="${activityItems}">
			<li>
				<fmt:formatDate value="${activityItem.date}" pattern="hh:mm:ss aa"/>:
				<ic:markup>${activityItem.description}</ic:markup>
			</li>
		</c:forEach>
	</ul>
</c:forEach>

</tiles:putAttribute>	
</tiles:insertDefinition>