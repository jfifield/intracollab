<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:importAttribute name="tab"/>

<div class="left">
	<ul>
		<c:set var="active"><c:if test="${tab == 'projects'}">active</c:if></c:set>
		<li class="${active}">
			<a href="project/list.do">Projects</a>
		</li>
		<c:set var="active"><c:if test="${tab == 'tickets'}">active</c:if></c:set>
		<li class="${active}">
			<a href="ticket/list.do">Tickets</a>
		</li>
		<c:set var="active"><c:if test="${tab == 'activity'}">active</c:if></c:set>
		<li class="${active}">
			<a href="project/activity.do">Activity</a>
		</li>
	</ul>
</div>

<div class="right">
	<ul>
		<c:set var="active"><c:if test="${tab == 'search'}">active</c:if></c:set>
		<li class="${active}">
			<a href="search.do">Search</a>
		</li>
		<t:administrator>
			<c:set var="active"><c:if test="${tab == 'admin'}">active</c:if></c:set>
			<li class="${active}">
				<a href="admin/index.do">Administration</a>
			</li>
		</t:administrator>
		<li>
			<a href="logout.do">Logout</a>
		</li>
	</ul>
</div>
