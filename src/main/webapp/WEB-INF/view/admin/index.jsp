<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="admin"/>
<tiles:putAttribute name="content" type="string">

<ul>
	<li><a href="admin/configuration/edit.do">Configuration</a></li>
	<li><a href="admin/user/list.do">Users</a></li>
	<li><a href="admin/project/list.do">Projects</a></li>
</ul>

</tiles:putAttribute>	
</tiles:insertDefinition>