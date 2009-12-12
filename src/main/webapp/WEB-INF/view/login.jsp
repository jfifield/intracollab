<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="simple">
<tiles:putAttribute name="content" type="string">

<div align="center">
	
	<form method="post">
	
		<spring:hasBindErrors name="login">
			<c:if test="${errors.globalErrorCount > 0}">
				<c:forEach var="error" items="${errors.globalErrors}">
					<span class="error">* <spring:message code="${error.code}" text="${error.defaultMessage}"/></span><br/>
				</c:forEach>
			</c:if>
		</spring:hasBindErrors>
		
		<table class="detail" width="25%">
			<caption>Login</caption>
		
			<spring:bind path="login.username">
			<tr>
				<td class="label required">Username: </td>
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
			
			<spring:bind path="login.password">
			<tr>
				<td class="label required">Password: </td>
				<td>
					<input type="password" name="${status.expression}" />
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
					<input type="submit" class="button" value="Login"/>
				</td>
			</tr>
		</table>
	          
	</form>

</div>

<t:focus element="username"/>

</tiles:putAttribute>	
</tiles:insertDefinition>