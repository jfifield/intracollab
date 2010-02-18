<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="tickets"/>
<tiles:putAttribute name="content" type="string">

<div align="right">
	<form name="show_form">
		Project:
		<select name="project_id" onchange="document.forms['show_form'].submit();">
			<c:set var="selected" value=""/>
			<c:if test="${project == null}"><c:set var="selected">selected="selected"</c:set></c:if>
			<option value=""${selected}>All</option>
			<c:forEach var="projectOption" items="${projects}">
				<c:set var="selected" value=""/>
				<c:if test="${project.id == projectOption.id}"><c:set var="selected">selected="selected"</c:set></c:if>
				<option value="${projectOption.id}"${selected}>${projectOption.name}</option>
			</c:forEach>
		</select>
		&nbsp;
		Show:
		<select name="show" onchange="document.forms['show_form'].submit();">
			<c:forEach var="showOption" items="${showOptions}">
				<c:set var="selected" value=""/>
				<c:if test="${show == showOption.first}"><c:set var="selected">selected="selected"</c:set></c:if>
				<option value="${showOption.first}"${selected}>${showOption.second}</option>
			</c:forEach>
		</select>
	</form>
</div>

<div align="center">

	<display:table id="ticket" name="tickets" class="list" style="width:90%;" defaultsort="1" defaultorder="ascending" pagesize="25" sort="list">
		<display:caption><c:if test="${project != null}">${project.name}: </c:if>Tickets</display:caption>
		<display:column title="#" class="shrink" property="id" sortable="true" href="ticket/view.do" paramId="id" paramProperty="id"/>
		<display:column title="Name" property="name" sortable="true"/>
		<c:if test="${project == null}">
			<display:column title="Project" property="project.name" sortable="true"/>
		</c:if>
		<display:column title="Component" property="component.name" sortable="true"/>
		<display:column title="Assigned To" property="assignedTo.username" sortable="true" href="user/activity.do" paramId="username" paramProperty="assignedTo.username"/>
		<display:column title="Status" sortable="true" sortProperty="status">
			${ticket.status.title}
		</display:column>
		<display:column title="Priority" sortable="true" sortProperty="priority">
			${ticket.priority.title}
		</display:column>
		<display:column title="Created" property="created" format="{0,date,short}" sortable="true"/>
		<display:column title="Created By" property="createdBy" sortable="true" href="user/activity.do" paramId="username" paramProperty="createdBy"/>
		<c:url var="addLinkHref" value="ticket/edit.do">
			<c:if test="${project != null}">
				<c:param name="project_id" value="${project.id}"/>
			</c:if>
		</c:url>
		<c:set var="addLink"><a href="${addLinkHref}">Add</a></c:set>
		<display:column title="${addLink}" headerClass="shrink">
			<a href="ticket/edit.do?id=${ticket.id}">Edit</a>
		</display:column>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>