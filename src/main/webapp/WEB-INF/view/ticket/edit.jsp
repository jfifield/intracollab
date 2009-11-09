<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="content" type="string">

<div align="center">

	<form method="post">
		<table class="detail" width="80%">
			<caption>
				<c:choose>
					<c:when test="${ticket.id != null}">Edit Ticket: ${ticket.name}</c:when>
					<c:otherwise>Add Ticket</c:otherwise>
				</c:choose>
			</caption>
		
			<spring:bind path="ticket.name">
			<tr>
				<td class="label required">Name:</td>
				<td>
					<input type="text" name="${status.expression}" value="${status.value}"/>          
				</td>
			</tr>
			<c:if test="${status.error}">
			<tr>
				<td></td>
				<td>
					<c:forEach var="errorMessage" items="${status.errorMessages}">
						<span class="error">* ${errorMessage}</span><br/>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</spring:bind>
	
			<spring:bind path="ticket.description">
			<tr>
				<td class="label">Description:</td>
				<td>
					<textarea name="${status.expression}" rows="10" cols="75">${status.value}</textarea>
				</td>
			</tr>
			<c:if test="${status.error}">
			<tr>
				<td></td>
				<td>
					<c:forEach var="errorMessage" items="${status.errorMessages}">
						<span class="error">* ${errorMessage}</span><br/>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</spring:bind>

			<spring:bind path="ticket.component">
			<tr>
				<td class="label">Component:</td>
				<td>
					<select name="${status.expression}">
						<option value=""></option>
						<c:forEach var="cv" items="${components}">
							<c:set var="selected" value=""/>
							<c:if test="${status.value == cv.id}">
								<c:set var="selected" value=" selected=\"selected\""/>
							</c:if>
							<option value="${cv.id}"${selected}>${cv.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<c:if test="${status.error}">
			<tr>
				<td></td>
				<td>
					<c:forEach var="errorMessage" items="${status.errorMessages}">
						<span class="error">* ${errorMessage}</span><br/>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</spring:bind>
	
			<spring:bind path="ticket.priority">
			<tr>
				<td class="label required">Priority:</td>
				<td>
					<select name="${status.expression}">
						<c:forEach var="pv" items="${priorityValues}">
							<c:set var="selected" value=""/>
							<c:if test="${status.value == pv}">
								<c:set var="selected" value=" selected=\"selected\""/>
							</c:if>
							<option value="${pv}"${selected}>${pv.title}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<c:if test="${status.error}">
			<tr>
				<td></td>
				<td>
					<c:forEach var="errorMessage" items="${status.errorMessages}">
						<span class="error">* ${errorMessage}</span><br/>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</spring:bind>
	
			<spring:bind path="ticket.status">
			<tr>
				<td class="label required">Status:</td>
				<td>
					<select name="${status.expression}">
						<c:forEach var="sv" items="${statusValues}">
							<c:set var="selected" value=""/>
							<c:if test="${status.value == sv}">
								<c:set var="selected" value=" selected=\"selected\""/>
							</c:if>
							<option value="${sv}"${selected}>${sv.title}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<c:if test="${status.error}">
			<tr>
				<td></td>
				<td>
					<c:forEach var="errorMessage" items="${status.errorMessages}">
						<span class="error">* ${errorMessage}</span><br/>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</spring:bind>
	
	
			<spring:bind path="ticket.assignedTo">
			<tr>
				<td class="label">Assigned To:</td>
				<td>
					<select name="${status.expression}">
						<option value=""></option>
						<c:forEach var="uv" items="${users}">
							<c:set var="selected" value=""/>
							<c:if test="${status.value == uv.id}">
								<c:set var="selected" value=" selected=\"selected\""/>
							</c:if>
							<option value="${uv.id}"${selected}>${uv.username}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<c:if test="${status.error}">
			<tr>
				<td></td>
				<td>
					<c:forEach var="errorMessage" items="${status.errorMessages}">
						<span class="error">* ${errorMessage}</span><br/>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</spring:bind>
			
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" class="button" value="Save" name="__save"/>
					<c:if test="${ticket.id != null}">
						<input type="submit" class="button" value="Delete" name="__delete"/>
					</c:if>
					<input type="submit" class="button" value="Cancel" name="__cancel"/>
				</td>
			</tr>
			
		</table>
	</form>

</div>

<t:focus element="name"/>

</tiles:putAttribute>	
</tiles:insertDefinition>