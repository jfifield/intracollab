<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="content" type="string">

<div align="center">

	<display:table id="project" name="projects" class="list" style="width:90%;" defaultsort="1" defaultorder="ascending" pagesize="25" sort="list">
		<display:caption>Projects</display:caption>
		<display:column title="Name" property="name" sortable="true"/>
		<display:column title="Description" property="description" sortable="true"/>
		<c:set var="addLink" value=""/>
		<t:administrator>
			<c:set var="addLink"><a href="admin/project/edit.do">Add</a></c:set>
		</t:administrator>
		<display:column title="${addLink}" headerClass="shrink">
			<a href="project/activity.do?id=${project.id}">Activity</a>
			<a href="ticket/list.do?project_id=${project.id}">Tickets</a>
			<t:administrator>
				<a href="admin/project/configuration.do?id=${project.id}">Configuration</a>
			</t:administrator>
		</display:column>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>