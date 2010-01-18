<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ic" uri="http://www.programmerplanet.org/intracollab" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="search"/>
<tiles:putAttribute name="content" type="string">

<div align="center">
	
	<form method="post">
		<table class="detail" width="25%">
			<spring:bind path="search.query">
			<tr>
				<td class="label required">Search:</td>
				<td>
					<input type="text" name="${status.expression}" value="${status.value}"/>          
				</td>
			</tr>
			</spring:bind>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" class="button" value="Search"/>
				</td>
			</tr>
		</table>
	</form>

</div>

<c:if test="${results != null}">
	<c:choose>
		<c:when test="${empty results}">No matches found.</c:when>
		<c:otherwise>
			<c:forEach var="result" items="${results}">
				<div style="padding-bottom: 10px;">
					<div style="font-weight: bold;">
						<fmt:formatDate value="${result.date}" pattern="MM/dd/yyyy hh:mm:ss aa"/>:
						<ic:markup>${result.description}</ic:markup>
					</div>
					<div>
						<ic:markup><ic:highlight text="${search.query}">${result.text}</ic:highlight></ic:markup>
					</div>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</c:if>

<t:focus element="query"/>

</tiles:putAttribute>	
</tiles:insertDefinition>