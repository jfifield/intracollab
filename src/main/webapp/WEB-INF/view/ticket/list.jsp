<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="projects"/>
<tiles:putAttribute name="content" type="string">

<div align="right">
	<form name="show_form">
		<input type="hidden" name="project_id" value="${project.id}"/>
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
		<display:caption>${project.name}: Tickets</display:caption>
		<display:column title="#" class="shrink" property="id" sortable="true" href="ticket/view.do" paramId="id" paramProperty="id"/>
		<display:column title="Name" property="name" sortable="true"/>
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
		<c:set var="addLink"><a href="ticket/edit.do?project_id=${project.id}">Add</a></c:set>
		<display:column title="${addLink}" headerClass="shrink">
			<a href="ticket/edit.do?id=${ticket.id}">Edit</a>
		</display:column>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>