<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="ic" uri="http://www.programmerplanet.org/intracollab" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="content" type="string">

<i>${repositoryChange.username} on <fmt:formatDate value="${repositoryChange.changeDate}" type="both" dateStyle="short"/></i>
<p>
	<ic:markup>[${repositoryChange.id}] ${repositoryChange.comment}</ic:markup>
</p>

<div align="center">

	<display:table id="file" name="${repositoryChange.files}" class="list" style="width:90%;" defaultsort="1" defaultorder="ascending" pagesize="25" sort="list">
		<display:caption>Files</display:caption>
		<display:column title="File" property="filename" sortable="true"/>
		<display:column title="Revision" property="revision" sortable="true"/>
	</display:table>

</div>

</tiles:putAttribute>	
</tiles:insertDefinition>