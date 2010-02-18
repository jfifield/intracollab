<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="tickets"/>
<tiles:putAttribute name="content" type="string">

<div align="center">

	<display:table id="project" name="projects" class="list" style="width:90%;" defaultsort="1" defaultorder="ascending" pagesize="25" sort="list">
		<display:caption>Select Project</display:caption>
		<display:column title="Name" property="name" sortable="true" href="ticket/edit.do" paramId="project_id" paramProperty="id"/>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>