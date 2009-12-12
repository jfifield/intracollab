<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="users"/>
<tiles:putAttribute name="content" type="string">

<div align="center">

	<form method="post">
		<table class="detail" width="80%">
			<caption>
				<c:choose>
					<c:when test="${user.id != null}">Edit User: ${user.username}</c:when>
					<c:otherwise>Add User</c:otherwise>
				</c:choose>
			</caption>
		
			<spring:bind path="user.username">
			<tr>
				<td class="label required">Username:</td>
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
	
			<c:set var="passwordRequired" value=""/>
			<c:if test="${user.id == null}">
				<c:set var="passwordRequired" value=" required"/>
			</c:if>
	
			<spring:bind path="user.password1">
			<tr>
				<td class="label${passwordRequired}">Password:</td>
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

			<spring:bind path="user.password2">
			<tr>
				<td class="label${passwordRequired}">Confirm Password:</td>
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
	
			<spring:bind path="user.emailAddress">
			<tr>
				<td class="label required">Email Address:</td>
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
	
			<spring:bind path="user.administrator">
			<tr>
				<td class="label required">Administrator:</td>
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
			
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" class="button" value="Save" name="__save"/>
					<c:if test="${user.id != null}">
						<input type="submit" class="button" value="Delete" name="__delete"/>
					</c:if>
					<input type="submit" class="button" value="Cancel" name="__cancel"/>
				</td>
			</tr>
			
		</table>
	</form>

</div>

<t:focus element="username"/>

</tiles:putAttribute>	
</tiles:insertDefinition>