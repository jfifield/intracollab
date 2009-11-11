<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="content" type="string">

<div align="center">

	<display:table id="component" name="project.components" class="list" style="width:90%;" defaultsort="1" defaultorder="ascending" pagesize="25" sort="list">
		<display:caption>${project.name}: Components</display:caption>
		<display:column title="Name" property="name" sortable="true"/>
		<c:set var="addLink"><a href="component/edit.do?project_id=${project.id}">Add</a></c:set>
		<display:column title="${addLink}" headerClass="shrink">
			<a href="component/edit.do?id=${component.id}">Edit</a>
		</display:column>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>