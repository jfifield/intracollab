<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="default">
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
			<td>${ticket.assignedTo.username}</td>
		</tr>
		<tr>
			<td class="label">Status:</td>
			<td>${ticket.status.title}</td>
			<td class="label">Priority:</td>
			<td>${ticket.priority.title}</td>
		</tr>
		<tr>
			<td class="label">Created:</td>
			<td><fmt:formatDate value="${ticket.created}" type="date" dateStyle="short"/></td>
			<td class="label">Created By:</td>
			<td>${ticket.createdBy}</td>
		</tr>
		<tr>
			<td class="label">Description:</td>
			<td colspan="3">${ticket.description}</td>
		</tr>
	</table>

</div>

<div style="padding-top: 20px;">
	<div style="padding-bottom: 3px;">
		<span style="color: navy; font-size: larger; font-weight: bold;">Attachments</span>
		<span style="float: right;"><a href="attachment/edit.do?ticket_id=${ticket.id}">Add Attachment</a></span>
	</div>
	<c:forEach var="attachment" items="${ticket.attachments}">
		<div style="border-top: 1px solid gray;">
			<a href="attachment/download.do?id=${attachment.id}">${attachment.fileName}</a> (${attachment.fileSize} bytes)
			<i>${attachment.createdBy} on <fmt:formatDate value="${attachment.created}" type="both" dateStyle="short"/></i>
			<p>
				${attachment.description}
			</p>
		</div>
	</c:forEach>
</div>

<div style="padding-top: 20px;">
	<div style="padding-bottom: 3px;">
		<span style="color: navy; font-size: larger; font-weight: bold;">Comments</span>
		<span style="float: right;"><a href="comment/edit.do?ticket_id=${ticket.id}">Add Comment</a></span>
	</div>
	<c:forEach var="comment" items="${ticket.comments}">
		<div style="border-top: 1px solid gray;">
			<i>${comment.createdBy} on <fmt:formatDate value="${comment.created}" type="both" dateStyle="short"/></i>
			<p>
				${comment.content}
			</p>
		</div>
	</c:forEach>
</div>

<div style="padding-top: 20px;">
	<div style="padding-bottom: 3px;">
		<span style="color: navy; font-size: larger; font-weight: bold;">Changes</span>
	</div>
	<c:forEach var="change" items="${ticket.ticketChanges}">
		<div style="border-top: 1px solid gray;">
			<i>${change.username} on <fmt:formatDate value="${change.changeDate}" type="both" dateStyle="short"/></i>
			<ul>
				<c:forEach var="field" items="${change.fields}">
					<li><b>${field.field}</b> from '${field.oldValue}' to '${field.newValue}'</li>
				</c:forEach>
			</ul>
		</div>
	</c:forEach>
</div>

<div style="padding-top: 20px;">
	<div style="padding-bottom: 3px;">
		<span style="color: navy; font-size: larger; font-weight: bold;">Repository Changes</span>
	</div>
	<c:forEach var="change" items="${ticket.repositoryChanges}">
		<div style="border-top: 1px solid gray;">
			<i>${change.username} on <fmt:formatDate value="${change.changeDate}" type="both" dateStyle="short"/></i>
			<p>
				[${change.id}] ${change.comment}
			</p>
		</div>
	</c:forEach>
</div>

</tiles:putAttribute>	
</tiles:insertDefinition>