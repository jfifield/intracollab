<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="ic" uri="http://www.programmerplanet.org/intracollab" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="activity"/>
<tiles:putAttribute name="content" type="string">

<div align="right">
	<form name="show_form">
		Project:
		<select name="id" onchange="document.forms['show_form'].submit();">
			<c:set var="selected" value=""/>
			<c:if test="${project == null}"><c:set var="selected">selected="selected"</c:set></c:if>
			<option value=""${selected}>All</option>
			<c:forEach var="projectOption" items="${projects}">
				<c:set var="selected" value=""/>
				<c:if test="${project.id == projectOption.id}"><c:set var="selected">selected="selected"</c:set></c:if>
				<option value="${projectOption.id}"${selected}>${projectOption.name}</option>
			</c:forEach>
		</select>
	</form>
</div>

<h4><c:if test="${project != null}">${project.name}: </c:if>Activity</h4>

<c:forEach var="day" items="${days}">
	<div class="section-header">
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