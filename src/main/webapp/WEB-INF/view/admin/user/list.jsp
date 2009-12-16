<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="admin"/>
<tiles:putAttribute name="content" type="string">

<div align="center">

	<display:table id="user" name="users" class="list" style="width:90%;" defaultsort="1" defaultorder="ascending" pagesize="25" sort="list">
		<display:caption>Users</display:caption>
		<display:column title="Username" property="username" sortable="true"/>
		<display:column title="Email Address" property="emailAddress" sortable="true"/>
		<display:column title="Administrator" sortable="true">
			<c:if test="${user.administrator}">Yes</c:if>
		</display:column>
		<c:set var="addLink"><a href="admin/user/edit.do">Add</a></c:set>
		<display:column title="${addLink}" headerClass="shrink">
			<a href="admin/user/edit.do?id=${user.id}">Edit</a>
		</display:column>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>