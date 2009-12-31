<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="default">
<tiles:putAttribute name="tab" value="admin"/>
<tiles:putAttribute name="content" type="string">

<script type="text/javascript">
function toggleFormFields() {
	var type = $F('type');
	var elements = ['path_row', 'path_error_row', 'modules_row', 'modules_error_row'];
	elements.each(function(id) {
		var element = $(id);
		if (element) {
			element.style.display = (type == 'none' ? 'none' : '');
		}
	});
}
Event.observe(window, 'load', toggleFormFields);
</script>

<div align="center">

	<form method="post">
		<table class="detail" width="80%">
			<caption>Edit Source Repository: ${project.name}</caption>
			
			<spring:bind path="sourceRepository.type">
			<tr>
				<td class="label required">Type:</td>
				<td>
					<select id="type" name="${status.expression}" onchange="toggleFormFields();">
						<c:forEach var="ov" items="${typeOptions}">
							<c:set var="selected" value=""/>
							<c:if test="${status.value == ov.first}">
								<c:set var="selected" value=" selected=\"selected\""/>
							</c:if>
							<option value="${ov.first}"${selected}>${ov.second}</option>
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

			<spring:bind path="sourceRepository.path">
			<tr id="path_row">
				<td class="label required">Path:</td>
				<td>
					<input type="text" name="${status.expression}" size="50" value="${status.value}"/>          
				</td>
			</tr>
			<c:if test="${status.error}">
			<tr id="path_error_row">
				<td></td>
				<td>
					<c:forEach var="errorMessage" items="${status.errorMessages}">
						<span class="error">* ${errorMessage}</span><br/>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</spring:bind>

			<spring:bind path="sourceRepository.modules">
			<tr id="modules_row">
				<td class="label required">Module(s):</td>
				<td>
					<input type="text" name="${status.expression}" size="50" value="${status.value}"/>          
				</td>
			</tr>
			<c:if test="${status.error}">
			<tr id="modules_error_row">
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

<t:focus element="type"/>

</tiles:putAttribute>	
</tiles:insertDefinition>