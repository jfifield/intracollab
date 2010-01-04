<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="admin"/>
<tiles:putAttribute name="content" type="string">

<div align="center">

	<form method="post">
		<table class="detail" width="80%">
			<caption>Edit Configuration</caption>

			<spring:bind path="configuration.emailNotifications">
			<tr>
				<td class="label required">Send ticket change email notifications:</td>
				<td>
					<input type="checkbox" name="${status.expression}" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
					<input type="hidden" name="_${status.expression}"/>
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

			<spring:bind path="configuration.emailSender">
			<tr>
				<td class="label required">Sender Email Address:</td>
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
			
			<spring:bind path="configuration.smtpHost">
			<tr>
				<td class="label required">SMTP Host:</td>
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

			<spring:bind path="configuration.smtpUsername">
			<tr>
				<td class="label">SMTP Username:</td>
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

			<spring:bind path="configuration.smtpPassword">
			<tr>
				<td class="label">SMTP Password:</td>
				<td>
					<input type="password" name="${status.expression}" value="${status.value}"/>          
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
					<input type="submit" class="button" value="Cancel" name="__cancel"/>
				</td>
			</tr>
			
		</table>
	</form>

</div>

<t:focus element="smtpNotifications"/>

</tiles:putAttribute>	
</tiles:insertDefinition>