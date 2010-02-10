<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="ic" uri="http://www.programmerplanet.org/intracollab" %>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="projects"/>
<tiles:putAttribute name="content" type="string">

<div align="center">

	<table class="detail" width="100%">
		<tr>
			<td colspan="4" style="text-align: right;"><a href="ticket/edit.do?id=${ticket.id}">Edit</a></td>
		</tr>
		<tr>
			<td class="label">Name:</td>
			<td colspan="3">${ticket.name}</td>
		</tr>
		<tr>
			<td class="label">Ticket #:</td>
			<td>${ticket.id}</td>
			<td class="label">Assigned To:</td>
			<td><a href="user/activity.do?username=${ticket.assignedTo.username}">${ticket.assignedTo.username}</a></td>
		</tr>
		<tr>
			<td class="label">Status:</td>
			<td>${ticket.status.title}</td>
			<td class="label">Priority:</td>
			<td>${ticket.priority.title}</td>
		</tr>
		<tr>
			<td class="label">Milestone:</td>
			<td><a href="milestone/view.do?id=${ticket.milestone.id}">${ticket.milestone.name}</a></td>
			<td class="label">Component:</td>
			<td>${ticket.component.name}</td>
		</tr>
		<tr>
			<td class="label">Created:</td>
			<td><fmt:formatDate value="${ticket.created}" type="date" dateStyle="short"/></td>
			<td class="label">Created By:</td>
			<td><a href="user/activity.do?username=${ticket.createdBy}">${ticket.createdBy}</a></td>
		</tr>
		<tr>
			<td class="label">Description:</td>
			<td colspan="3"><ic:markup>${ticket.description}</ic:markup></td>
		</tr>
	</table>

</div>

<div style="padding-top: 20px;">
	<div class="section-header">Attachments</div>
	<div style="border-bottom: 1px solid gray; text-align: right;"><a href="attachment/edit.do?ticket_id=${ticket.id}">Add Attachment</a></div>
	<c:forEach var="attachment" items="${ticket.attachments}">
		<div style="border-bottom: 1px solid gray;">
			<i><a href="user/activity.do?username=${attachment.createdBy}">${attachment.createdBy}</a> on <fmt:formatDate value="${attachment.created}" type="both" dateStyle="short"/></i>
			<p>
				<a href="attachment/download.do?id=${attachment.id}">${attachment.fileName}</a> (${attachment.fileSize} bytes)
			</p>
			<p>
				<ic:markup>${attachment.description}</ic:markup>
			</p>
		</div>
	</c:forEach>
</div>

<div style="padding-top: 20px;">
	<div class="section-header">Comments</div>
	<div style="border-bottom: 1px solid gray; text-align: right;"><a href="comment/edit.do?ticket_id=${ticket.id}">Add Comment</a></div>
	<c:forEach var="comment" items="${ticket.comments}">
		<div style="border-bottom: 1px solid gray;">
			<i><a href="user/activity.do?username=${comment.createdBy}">${comment.createdBy}</a> on <fmt:formatDate value="${comment.created}" type="both" dateStyle="short"/></i>
			<p>
				<ic:markup>${comment.content}</ic:markup>
			</p>
		</div>
	</c:forEach>
</div>

<div style="padding-top: 20px;">
	<div class="section-header">Changes</div>
	<c:forEach var="change" items="${ticket.ticketChanges}">
		<div style="border-bottom: 1px solid gray;">
			<i><a href="user/activity.do?username=${change.username}">${change.username}</a> on <fmt:formatDate value="${change.changeDate}" type="both" dateStyle="short"/></i>
			<ul>
				<c:forEach var="field" items="${change.fields}">
					<li><b>${field.field}</b> from '${field.oldValue}' to '${field.newValue}'</li>
				</c:forEach>
			</ul>
		</div>
	</c:forEach>
</div>

<div style="padding-top: 20px;">
	<div class="section-header">Repository Changes</div>
	<c:forEach var="change" items="${ticket.repositoryChanges}">
		<div style="border-bottom: 1px solid gray;">
			<i><a href="user/activity.do?username=${change.username}">${change.username}</a> on <fmt:formatDate value="${change.changeDate}" type="both" dateStyle="short"/></i>
			<p>
				<ic:markup>[${change.id}] ${change.comment}</ic:markup>
			</p>
		</div>
	</c:forEach>
</div>

</tiles:putAttribute>	
</tiles:insertDefinition>