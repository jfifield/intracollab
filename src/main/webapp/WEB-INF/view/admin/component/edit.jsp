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
					<c:when test="${component.id != null}">Edit Component: ${component.name}</c:when>
					<c:otherwise>Add Component</c:otherwise>
				</c:choose>
			</caption>
		
			<spring:bind path="component.name">
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
	
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" class="button" value="Save" name="__save"/>
					<c:if test="${component.id != null}">
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