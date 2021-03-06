<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="admin"/>
<tiles:putAttribute name="content" type="string">

<table class="detail" width="100%">
	<tr>
		<td class="label">Name:</td>
		<td colspan="3">${project.name}</td>
	</tr>
	<tr>
		<td class="label">Description:</td>
		<td colspan="3">${project.description}</td>
	</tr>
</table>

<ul>
	<li><a href="admin/source_repository/edit.do?project_id=${project.id}">Source Repository</a></li>
	<li><a href="admin/component/list.do?project_id=${project.id}">Components</a></li>
	<li><a href="admin/milestone/list.do?project_id=${project.id}">Milestones</a></li>
</ul>

</tiles:putAttribute>	
</tiles:insertDefinition>