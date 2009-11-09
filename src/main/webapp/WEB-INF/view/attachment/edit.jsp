<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="content" type="string">

<div align="center">

	<form method="post" enctype="multipart/form-data">
		<table class="detail" width="80%">
			<caption>
				Add Attachment
			</caption>
	
			<spring:bind path="attachment.file">
			<tr>
				<td class="label required">File:</td>
				<td>
					<input type="file" name="${status.expression}"/>
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
		
			<spring:bind path="attachment.description">
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
			
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" class="button" value="Save" name="__save"/>
					<input type="submit" class="button" value="Cancel" name="__cancel"/>
				</td>
			</tr>
			
		</table>
	</form>

</div>

<t:focus element="file"/>

</tiles:putAttribute>	
</tiles:insertDefinition>