<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="admin"/>
<tiles:putAttribute name="content" type="string">

<div align="center">

	<display:table id="project" name="projects" class="list" style="width:90%;" defaultsort="1" defaultorder="ascending" pagesize="25" sort="list">
		<display:caption>Projects</display:caption>
		<display:column title="Name" property="name" sortable="true"/>
		<display:column title="Description" property="description" sortable="true"/>
		<c:set var="addLink"><a href="admin/project/edit.do">Add</a></c:set>
		<display:column title="${addLink}" headerClass="shrink">
			<a href="admin/project/edit.do?id=${project.id}">Edit</a>
			<a href="admin/project/configuration.do?id=${project.id}">Configuration</a>
		</display:column>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>