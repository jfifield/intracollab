<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="projects"/>
<tiles:putAttribute name="content" type="string">

<h4>${milestone.project.name}: Milestone ${milestone.name}</h4>
<p>
	Due: <fmt:formatDate value="${milestone.dueDate}" type="date" dateStyle="short"/><br/>
	Completed: <c:choose><c:when test="${milestone.completed}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose><br/>
</p>

<div align="center">

	<display:table id="ticket" name="tickets" class="list" style="width:90%;" defaultsort="1" defaultorder="ascending" pagesize="25" sort="list">
		<display:caption>Milestone ${milestone.name}: Tickets</display:caption>
		<display:column title="#" class="shrink" property="id" sortable="true" href="ticket/view.do" paramId="id" paramProperty="id"/>
		<display:column title="Name" property="name" sortable="true"/>
		<display:column title="Component" property="component.name" sortable="true"/>
		<display:column title="Assigned To" property="assignedTo.username" sortable="true"/>
		<display:column title="Status" sortable="true" sortProperty="status">
			${ticket.status.title}
		</display:column>
		<display:column title="Priority" sortable="true" sortProperty="priority">
			${ticket.priority.title}
		</display:column>
		<display:column title="Created" property="created" format="{0,date,short}" sortable="true"/>
		<display:column title="Created By" property="createdBy" sortable="true"/>
		<c:set var="addLink"><a href="ticket/edit.do?project_id=${milestone.project.id}&milestone=${milestone.id}">Add</a></c:set>
		<display:column title="${addLink}" headerClass="shrink">
			<a href="ticket/edit.do?id=${ticket.id}">Edit</a>
		</display:column>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>